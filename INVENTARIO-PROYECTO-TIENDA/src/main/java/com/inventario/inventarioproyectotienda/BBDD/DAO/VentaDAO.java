package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Cliente;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaDAO {

    private static final Logger LOGGER = Logger.getLogger(VentaDAO.class.getName());

    public Venta getVentaById(int idVenta) {
        Venta venta = null;
        String sql = "SELECT id_venta, fecha_venta, monto_total, empresas_id_empresa, clientes_dni FROM ventas WHERE id_venta = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    venta = mapResultSetToVenta(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la venta con ID: " + idVenta, e);
        }
        return venta;
    }

    public List<Venta> getAllVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT id_venta, fecha_venta, monto_total, empresas_id_empresa, clientes_dni FROM ventas";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ventas.add(mapResultSetToVenta(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las ventas", e);
        }
        return ventas;
    }

    public boolean insertVenta(Venta venta) {
        String sql = "INSERT INTO ventas (fecha_venta, monto_total, empresas_id_empresa, clientes_dni) VALUES (?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new java.sql.Date(venta.getFechaVenta().getTime()));
            statement.setDouble(2, venta.getMontoTotal());
            statement.setInt(3, venta.getEmpresa().getIdEmpresa());
            statement.setString(4, venta.getCliente().getDni());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        venta.setIdVenta(generatedKeys.getInt(1));
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error al obtener el ID generado para la venta con fecha: " + venta.getFechaVenta(), e);
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar la venta con fecha: " + venta.getFechaVenta(), e);
            return false;
        }
    }
    public boolean updateVenta(Venta venta) {
        String sql = "UPDATE ventas SET fecha_venta = ?, monto_total = ?, empresas_id_empresa = ?, clientes_dni = ? WHERE id_venta = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(venta.getFechaVenta().getTime()));
            statement.setDouble(2, venta.getMontoTotal());
            statement.setInt(3, venta.getEmpresa().getIdEmpresa());
            statement.setString(4, venta.getCliente().getDni());
            statement.setInt(5, venta.getIdVenta());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la venta con ID: " + venta.getIdVenta(), e);
            return false;
        }
    }

    public boolean deleteVenta(int idVenta) {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVenta);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la venta con ID: " + idVenta, e);
            return false;
        }
    }

    private Venta mapResultSetToVenta(ResultSet resultSet) throws SQLException {
        Venta venta = new Venta();
        venta.setIdVenta(resultSet.getInt("id_venta"));
        venta.setFechaVenta(resultSet.getDate("fecha_venta"));
        venta.setMontoTotal(resultSet.getDouble("monto_total"));
        int empresaId = resultSet.getInt("empresas_id_empresa");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.getEmpresaById(empresaId);
        venta.setEmpresa(empresa);
        String clienteDni = resultSet.getString("clientes_dni");
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.getClienteByDni(clienteDni);
        venta.setCliente(cliente);
        return venta;
    }
}