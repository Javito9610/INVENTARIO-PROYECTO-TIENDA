package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.*;
import com.inventario.inventarioproyectotienda.models.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.ArrayList;

@WebServlet(name = "CompraController", urlPatterns = {"/compras"})
public class CompraController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CompraController.class.getName());
    private CompraDAO compraDAO;
    private ProveedorDAO proveedorDAO;
    private EmpresaDAO empresaDAO;
    private ProductoDAO productoDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        compraDAO = new CompraDAO();
        proveedorDAO = new ProveedorDAO();
        empresaDAO = new EmpresaDAO();
        productoDAO = new ProductoDAO();
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
                    eliminarCompra(request, response);
                    break;
                default:
                    listarCompras(request, response);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en doGet de CompraController", e);
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
                guardarCompra(request, response);
            }
        } catch (SQLException | ParseException e) {
            LOGGER.log(Level.SEVERE, "Error en doPost de CompraController", e);
            throw new ServletException(e);
        }
    }

    private void listarCompras(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Compra> listaCompras = compraDAO.getAllCompras();
        request.setAttribute("listaCompras", listaCompras);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-compras.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("Se ha llamado al método mostrarFormularioNuevaCompra");
        List<Proveedor> listaProveedores = proveedorDAO.getAllProveedores();
        List<Producto> listaProductos = productoDAO.getAllProductos();
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        String mensajeAlerta = null;
        String mensajeAlertaParam = request.getParameter("mensajeAlerta");
        if (mensajeAlertaParam != null) {
            mensajeAlerta = java.net.URLDecoder.decode(mensajeAlertaParam, "UTF-8");
        }
        if (listaProveedores.isEmpty()) {
            mensajeAlerta = "No hay proveedores registrados. Debes crear un proveedor antes de poder registrar una compra.";
        } else if (listaEmpresas.isEmpty()) {
            mensajeAlerta = "No hay empresas registradas. Debes crear una empresa antes de poder registrar una compra.";
        }
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("listaProveedores", listaProveedores);
        request.setAttribute("listaProductos", listaProductos);
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("compra-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idCompra = Integer.parseInt(request.getParameter("id"));
        Compra compraAEditar = compraDAO.getCompraById(idCompra);
        List<Proveedor> listaProveedores = proveedorDAO.getAllProveedores();
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        List<Producto> listaProductos = productoDAO.getAllProductos();
        request.setAttribute("compra", compraAEditar);
        request.setAttribute("listaProveedores", listaProveedores);
        request.setAttribute("listaEmpresas", listaEmpresas);
        request.setAttribute("listaProductos", listaProductos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("compra-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarCompra(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        LOGGER.log(Level.INFO, "Received parameters in guardarCompra:");
        request.getParameterMap().forEach((key, value) -> {
            if (value != null && value.length > 0) {
                String valueString = Arrays.toString(value);
                if (valueString.length() > 200) {
                    valueString = valueString.substring(0, 200) + "...";
                }
                LOGGER.log(Level.INFO, "Parameter '" + key + "': " + valueString);
            } else {
                LOGGER.log(Level.INFO, "Parameter '" + key + "': [] (empty or null)");
            }
        });
        String fechaCompraStr = request.getParameter("fecha_compra");
        double montoTotal = Double.parseDouble(request.getParameter("monto_total"));
        int proveedorId = Integer.parseInt(request.getParameter("proveedores_id_proveedores"));
        int empresaId = Integer.parseInt(request.getParameter("empresas_id_empresa"));
        int idCompra = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;
        String[] detalleTipos = request.getParameterValues("detalle_tipo[]");
        String[] productoIdsStr = request.getParameterValues("productos_id_producto[]");
        String[] nuevoProductoNombres = request.getParameterValues("nuevo_producto_nombre[]");
        String[] nuevoProductoDescripciones = request.getParameterValues("nuevo_producto_descripcion[]");
        String[] cantidadesStr = request.getParameterValues("cantidad[]");
        String[] preciosUnidadStr = request.getParameterValues("precio_unidad[]");
        Date fechaCompra = dateFormat.parse(fechaCompraStr);
        Proveedor proveedor = proveedorDAO.getProveedorById(proveedorId);
        Empresa empresa = empresaDAO.getEmpresaById(empresaId);
        if (proveedor == null || empresa == null) {
            LOGGER.log(Level.SEVERE, "Proveedor o Empresa no encontrados al intentar guardar compra.");
            response.sendRedirect("compras?action=new&mensajeAlerta=Error: Proveedor o Empresa no v%C3%A1lidos.");
            return;
        }
        Compra compra = new Compra();
        compra.setFechaCompra(new java.sql.Date(fechaCompra.getTime()));
        compra.setMontoTotal(montoTotal);
        compra.setProveedor(proveedor);
        compra.setEmpresa(empresa);
        if (idCompra > 0) {
            compra.setIdCompra(idCompra);
            compraDAO.updateCompra(compra);
            response.sendRedirect("compras?action=list");
        } else {
            compraDAO.insertCompra(compra);
            int compraId = compra.getIdCompra();
            List<String> erroresDetalle = new ArrayList<>();
            if (detalleTipos != null && detalleTipos.length > 0) {
                int expectedLength = detalleTipos.length;
                boolean arraysAligned = (productoIdsStr != null ? productoIdsStr.length : 0) == expectedLength &&
                        (nuevoProductoNombres != null ? nuevoProductoNombres.length : 0) == expectedLength &&
                        (nuevoProductoDescripciones != null ? nuevoProductoDescripciones.length : 0) == expectedLength &&
                        (cantidadesStr != null ? cantidadesStr.length : 0) == expectedLength &&
                        (preciosUnidadStr != null ? preciosUnidadStr.length : 0) == expectedLength;
                if (!arraysAligned) {
                    erroresDetalle.add("Error interno: Los datos de los detalles del formulario no están completos o alineados.");
                    LOGGER.log(Level.SEVERE, "Desalineación de arrays de detalles en guardarCompra. Longitudes: detalleTipos=" + detalleTipos.length +
                            ", productoIdsStr=" + (productoIdsStr != null ? productoIdsStr.length : 0) + ", nuevoProductoNombres=" + (nuevoProductoNombres != null ? nuevoProductoNombres.length : 0) +
                            ", nuevoProductoDescripciones=" + (nuevoProductoDescripciones != null ? nuevoProductoDescripciones.length : 0) + ", cantidadesStr=" + (cantidadesStr != null ? cantidadesStr.length : 0) +
                            ", preciosUnidadStr=" + (preciosUnidadStr != null ? preciosUnidadStr.length : 0));
                } else {
                    DetalleCompraDAO detalleCompraDAO = new DetalleCompraDAO();
                    for (int i = 0; i < expectedLength; i++) {
                        String detalleTipo = detalleTipos[i];
                        int productoIdParaDetalle = 0;
                        int cantidadComprada = 0;
                        double precioUnitarioComprado = 0.0;
                        String nombreNuevoProducto = null;
                        try {
                            try {
                                cantidadComprada = Integer.parseInt(cantidadesStr[i]);
                                precioUnitarioComprado = Double.parseDouble(preciosUnidadStr[i]);
                            } catch (NumberFormatException e) {
                                erroresDetalle.add("Fila " + (i + 1) + ": Error de formato numérico en cantidad o precio.");
                                LOGGER.log(Level.WARNING, "Error al convertir cantidad o precio unitario en la fila " + (i + 1) + ". Error: " + e.getMessage());
                                continue;
                            }
                            if ("existing".equals(detalleTipo)) {
                                String productoIdExistenteStr = productoIdsStr[i];
                                if (productoIdExistenteStr == null || productoIdExistenteStr.isEmpty()) {
                                    erroresDetalle.add("Fila " + (i + 1) + ": No se seleccionó un producto existente.");
                                    LOGGER.log(Level.WARNING, "Se omitió un detalle de compra existente sin producto seleccionado en la fila " + (i + 1));
                                    continue;
                                }
                                try {
                                    productoIdParaDetalle = Integer.parseInt(productoIdExistenteStr);
                                } catch (NumberFormatException e) {
                                    erroresDetalle.add("Fila " + (i + 1) + ": ID de producto existente inválido.");
                                    LOGGER.log(Level.WARNING, "ID de producto existente inválido en la fila " + (i + 1) + ". Error: " + e.getMessage());
                                    continue;
                                }
                            } else if ("new".equals(detalleTipo)) {
                                nombreNuevoProducto = nuevoProductoNombres[i];
                                String nuevoDescripcion = nuevoProductoDescripciones[i] != null ? nuevoProductoDescripciones[i].trim() : null;
                                double precioNuevoProducto = precioUnitarioComprado;
                                if (nombreNuevoProducto == null || nombreNuevoProducto.trim().isEmpty()) {
                                    erroresDetalle.add("Fila " + (i + 1) + ": El nombre del nuevo producto no puede estar vacío.");
                                    LOGGER.log(Level.WARNING, "Se omitió un detalle de compra para nuevo producto sin nombre en la fila " + (i + 1));
                                    continue;
                                }
                                if (productoDAO.existsProductByNameAndCompany(nombreNuevoProducto.trim(), empresaId)) {
                                    erroresDetalle.add("Fila " + (i + 1) + ": Ya existe un producto con el nombre '" + nombreNuevoProducto.trim() + "' para esta empresa.");
                                    LOGGER.log(Level.WARNING, "Intento de crear producto duplicado: '" + nombreNuevoProducto.trim() + "' en la fila " + (i + 1));
                                    continue;
                                }
                                Producto nuevoProducto = new Producto();
                                nuevoProducto.setNombre(nombreNuevoProducto.trim());
                                nuevoProducto.setDescripcion(nuevoDescripcion);
                                nuevoProducto.setPrecio(precioNuevoProducto);
                                nuevoProducto.setUnidades(0);
                                nuevoProducto.setProveedor(proveedor);
                                nuevoProducto.setEmpresa(empresa);
                                LOGGER.log(Level.INFO, "Intentando insertar nuevo producto: '" + nombreNuevoProducto + "' para detalle en fila " + (i + 1));
                                boolean productInserted = productoDAO.insertProducto(nuevoProducto);
                                if (productInserted) {
                                    productoIdParaDetalle = nuevoProducto.getIdProducto();
                                    if (productoIdParaDetalle <= 0) {
                                        erroresDetalle.add("Fila " + (i + 1) + ": Error interno al obtener ID del nuevo producto.");
                                        LOGGER.log(Level.SEVERE, "ProductoDAO.insertProducto no devolvió un ID válido para el nuevo producto '" + nombreNuevoProducto + "' en la fila " + (i + 1));
                                        continue;
                                    }
                                    LOGGER.log(Level.INFO, "Nuevo producto insertado exitosamente con ID: " + productoIdParaDetalle + " para detalle en fila " + (i + 1));
                                } else {
                                    erroresDetalle.add("Fila " + (i + 1) + ": Falló la inserción del nuevo producto.");
                                    LOGGER.log(Level.SEVERE, "ProductoDAO.insertProducto falló para el nuevo producto: '" + nombreNuevoProducto + "' en la fila " + (i + 1) + ". Omitiendo detalle de compra.");
                                    continue;
                                }
                            } else {
                                erroresDetalle.add("Fila " + (i + 1) + ": Tipo de detalle desconocido.");
                                LOGGER.log(Level.WARNING, "Tipo de detalle desconocido o inválido ('" + detalleTipos[i] + "') en la fila " + (i + 1) + ". Omitiendo detalle.");
                                continue;
                            }
                            if (productoIdParaDetalle > 0) {
                                DetalleCompra detalleCompra = new DetalleCompra();
                                Compra compraParaDetalle = new Compra();
                                compraParaDetalle.setIdCompra(compraId);
                                detalleCompra.setCompra(compraParaDetalle);
                                Producto productoParaDetalle = new Producto();
                                productoParaDetalle.setIdProducto(productoIdParaDetalle);
                                detalleCompra.setProducto(productoParaDetalle);
                                detalleCompra.setCantidad(cantidadComprada);
                                detalleCompra.setPrecioUnidad(precioUnitarioComprado);
                                detalleCompra.setEmpresa(empresa);
                                LOGGER.log(Level.INFO, "Intentando insertar detalle para producto ID: " + productoIdParaDetalle + " (Cantidad: " + cantidadComprada + ", Precio: " + precioUnitarioComprado + ")");
                                boolean detailInserted = detalleCompraDAO.insertDetalleCompra(detalleCompra);
                                if (detailInserted) {
                                    LOGGER.log(Level.INFO, "Detalle de compra insertado exitosamente para producto ID: " + productoIdParaDetalle);
                                    LOGGER.log(Level.INFO, "Actualizando stock para producto ID: " + productoIdParaDetalle + " con cantidad comprada: " + cantidadComprada);
                                    boolean stockUpdated = productoDAO.updateStock(productoIdParaDetalle, cantidadComprada);
                                    if (!stockUpdated) {
                                        LOGGER.log(Level.WARNING, "No se pudo actualizar el stock para el producto con ID: " + productoIdParaDetalle + " después de insertar el detalle de compra.");
                                    } else {
                                        LOGGER.log(Level.INFO, "Stock actualizado exitosamente para producto ID: " + productoIdParaDetalle);
                                    }
                                } else {
                                    erroresDetalle.add("Fila " + (i + 1) + ": Falló la inserción del detalle de compra.");
                                    LOGGER.log(Level.WARNING, "El detalle de compra para el producto con ID: " + productoIdParaDetalle + " no se insertó correctamente. Stock no actualizado.");
                                }
                            }
                        } catch (SQLException e) {
                            erroresDetalle.add("Fila " + (i + 1) + ": Error de base de datos al procesar detalle.");
                            LOGGER.log(Level.SEVERE, "Error de base de datos al procesar detalle de compra (fila " + (i + 1) + ").", e);
                        }
                    }
                }
            } else {
                erroresDetalle.add("No se recibieron detalles de compra.");
                LOGGER.log(Level.WARNING, "No se recibieron detalles de compra.");
            }

            if (!erroresDetalle.isEmpty()) {
                String errorMsg = "Errores al procesar detalles de compra:\\n- " + String.join("\\n- ", erroresDetalle);
                String encodedErrorMsg = java.net.URLEncoder.encode(errorMsg, "UTF-8");
                response.sendRedirect("compras?action=new&mensajeAlerta=" + encodedErrorMsg);
                return;
            }

            if (detalleTipos != null && detalleTipos.length > 0 && erroresDetalle.isEmpty()) {
                response.sendRedirect("compras?action=list");
            } else {
                response.sendRedirect("compras?action=list");
            }
        }
    }

    private void eliminarCompra(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idCompra = Integer.parseInt(request.getParameter("id"));
        compraDAO.deleteCompra(idCompra);
        response.sendRedirect("compras?action=list");
    }
}