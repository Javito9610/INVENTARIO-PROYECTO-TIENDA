package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompraDAO {

    private static final Logger LOGGER = Logger.getLogger(CompraDAO.class.getName());

    public Compra getCompraById(int idCompra) {
        Compra compra = null;
        String sql = "SELECT id_compra, fecha_compra, fecha_devolucion, monto_total, empresas_id_empresa, proveedores_id_proveedores FROM compras WHERE id_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCompra);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    compra = new Compra();
                    compra.setIdCompra(resultSet.getInt("id_compra"));
                    compra.setFechaCompra(resultSet.getDate("fecha_compra"));
                    compra.setFechaDevolucion(resultSet.getDate("fecha_devolucion"));
                    compra.setMontoTotal(resultSet.getDouble("monto_total"));
                    int idEmpresa = resultSet.getInt("empresas_id_empresa");
                    EmpresaDAO empresaDAO = new EmpresaDAO();
                    Empresa empresa = empresaDAO.getEmpresaById(idEmpresa);
                    compra.setEmpresa(empresa);
                    int idProveedor = resultSet.getInt("proveedores_id_proveedores");
                    ProveedorDAO proveedorDAO = new ProveedorDAO();
                    Proveedor proveedor = proveedorDAO.getProveedorById(idProveedor);
                    compra.setProveedor(proveedor);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la compra con ID: " + idCompra, e);
        }
        return compra;
    }

    public List<Compra> getAllCompras() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT id_compra, fecha_compra, fecha_devolucion, monto_total, empresas_id_empresa, proveedores_id_proveedores FROM compras";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Compra compra = new Compra();
                compra.setIdCompra(resultSet.getInt("id_compra"));
                compra.setFechaCompra(resultSet.getDate("fecha_compra"));
                compra.setFechaDevolucion(resultSet.getDate("fecha_devolucion"));
                compra.setMontoTotal(resultSet.getDouble("monto_total"));
                int idEmpresa = resultSet.getInt("empresas_id_empresa");
                EmpresaDAO empresaDAO = new EmpresaDAO();
                Empresa empresa = empresaDAO.getEmpresaById(idEmpresa);
                compra.setEmpresa(empresa);
                int idProveedor = resultSet.getInt("proveedores_id_proveedores");
                ProveedorDAO proveedorDAO = new ProveedorDAO();
                Proveedor proveedor = proveedorDAO.getProveedorById(idProveedor);
                compra.setProveedor(proveedor);
                compras.add(compra);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las compras", e);
        }
        return compras;
    }

    public boolean insertCompra(Compra compra) {
        String sql = "INSERT INTO compras (fecha_compra, fecha_devolucion, monto_total, empresas_id_empresa, proveedores_id_proveedores) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new java.sql.Date(compra.getFechaCompra().getTime()));
            statement.setDate(2, (compra.getFechaDevolucion() != null) ? new java.sql.Date(compra.getFechaDevolucion().getTime()) : null);
            statement.setDouble(3, compra.getMontoTotal());
            statement.setInt(4, compra.getEmpresa().getIdEmpresa());
            statement.setInt(5, compra.getProveedor().getIdProveedores());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        compra.setIdCompra(generatedKeys.getInt(1));
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error al obtener el ID generado para la compra con fecha: " + compra.getFechaCompra(), e);
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar la compra con fecha: " + compra.getFechaCompra(), e);
            return false;
        }
    }

    public boolean updateCompra(Compra compra) {
        String sql = "UPDATE compras SET fecha_compra = ?, fecha_devolucion = ?, monto_total = ?, empresas_id_empresa = ?, proveedores_id_proveedores = ? WHERE id_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(compra.getFechaCompra().getTime()));
            statement.setDate(2, (compra.getFechaDevolucion() != null) ? new java.sql.Date(compra.getFechaDevolucion().getTime()) : null);
            statement.setDouble(3, compra.getMontoTotal());
            statement.setInt(4, compra.getEmpresa().getIdEmpresa());
            statement.setInt(5, compra.getProveedor().getIdProveedores());
            statement.setInt(6, compra.getIdCompra());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la compra con ID: " + compra.getIdCompra(), e);
            return false;
        }
    }

    public boolean deleteCompra(int idCompra) {
        String sql = "DELETE FROM compras WHERE id_compra = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCompra);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la compra con ID: " + idCompra, e);
            return false;
        }
    }
}