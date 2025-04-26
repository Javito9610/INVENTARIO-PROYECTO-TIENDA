package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.ClienteDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.DetalleVentaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.ProductoDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.VentaDAO;
import com.inventario.inventarioproyectotienda.models.Cliente;
import com.inventario.inventarioproyectotienda.models.DetalleVenta;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Producto;
import com.inventario.inventarioproyectotienda.models.Venta;
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

@WebServlet(name = "VentaController", urlPatterns = {"/ventas"})
public class VentaController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(VentaController.class.getName());

    private VentaDAO ventaDAO;
    private EmpresaDAO empresaDAO;
    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        ventaDAO = new VentaDAO();
        empresaDAO = new EmpresaDAO();
        clienteDAO = new ClienteDAO();
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
                    eliminarVenta(request, response);
                    break;
                default:
                    listarVentas(request, response);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en doGet de VentaController", e);
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
                guardarVenta(request, response);
            }
        } catch (SQLException | ParseException e) {
            LOGGER.log(Level.SEVERE, "Error en doPost de VentaController", e);
            throw new ServletException(e);
        }
    }

    private void listarVentas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Venta> listaVentas = ventaDAO.getAllVentas();
        request.setAttribute("listaVentas", listaVentas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-ventas.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("Se ha llamado al método mostrarFormularioNuevo");
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        List<Cliente> listaClientes = clienteDAO.getAllClientes();
        List<Producto> listaProductos = productoDAO.getAllProductos();

        String mensajeAlerta = null;

        if (listaEmpresas.isEmpty()) {
            mensajeAlerta = "No hay empresas registradas. Debes crear una empresa antes de poder registrar una venta.";
        } else if (listaClientes.isEmpty()) {
            mensajeAlerta = "No hay clientes registrados. Debes crear un cliente antes de poder registrar una venta.";
        } else if (listaProductos.isEmpty()) {
            mensajeAlerta = "No hay productos registrados. Debes crear al menos un producto antes de poder registrar una venta.";
        } else if (listaEmpresas.isEmpty() && listaClientes.isEmpty()) {
            mensajeAlerta = "No hay empresas ni clientes registrados. Debes crear al menos uno de cada para poder registrar una venta.";
        } else if (listaEmpresas.isEmpty() && listaProductos.isEmpty()) {
            mensajeAlerta = "No hay empresas ni productos registrados. Debes crear al menos uno de cada para poder registrar una venta.";
        } else if (listaClientes.isEmpty() && listaProductos.isEmpty()) {
            mensajeAlerta = "No hay clientes ni productos registrados. Debes crear al menos uno de cada para poder registrar una venta.";
        } else if (listaEmpresas.isEmpty() && listaClientes.isEmpty() && listaProductos.isEmpty()) {
            mensajeAlerta = "No hay empresas, clientes ni productos registrados. Debes crear al menos uno de cada para poder registrar una venta.";
        }

        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("listaEmpresas", listaEmpresas);
        request.setAttribute("listaClientes", listaClientes);
        request.setAttribute("listaProductos", listaProductos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("venta-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idVenta = Integer.parseInt(request.getParameter("id"));
        Venta ventaAEditar = ventaDAO.getVentaById(idVenta);
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        List<Cliente> listaClientes = clienteDAO.getAllClientes();
        request.setAttribute("venta", ventaAEditar);
        request.setAttribute("listaEmpresas", listaEmpresas);
        request.setAttribute("listaClientes", listaClientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("venta-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarVenta(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        String fechaVentaStr = request.getParameter("fecha_venta");
        double montoTotal = Double.parseDouble(request.getParameter("monto_total"));
        int empresaId = Integer.parseInt(request.getParameter("empresas_id_empresa"));
        String clienteDni = request.getParameter("clientes_dni");
        int idVenta = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;

        Date fechaVenta = dateFormat.parse(fechaVentaStr);
        Empresa empresa = empresaDAO.getEmpresaById(empresaId);
        Cliente cliente = clienteDAO.getClienteByDni(clienteDni);

        Venta venta = new Venta();
        venta.setFechaVenta(new java.sql.Date(fechaVenta.getTime()));
        venta.setMontoTotal(montoTotal);
        venta.setEmpresa(empresa);
        venta.setCliente(cliente);

        if (idVenta > 0) {
            venta.setIdVenta(idVenta);
            ventaDAO.updateVenta(venta);
        } else {
            ventaDAO.insertVenta(venta);
            int ventaId = venta.getIdVenta();

            String[] productoIds = request.getParameterValues("producto_id");
            String[] cantidades = request.getParameterValues("cantidad");
            String[] preciosUnitarios = request.getParameterValues("precio_unitario");

            if (productoIds != null && cantidades != null && preciosUnitarios != null &&
                    productoIds.length == cantidades.length && cantidades.length == preciosUnitarios.length) {
                DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
                for (int i = 0; i < productoIds.length; i++) {
                    try {
                        if (productoIds[i] == null || productoIds[i].isEmpty()) {
                            LOGGER.log(Level.WARNING, "Se omitió un detalle de venta debido a un producto no seleccionado en la fila " + (i + 1));
                            continue;
                        }

                        int productoId = Integer.parseInt(productoIds[i]);
                        int cantidad = Integer.parseInt(cantidades[i]);
                        java.math.BigDecimal precioUnitario = new java.math.BigDecimal(preciosUnitarios[i]);

                        Producto producto = productoDAO.getProductoById(productoId);

                        if (producto != null) {
                            DetalleVenta detalleVenta = new DetalleVenta();
                            Venta ventaCreada = new Venta();
                            ventaCreada.setIdVenta(ventaId);
                            detalleVenta.setVenta(ventaCreada);
                            detalleVenta.setProducto(producto);
                            detalleVenta.setCantidad(cantidad);
                            detalleVenta.setPrecioUnidad(precioUnitario);

                            boolean detailInserted = detalleVentaDAO.insertDetalleVenta(detalleVenta);

                            if (detailInserted) {
                                // Actualizar stock: disminuir por la cantidad vendida
                                boolean stockUpdated = productoDAO.updateStock(productoId, -cantidad); // Nota el signo negativo
                                if (!stockUpdated) {
                                    LOGGER.log(Level.WARNING, "No se pudo actualizar el stock para el producto con ID: " + productoId + " después de insertar el detalle de venta.");
                                }
                            } else {
                                LOGGER.log(Level.WARNING, "El detalle de venta para el producto con ID: " + productoId + " no se insertó correctamente.");
                            }

                        } else {
                            LOGGER.log(Level.WARNING, "Producto con ID " + productoId + " no encontrado para el detalle de venta.");
                        }

                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.WARNING, "Error al convertir los datos numéricos de un detalle de venta: " + e.getMessage() + " en la fila " + (i + 1));
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error de base de datos al insertar detalle de venta o actualizar stock.", e);
                        throw e;
                    }
                }
            } else {
                LOGGER.log(Level.WARNING, "No se recibieron detalles de venta válidos o los arrays tienen longitudes diferentes.");
            }
        }
        response.sendRedirect("ventas?action=list");
    }

    private void eliminarVenta(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idVenta = Integer.parseInt(request.getParameter("id"));
        ventaDAO.deleteVenta(idVenta);
        response.sendRedirect("ventas?action=list");
    }
}