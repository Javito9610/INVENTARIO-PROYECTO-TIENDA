package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.ClienteDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
import com.inventario.inventarioproyectotienda.models.Cliente;
import com.inventario.inventarioproyectotienda.models.Empresa;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ClienteController", urlPatterns = {"/clientes"})
public class ClienteController extends HttpServlet {
    private ClienteDAO clienteDAO;
    private EmpresaDAO empresaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        clienteDAO = new ClienteDAO();
        empresaDAO = new EmpresaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "new":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "edit":
                    mostrarFormularioEdicion(request, response);
                    break;
                case "delete":
                    eliminarCliente(request, response);
                    break;
                default:
                    listarClientes(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "save";
        }
        try {
            if (action.equals("save")) {
                guardarCliente(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Cliente> listaClientes = clienteDAO.getAllClientes();
        request.setAttribute("listaClientes", listaClientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-clientes.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String dni = request.getParameter("dni");
        Cliente clienteAEditar = clienteDAO.getClienteByDni(dni);
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("cliente", clienteAEditar);
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String numeroTelefono = request.getParameter("numero_telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String ciudad = request.getParameter("ciudad");
        String codigoPostal = request.getParameter("codigo_postal");
        String provincia = request.getParameter("provincia");
        int empresaId = Integer.parseInt(request.getParameter("empresas_id_empresa"));

        Empresa empresa = empresaDAO.getEmpresaById(empresaId);
        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellidos(apellidos);
        cliente.setNumeroTelefono(numeroTelefono);
        cliente.setCorreo(correo);
        cliente.setDireccion(direccion);
        cliente.setCiudad(ciudad);
        cliente.setCodigoPostal(codigoPostal);
        cliente.setProvincia(provincia);
        cliente.setEmpresa(empresa);

        clienteDAO.insertCliente(cliente);
        response.sendRedirect("clientes?action=list");
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String dni = request.getParameter("dni");
        clienteDAO.deleteCliente(dni);
        response.sendRedirect("clientes?action=list");
    }
}