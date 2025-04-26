package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Compra;
import com.inventario.inventarioproyectotienda.models.DetalleCompra;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetalleCompraDAO {

    private static final Logger LOGGER = Logger.getLogger(DetalleCompraDAO.class.getName());

    public DetalleCompra getDetalleCompraById(int idDetallesCompra) {
        DetalleCompra detalleCompra = null;
        String sql = "SELECT dc.id_detalles_compra, dc.cantidad, dc.precio_unidad, " +
                "c.id_compra, " +
                "p.id_producto, " +
                "e.id_empresa " +
                "FROM detalles_compra dc " +
                "JOIN compras c ON dc.compras_id_compra = c.id_compra " +
                "JOIN productos p ON dc.productos_id_producto = p.id_producto " +
                "JOIN empresas e ON p.empresas_id_empresa = e.id_empresa " +
                "WHERE dc.id_detalles_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDetallesCompra);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    detalleCompra = mapResultSetToDetalleCompra(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el detalle de compra con ID: " + idDetallesCompra, e);
        }
        return detalleCompra;
    }

    public List<DetalleCompra> getAllDetallesCompra() {
        List<DetalleCompra> detallesCompraList = new ArrayList<>();
        String sql = "SELECT dc.id_detalles_compra, dc.cantidad, dc.precio_unidad, " +
                "c.id_compra, " +
                "p.id_producto, " +
                "e.id_empresa " +
                "FROM detalles_compra dc " +
                "JOIN compras c ON dc.compras_id_compra = c.id_compra " +
                "JOIN productos p ON dc.productos_id_producto = p.id_producto " +
                "JOIN empresas e ON p.empresas_id_empresa = e.id_empresa";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                detallesCompraList.add(mapResultSetToDetalleCompra(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los detalles de compra", e);
        }
        return detallesCompraList;
    }

    public List<DetalleCompra> getDetallesCompraByCompraId(int compraId) {
        List<DetalleCompra> detallesCompraList = new ArrayList<>();
        String sql = "SELECT dc.id_detalles_compra, dc.cantidad, dc.precio_unidad, " +
                "c.id_compra, " +
                "p.id_producto, " +
                "e.id_empresa " +
                "FROM detalles_compra dc " +
                "JOIN compras c ON dc.compras_id_compra = c.id_compra " +
                "JOIN productos p ON dc.productos_id_producto = p.id_producto " +
                "JOIN empresas e ON p.empresas_id_empresa = e.id_empresa " +
                "WHERE c.id_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, compraId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    detallesCompraList.add(mapResultSetToDetalleCompra(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los detalles de compra para la compra con ID: " + compraId, e);
        }
        return detallesCompraList;
    }

    public boolean insertDetalleCompra(DetalleCompra detalleCompra) throws SQLException {
        String sql = "INSERT INTO detalles_compra (compras_id_compra, productos_id_producto, cantidad, precio_unidad, empresas_id_empresa) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, detalleCompra.getCompra().getIdCompra());
            statement.setInt(2, detalleCompra.getProducto().getIdProducto());
            statement.setInt(3, detalleCompra.getCantidad());
            statement.setDouble(4, detalleCompra.getPrecioUnidad());
            statement.setInt(5, detalleCompra.getEmpresa().getIdEmpresa());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el detalle de compra para la compra con ID: " + detalleCompra.getCompra().getIdCompra() + " y producto ID: " + detalleCompra.getProducto().getIdProducto(), e);
            throw e;
        }
    }

    public boolean updateDetalleCompra(DetalleCompra detalleCompra) {
        String sql = "UPDATE detalles_compra SET cantidad = ?, precio_unidad = ? WHERE id_detalles_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, detalleCompra.getCantidad());
            statement.setDouble(2, detalleCompra.getPrecioUnidad());
            statement.setInt(3, detalleCompra.getIdDetallesCompra());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el detalle de compra con ID: " + detalleCompra.getIdDetallesCompra(), e);
            return false;
        }
    }

    public boolean deleteDetalleCompra(int idDetallesCompra) {
        String sql = "DELETE FROM detalles_compra WHERE id_detalles_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDetallesCompra);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el detalle de compra con ID: " + idDetallesCompra, e);
            return false;
        }
    }

    private DetalleCompra mapResultSetToDetalleCompra(ResultSet resultSet) throws SQLException {
        DetalleCompra detalleCompra = new DetalleCompra();
        detalleCompra.setIdDetallesCompra(resultSet.getInt("id_detalles_compra"));
        detalleCompra.setCantidad(resultSet.getInt("cantidad"));
        detalleCompra.setPrecioUnidad(resultSet.getDouble("precio_unidad"));

        Compra compra = new Compra();
        compra.setIdCompra(resultSet.getInt("id_compra"));
        detalleCompra.setCompra(compra);

        Producto producto = new Producto();
        producto.setIdProducto(resultSet.getInt("id_producto"));
        detalleCompra.setProducto(producto);

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(resultSet.getInt("id_empresa"));
        detalleCompra.setEmpresa(empresa);

        return detalleCompra;
    }
}