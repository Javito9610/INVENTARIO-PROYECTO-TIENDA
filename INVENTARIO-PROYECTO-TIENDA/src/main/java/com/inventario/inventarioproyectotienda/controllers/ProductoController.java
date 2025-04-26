package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.ProductoDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.ProveedorDAO;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Producto;
import com.inventario.inventarioproyectotienda.models.Proveedor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProductoController", urlPatterns = {"/productos"})
public class ProductoController extends HttpServlet {
    private ProductoDAO productoDAO;
    private ProveedorDAO proveedorDAO;
    private EmpresaDAO empresaDAO;
    static final Logger LOGGER = Logger.getLogger(ProductoController.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAO();
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
                    eliminarProducto(request, response);
                    break;
                default:
                    listarProductos(request, response);
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
                guardarProducto(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Producto> listaProductos = productoDAO.getAllProductos();
        request.setAttribute("listaProductos", listaProductos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-productos.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("Se ha llamado al método mostrarFormularioNuevoProducto");
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        List<Proveedor> listaProveedores = proveedorDAO.getAllProveedores();

        String mensajeAlerta = null;

        if (listaEmpresas.isEmpty()) {
            mensajeAlerta = "No hay empresas registradas. Debes crear una empresa antes de poder registrar un producto.";
        } else if (listaProveedores.isEmpty()) {
            mensajeAlerta = "No hay proveedores registrados. Debes crear un proveedor antes de poder registrar un producto.";
        } else if (listaEmpresas.isEmpty() && listaProveedores.isEmpty()) {
            mensajeAlerta = "No hay empresas ni proveedores registrados. Debes crear al menos uno de cada para poder registrar un producto.";
        }

        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("listaEmpresas", listaEmpresas);
        request.setAttribute("listaProveedores", listaProveedores);

        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        Producto productoAEditar = productoDAO.getProductoById(idProducto);
        List<Proveedor> listaProveedores = proveedorDAO.getAllProveedores();
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("producto", productoAEditar);
        request.setAttribute("listaProveedores", listaProveedores);
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        String anyosGarantiaStr = request.getParameter("anyos_garantia");
        Integer anyosGarantia = anyosGarantiaStr != null && !anyosGarantiaStr.isEmpty() ? Integer.parseInt(anyosGarantiaStr) : null;
        String unidadesStr = request.getParameter("unidades");
        Integer unidades = null;
        if (unidadesStr != null && !unidadesStr.isEmpty()) {
            try {
                unidades = Integer.parseInt(unidadesStr);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Error al parsear unidades: " + unidadesStr, e);
                return;
            }
        }

        String proveedorIdStr = request.getParameter("proveedores_id_proveedores");
        Integer proveedorId = null;
        if (proveedorIdStr != null && !proveedorIdStr.isEmpty()) {
            try {
                proveedorId = Integer.parseInt(proveedorIdStr);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Error al parsear proveedorId: " + proveedorIdStr, e);
                request.setAttribute("errorProveedor", "El ID del proveedor no es válido.");
                mostrarFormularioNuevo(request, response);
                return;
            }
        } else {
            LOGGER.warning("Advertencia: No se ha seleccionado ningún proveedor.");
            request.setAttribute("errorProveedor", "Debes seleccionar un proveedor.");
            mostrarFormularioNuevo(request, response);
            return;
        }

        String empresaIdStr = request.getParameter("empresas_id_empresa");
        Integer empresaId = null;
        if (empresaIdStr != null && !empresaIdStr.isEmpty()) {
            try {
                empresaId = Integer.parseInt(empresaIdStr);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Error al parsear empresaId: " + empresaIdStr, e);
                request.setAttribute("errorEmpresa", "El ID de la empresa no es válido.");
                mostrarFormularioNuevo(request, response);
                return;
            }
        } else {
            LOGGER.warning("Advertencia: No se ha seleccionado ninguna empresa.");
            request.setAttribute("errorEmpresa", "Debes seleccionar una empresa.");
            mostrarFormularioNuevo(request, response);
            return;
        }

        int idProducto = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;

        Proveedor proveedor = null;
        if (proveedorId != null) {
            proveedor = proveedorDAO.getProveedorById(proveedorId);
            if (proveedor == null) {
                LOGGER.log(Level.SEVERE, "No se encontró el proveedor con ID: " + proveedorId);
                request.setAttribute("errorProveedor", "No se encontró el proveedor seleccionado.");
                mostrarFormularioNuevo(request, response);
                return;
            }
        } else {
            LOGGER.severe("El proveedorId es null, no se puede buscar el proveedor.");
            request.setAttribute("errorProveedor", "Error interno: ID de proveedor no válido.");
            mostrarFormularioNuevo(request, response);
            return;
        }

        Empresa empresa = null;
        if (empresaId != null) {
            empresa = empresaDAO.getEmpresaById(empresaId);
            if (empresa == null) {
                LOGGER.log(Level.SEVERE, "No se encontró la empresa con ID: " + empresaId);
                request.setAttribute("errorEmpresa", "No se encontró la empresa seleccionada.");
                mostrarFormularioNuevo(request, response);
                return;
            }
        } else {
            LOGGER.severe("El empresaId es null, no se puede buscar la empresa.");
            request.setAttribute("errorEmpresa", "Error interno: ID de empresa no válido.");
            mostrarFormularioNuevo(request, response);
            return;
        }


        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setAnyosGarantia(anyosGarantia);
        producto.setUnidades(unidades);
        producto.setProveedor(proveedor);
        producto.setEmpresa(empresa);

        boolean guardado = false;
        if (idProducto > 0) {
            producto.setIdProducto(idProducto);
            guardado = productoDAO.updateProducto(producto);
        } else {
            guardado = productoDAO.insertProducto(producto);
        }

        if (guardado) {
            response.sendRedirect("productos?action=list");
        } else {
            LOGGER.log(Level.SEVERE, "Error al guardar el producto.");
            request.setAttribute("errorGuardar", "Hubo un error al intentar guardar el producto.");

            mostrarFormularioNuevo(request, response);
            return;
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        productoDAO.deleteProducto(idProducto);
        response.sendRedirect("productos?action=list");
    }
}