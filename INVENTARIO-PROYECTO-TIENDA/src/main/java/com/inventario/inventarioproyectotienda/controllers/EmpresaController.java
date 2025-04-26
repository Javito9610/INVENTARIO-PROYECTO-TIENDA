package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
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

@WebServlet(name = "EmpresaController", urlPatterns = {"/empresas"})
public class EmpresaController extends HttpServlet {
    private EmpresaDAO empresaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
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
                    eliminarEmpresa(request, response);
                    break;
                default:
                    listarEmpresas(request, response);
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
                guardarEmpresa(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarEmpresas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-empresas.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("empresa-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idEmpresa = Integer.parseInt(request.getParameter("id"));
        Empresa empresaAEditar = empresaDAO.getEmpresaById(idEmpresa);
        request.setAttribute("empresa", empresaAEditar);
        RequestDispatcher dispatcher = request.getRequestDispatcher("empresa-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarEmpresa(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String correoElectronico = request.getParameter("correo_electronico");
        String descripcion = request.getParameter("descripcion");
        String sitioWeb = request.getParameter("sitio_web");
        int idEmpresa = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;

        Empresa empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);
        empresa.setCorreoElectronico(correoElectronico);
        empresa.setDescripcion(descripcion);
        empresa.setSitioWeb(sitioWeb);
        if (idEmpresa > 0) {
            empresa.setIdEmpresa(idEmpresa);
            empresaDAO.updateEmpresa(empresa);
        } else {
            empresaDAO.insertEmpresa(empresa);
        }
        response.sendRedirect("empresas?action=list");
    }

    private void eliminarEmpresa(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idEmpresa = Integer.parseInt(request.getParameter("id"));
        System.out.println("Intentando eliminar la empresa con ID: " + idEmpresa);
        boolean eliminado = empresaDAO.deleteEmpresa(idEmpresa);
        System.out.println("Resultado de la eliminación: " + eliminado);
        response.sendRedirect("empresas?action=list");
    }
}