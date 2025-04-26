package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Envio;
import com.inventario.inventarioproyectotienda.models.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvioDAO {

    private static final String INSERT_ENVIO = "INSERT INTO envios (fecha_envio, fecha_entrega, direccion_envio, ciudad_envio, codigo_postal_envio, estado_envio, detalles_venta_id_detalles_venta, empresas_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ENVIO_BY_ID = "SELECT id_envio, fecha_envio, fecha_entrega, direccion_envio, ciudad_envio, codigo_postal_envio, estado_envio, detalles_venta_id_detalles_venta, empresas_id_empresa FROM envios WHERE id_envio = ?";
    private static final String SELECT_ALL_ENVIOS = "SELECT id_envio, fecha_envio, fecha_entrega, direccion_envio, ciudad_envio, codigo_postal_envio, estado_envio, detalles_venta_id_detalles_venta, empresas_id_empresa FROM envios";
    private static final String UPDATE_ENVIO = "UPDATE envios SET fecha_envio = ?, fecha_entrega = ?, direccion_envio = ?, ciudad_envio = ?, codigo_postal_envio = ?, estado_envio = ?, detalles_venta_id_detalles_venta = ?, empresas_id_empresa = ? WHERE id_envio = ?";
    private static final String DELETE_ENVIO = "DELETE FROM envios WHERE id_envio = ?";

    private final EmpresaDAO empresaDAO;
    private final DetalleVentaDAO detalleVentaDAO;
    private static final Logger LOGGER = Logger.getLogger(EnvioDAO.class.getName());

    public EnvioDAO() {
        this.empresaDAO = new EmpresaDAO();
        this.detalleVentaDAO = new DetalleVentaDAO();
    }

    public boolean insertEnvio(Envio envio) throws SQLException {
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ENVIO)) {
            preparedStatement.setDate(1, new Date(envio.getFechaEnvio().getTime()));
            preparedStatement.setDate(2, new Date(envio.getFechaEntrega().getTime()));
            preparedStatement.setString(3, envio.getDireccionEnvio());
            preparedStatement.setString(4, envio.getCiudadEnvio());
            preparedStatement.setString(5, envio.getCodigoPostalEnvio());
            preparedStatement.setString(6, envio.getEstadoEnvio());
            preparedStatement.setInt(7, envio.getDetalleVenta().getIdDetallesVenta());
            preparedStatement.setInt(8, envio.getEmpresa().getIdEmpresa());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el envío para el detalle de venta con ID: " + envio.getDetalleVenta().getIdDetallesVenta(), e);
            throw e;
        }
    }

    public Envio getEnvioById(int id) throws SQLException {
        Envio envio = null;
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ENVIO_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    envio = mapEnvio(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el envío con ID: " + id, e);
            throw e;
        }
        return envio;
    }

    public List<Envio> getAllEnvios() throws SQLException {
        List<Envio> envios = new ArrayList<>();
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_ENVIOS)) {
            while (resultSet.next()) {
                envios.add(mapEnvio(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los envíos", e);
            throw e;
        }
        return envios;
    }

    public boolean updateEnvio(Envio envio) throws SQLException {
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ENVIO)) {
            preparedStatement.setDate(1, new Date(envio.getFechaEnvio().getTime()));
            preparedStatement.setDate(2, new Date(envio.getFechaEntrega().getTime()));
            preparedStatement.setString(3, envio.getDireccionEnvio());
            preparedStatement.setString(4, envio.getCiudadEnvio());
            preparedStatement.setString(5, envio.getCodigoPostalEnvio());
            preparedStatement.setString(6, envio.getEstadoEnvio());
            preparedStatement.setInt(7, envio.getDetalleVentaId());
            preparedStatement.setInt(8, envio.getEmpresa().getIdEmpresa());
            preparedStatement.setInt(9, envio.getIdEnvio());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el envío con ID: " + envio.getIdEnvio(), e);
            throw e;
        }
    }

    public boolean deleteEnvio(int id) throws SQLException {
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ENVIO)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el envío con ID: " + id, e);
            throw e;
        }
    }

    private Envio mapEnvio(ResultSet resultSet) throws SQLException {
        Envio envio = new Envio();
        envio.setIdEnvio(resultSet.getInt("id_envio"));
        envio.setFechaEnvio(resultSet.getDate("fecha_envio"));
        envio.setFechaEntrega(resultSet.getDate("fecha_entrega"));
        envio.setDireccionEnvio(resultSet.getString("direccion_envio"));
        envio.setCiudadEnvio(resultSet.getString("ciudad_envio"));
        envio.setCodigoPostalEnvio(resultSet.getString("codigo_postal_envio"));
        envio.setEstadoEnvio(resultSet.getString("estado_envio"));
        int detalleVentaId = resultSet.getInt("detalles_venta_id_detalles_venta");
        envio.setDetalleVentaId(detalleVentaId);
        DetalleVenta detalleVenta = detalleVentaDAO.getDetalleVentaById(detalleVentaId);
        envio.setDetalleVenta(detalleVenta);
        int empresaId = resultSet.getInt("empresas_id_empresa");
        Empresa empresa = empresaDAO.getEmpresaById(empresaId);
        envio.setEmpresa(empresa);
        return envio;
    }
}