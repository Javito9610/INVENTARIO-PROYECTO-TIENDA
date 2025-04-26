package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.ProveedorDAO;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Proveedor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ProveedorController", urlPatterns = {"/proveedores"})
public class ProveedorController extends HttpServlet {
    private ProveedorDAO proveedorDAO;
    private EmpresaDAO empresaDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        proveedorDAO = new ProveedorDAO();
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
                    eliminarProveedor(request, response);
                    break;
                default:
                    listarProveedores(request, response);
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
                guardarProveedor(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Proveedor> listaProveedores = proveedorDAO.getAllProveedores();
        request.setAttribute("listaProveedores", listaProveedores);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-proveedores.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("Se ha llamado al método mostrarFormularioNuevoProveedor");
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        System.out.println("Número de empresas encontradas: " + listaEmpresas.size());
        if (listaEmpresas.isEmpty()) {
            request.setAttribute("mensajeAlerta", "No hay empresas registradas. Debes crear una empresa antes de poder registrar un proveedor.");
        } else {
            request.setAttribute("listaEmpresas", listaEmpresas);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("proveedor-form.jsp");
        dispatcher.forward(request, response);
    }
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idProveedor = Integer.parseInt(request.getParameter("id"));
        Proveedor proveedorAEditar = proveedorDAO.getProveedorById(idProveedor);
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("proveedor", proveedorAEditar);
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("proveedor-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String ciudad = request.getParameter("ciudad");
        String provincia = request.getParameter("provincia");
        String codigoPostal = request.getParameter("codigoPostal");
        String pais = request.getParameter("pais");
        String rfcNit = request.getParameter("rfcNit");
        String tipo = request.getParameter("tipo");
        String sitioWeb = request.getParameter("sitioWeb");
        String fechaRegistroStr = request.getParameter("fechaRegistro");

        String empresaIdStr = request.getParameter("empresaId");
        int empresaId = Integer.parseInt(empresaIdStr);

        Empresa empresa = empresaDAO.getEmpresaById(empresaId);

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setContacto(contacto);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setCiudad(ciudad);
        proveedor.setProvincia(provincia);
        proveedor.setCodigoPostal(codigoPostal);
        proveedor.setPais(pais);
        proveedor.setRfcNit(rfcNit);
        proveedor.setTipo(tipo);
        proveedor.setSitioWeb(sitioWeb);


        if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
            try {
                Date fechaRegistro = dateFormat.parse(fechaRegistroStr);
                proveedor.setFechaRegistro(fechaRegistro);
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }

        proveedor.setEmpresa(empresa);

        proveedorDAO.insertProveedor(proveedor);

        response.sendRedirect("proveedores?action=list");
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idProveedor = Integer.parseInt(request.getParameter("id"));
        proveedorDAO.deleteProveedor(idProveedor);
        response.sendRedirect("proveedores?action=list");
    }
}