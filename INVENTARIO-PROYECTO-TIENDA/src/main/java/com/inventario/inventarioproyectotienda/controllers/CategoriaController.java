package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.CategoriaDAO;
import com.inventario.inventarioproyectotienda.models.Categoria;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoriaController", urlPatterns = {"/categorias"})
public class CategoriaController extends HttpServlet {
    private CategoriaDAO categoriaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        categoriaDAO = new CategoriaDAO();
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
                    eliminarCategoria(request, response);
                    break;
                default:
                    listarCategorias(request, response);
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
                guardarCategoria(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarCategorias(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Categoria> listaCategorias = categoriaDAO.getAllCategorias();
        request.setAttribute("listaCategorias", listaCategorias);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-categorias.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("categoria-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idCategoria = Integer.parseInt(request.getParameter("id"));
        Categoria categoriaAEditar = categoriaDAO.getCategoriaById(idCategoria);
        request.setAttribute("categoria", categoriaAEditar);
        RequestDispatcher dispatcher = request.getRequestDispatcher("categoria-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String nombre = request.getParameter("nombre");
        int idCategoria = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        if (idCategoria > 0) {
            categoria.setIdCategoria(idCategoria);
            categoriaDAO.updateCategoria(categoria);
        } else {
            categoriaDAO.insertCategoria(categoria);
        }
        response.sendRedirect("categorias?action=list");
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idCategoria = Integer.parseInt(request.getParameter("id"));
        categoriaDAO.deleteCategoria(idCategoria);
        response.sendRedirect("categorias?action=list");
    }
}