package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.UsuarioDAO;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuarios"})
public class UsuarioController extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private EmpresaDAO empresaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = new UsuarioDAO();
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
                    eliminarUsuario(request, response);
                    break;
                default:
                    listarUsuarios(request, response);
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
                guardarUsuario(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioDAO.getAllUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-usuarios.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        Usuario usuarioAEditar = usuarioDAO.getUsuarioById(idUsuario);
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("usuario", usuarioAEditar);
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String password = request.getParameter("contrasena");
        String rol = request.getParameter("rol");
        String empresaIdStr = request.getParameter("empresaId");
        int idUsuario = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;

        Empresa empresa = null;
        if (empresaIdStr != null && !empresaIdStr.isEmpty()) {
            int empresaId = Integer.parseInt(empresaIdStr);
            empresa = empresaDAO.getEmpresaById(empresaId);
        } else {

            System.err.println("Advertencia: No se seleccionó ninguna empresa para el usuario.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setRol(rol);
        usuario.setEmpresa(empresa);

        if (idUsuario > 0) {
        } else {
            if (password != null && !password.isEmpty()) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                usuario.setContrasena(hashedPassword);
                usuarioDAO.insertUsuario(usuario);
            } else {
            }
        }
        response.sendRedirect("usuarios?action=list");
    }
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.deleteUsuario(idUsuario);
        response.sendRedirect("usuarios?action=list");
    }
}