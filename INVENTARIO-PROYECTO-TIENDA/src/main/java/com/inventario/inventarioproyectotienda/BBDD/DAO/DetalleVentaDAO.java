package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.DetalleVenta;
import com.inventario.inventarioproyectotienda.models.Producto;
import com.inventario.inventarioproyectotienda.models.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal; // Asegúrate de tener esta importación

public class DetalleVentaDAO {

    private static final String INSERT_DETALLE_VENTA = "INSERT INTO detalles_venta (cantidad, precio_unidad, ventas_id_venta, productos_id_producto) VALUES (?, ?, ?, ?)";
    private static final String SELECT_DETALLE_VENTA_BY_ID = "SELECT id_detalles_venta, cantidad, precio_unidad, ventas_id_venta, productos_id_producto FROM detalles_venta WHERE id_detalles_venta = ?";
    private static final String SELECT_ALL_DETALLES_VENTA = "SELECT id_detalles_venta, cantidad, precio_unidad, ventas_id_venta, productos_id_producto FROM detalles_venta";
    private static final String UPDATE_DETALLE_VENTA = "UPDATE detalles_venta SET cantidad = ?, precio_unidad = ?, ventas_id_venta = ?, productos_id_producto = ? WHERE id_detalles_venta = ?";
    private static final String DELETE_DETALLE_VENTA = "DELETE FROM detalles_venta WHERE id_detalles_venta = ?";

    private final VentaDAO ventaDAO;
    private final ProductoDAO productoDAO;
    private static final Logger LOGGER = Logger.getLogger(DetalleVentaDAO.class.getName());

    public DetalleVentaDAO() {
        this.ventaDAO = new VentaDAO();
        this.productoDAO = new ProductoDAO();
    }

    public boolean insertDetalleVenta(DetalleVenta detalleVenta) throws SQLException {
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DETALLE_VENTA)) {
            preparedStatement.setInt(1, detalleVenta.getCantidad());
            preparedStatement.setBigDecimal(2, detalleVenta.getPrecioUnidad());
            preparedStatement.setInt(3, detalleVenta.getVenta().getIdVenta());
            preparedStatement.setInt(4, detalleVenta.getProducto().getIdProducto());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el detalle de venta para la venta con ID: " + detalleVenta.getVenta().getIdVenta() + " y producto con ID: " + detalleVenta.getProducto().getIdProducto(), e);
            throw e;
        }
    }

    public DetalleVenta getDetalleVentaById(int id) throws SQLException {
        DetalleVenta detalleVenta = null;
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DETALLE_VENTA_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    detalleVenta = mapResultSetToDetalleVenta(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el detalle de venta con ID: " + id, e);
            throw e;
        }
        return detalleVenta;
    }

    public List<DetalleVenta> getAllDetallesVenta() throws SQLException {
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_DETALLES_VENTA)) {
            while (resultSet.next()) {
                detallesVenta.add(mapResultSetToDetalleVenta(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los detalles de venta", e);
            throw e;
        }
        return detallesVenta;
    }

    public boolean updateDetalleVenta(DetalleVenta detalleVenta) throws SQLException {
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DETALLE_VENTA)) {
            preparedStatement.setInt(1, detalleVenta.getCantidad());
            preparedStatement.setBigDecimal(2, detalleVenta.getPrecioUnidad());
            preparedStatement.setInt(3, detalleVenta.getVenta().getIdVenta());
            preparedStatement.setInt(4, detalleVenta.getProducto().getIdProducto());
            preparedStatement.setInt(5, detalleVenta.getIdDetallesVenta());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el detalle de venta con ID: " + detalleVenta.getIdDetallesVenta(), e);
            throw e;
        }
    }

    public boolean deleteDetalleVenta(int id) throws SQLException {
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DETALLE_VENTA)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el detalle de venta con ID: " + id, e);
            throw e;
        }
    }

    private DetalleVenta mapResultSetToDetalleVenta(ResultSet resultSet) throws SQLException {
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setIdDetallesVenta(resultSet.getInt("id_detalles_venta"));
        detalleVenta.setCantidad(resultSet.getInt("cantidad"));
        detalleVenta.setPrecioUnidad(resultSet.getBigDecimal("precio_unidad"));

        int ventaId = resultSet.getInt("ventas_id_venta");
        Venta venta = ventaDAO.getVentaById(ventaId);
        detalleVenta.setVenta(venta);

        int productoId = resultSet.getInt("productos_id_producto");
        Producto producto = productoDAO.getProductoById(productoId);
        detalleVenta.setProducto(producto);

        return detalleVenta;
    }
}