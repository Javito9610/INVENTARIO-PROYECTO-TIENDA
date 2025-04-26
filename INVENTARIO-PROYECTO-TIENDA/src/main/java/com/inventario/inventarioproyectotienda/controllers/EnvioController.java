package com.inventario.inventarioproyectotienda.controllers;

import com.inventario.inventarioproyectotienda.BBDD.DAO.DetalleVentaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.EmpresaDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.EnvioDAO;
import com.inventario.inventarioproyectotienda.BBDD.DAO.VentaDAO; // Importamos VentaDAO
import com.inventario.inventarioproyectotienda.models.DetalleVenta;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Envio;
import com.inventario.inventarioproyectotienda.models.Venta; // Importamos el modelo Venta
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

@WebServlet(name = "EnvioController", urlPatterns = {"/envios"})
public class EnvioController extends HttpServlet {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    eliminarEnvio(request, response);
                    break;
                default:
                    listarEnvios(request, response);
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
                guardarEnvio(request, response);
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException(e);
        }
    }

    private void listarEnvios(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        EnvioDAO envioDAO = new EnvioDAO();
        List<Envio> listaEnvios = envioDAO.getAllEnvios();
        request.setAttribute("listaEnvios", listaEnvios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-envios.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("Se ha llamado al método mostrarFormularioNuevoEnvio");
        VentaDAO ventaDAO = new VentaDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();

        List<Venta> listaVentas = ventaDAO.getAllVentas();
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        List<DetalleVenta> listaDetallesVenta = detalleVentaDAO.getAllDetallesVenta();

        String mensajeAlerta = null;

        if (listaDetallesVenta.isEmpty()) {
            mensajeAlerta = "No hay detalles de venta registrados. Debes crear una venta y sus detalles antes de poder registrar un envío.";
        } else if (listaEmpresas.isEmpty()) {
            mensajeAlerta = "No hay empresas registradas. Debes crear una empresa antes de poder registrar un envío.";
        }

        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.setAttribute("listaVentas", listaVentas);
        request.setAttribute("listaEmpresas", listaEmpresas);
        request.setAttribute("listaDetallesVenta", listaDetallesVenta);

        RequestDispatcher dispatcher = request.getRequestDispatcher("envio-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idEnvio = Integer.parseInt(request.getParameter("id"));
        EnvioDAO envioDAO = new EnvioDAO();
        VentaDAO ventaDAO = new VentaDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Envio envioAEditar = envioDAO.getEnvioById(idEnvio);
        List<Venta> listaVentas = ventaDAO.getAllVentas();
        List<Empresa> listaEmpresas = empresaDAO.getAllEmpresas();
        request.setAttribute("envio", envioAEditar);
        request.setAttribute("listaVentas", listaVentas);
        request.setAttribute("listaEmpresas", listaEmpresas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("envio-form.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarEnvio(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        String fechaEnvioStr = request.getParameter("fecha_envio");
        String fechaEntregaStr = request.getParameter("fecha_entrega");
        String direccionEnvio = request.getParameter("direccion_envio");
        String ciudadEnvio = request.getParameter("ciudad_envio");
        String codigoPostalEnvio = request.getParameter("codigo_postal_envio");
        String estadoEnvio = request.getParameter("estado_envio");
        int detalleVentaId = Integer.parseInt(request.getParameter("detalles_venta_id_detalles_venta"));
        int empresaId = Integer.parseInt(request.getParameter("empresas_id_empresa"));
        int idEnvio = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id"))
                : 0;

        Date fechaEnvio = dateFormat.parse(fechaEnvioStr);
        Date fechaEntrega = dateFormat.parse(fechaEntregaStr);


        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
        DetalleVenta detalleVenta = detalleVentaDAO.getDetalleVentaById(detalleVentaId);

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.getEmpresaById(empresaId);

        EnvioDAO envioDAO = new EnvioDAO();
        Envio envio = new Envio();
        envio.setFechaEnvio(new java.sql.Date(fechaEnvio.getTime()));
        envio.setFechaEntrega(new java.sql.Date(fechaEntrega.getTime()));
        envio.setDireccionEnvio(direccionEnvio);
        envio.setCiudadEnvio(ciudadEnvio);
        envio.setCodigoPostalEnvio(codigoPostalEnvio);
        envio.setEstadoEnvio(estadoEnvio);
        envio.setDetalleVenta(detalleVenta);
        envio.setEmpresa(empresa);

        if (idEnvio > 0) {
            envio.setIdEnvio(idEnvio);
            envioDAO.updateEnvio(envio);
        } else {
            envioDAO.insertEnvio(envio);
        }
        response.sendRedirect("envios?action=list");
    }

    private void eliminarEnvio(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idEnvio = Integer.parseInt(request.getParameter("id"));
        EnvioDAO envioDAO = new EnvioDAO();
        envioDAO.deleteEnvio(idEnvio);
        response.sendRedirect("envios?action=list");
    }
}
