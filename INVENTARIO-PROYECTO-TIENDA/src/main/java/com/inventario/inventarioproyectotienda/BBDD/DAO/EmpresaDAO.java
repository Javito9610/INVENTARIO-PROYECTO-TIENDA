package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpresaDAO {

    private static final Logger LOGGER = Logger.getLogger(EmpresaDAO.class.getName());

    public Empresa getEmpresaById(int idEmpresa) {
        Empresa empresa = null;
        String sql = "SELECT id_empresa, nombre, direccion, telefono, correo_electronico, descripcion, sitio_web FROM empresas WHERE id_empresa = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEmpresa);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    empresa = mapResultSetToEmpresa(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la empresa con ID: " + idEmpresa, e);
        }
        return empresa;
    }

    public Empresa getEmpresaByNombre(String nombre) {
        Empresa empresa = null;
        String sql = "SELECT id_empresa, nombre, direccion, telefono, correo_electronico, descripcion, sitio_web FROM empresas WHERE nombre = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    empresa = mapResultSetToEmpresa(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la empresa con nombre: " + nombre, e);
        }
        return empresa;
    }

    public List<Empresa> getAllEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT id_empresa, nombre, direccion, telefono, correo_electronico, descripcion, sitio_web FROM empresas";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                empresas.add(mapResultSetToEmpresa(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las empresas", e);
        }
        return empresas;
    }

    public boolean insertEmpresa(Empresa empresa) {
        String sql = "INSERT INTO empresas (nombre, direccion, telefono, correo_electronico, descripcion, sitio_web) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empresa.getNombre());
            statement.setString(2, empresa.getDireccion());
            statement.setString(3, empresa.getTelefono());
            statement.setString(4, empresa.getCorreoElectronico());
            statement.setString(5, empresa.getDescripcion());
            statement.setString(6, empresa.getSitioWeb());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar la empresa con nombre: " + empresa.getNombre(), e);
            return false;
        }
    }

    public boolean updateEmpresa(Empresa empresa) {
        String sql = "UPDATE empresas SET nombre = ?, direccion = ?, telefono = ?, correo_electronico = ?, descripcion = ?, sitio_web = ? WHERE id_empresa = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empresa.getNombre());
            statement.setString(2, empresa.getDireccion());
            statement.setString(3, empresa.getTelefono());
            statement.setString(4, empresa.getCorreoElectronico());
            statement.setString(5, empresa.getDescripcion());
            statement.setString(6, empresa.getSitioWeb());
            statement.setInt(7, empresa.getIdEmpresa());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la empresa con ID: " + empresa.getIdEmpresa(), e);
            return false;
        }
    }

    public boolean deleteEmpresa(int idEmpresa) {
        String sql = "DELETE FROM empresas WHERE id_empresa = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEmpresa);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la empresa con ID: " + idEmpresa, e);
            return false;
        }
    }

    private Empresa mapResultSetToEmpresa(ResultSet resultSet) throws SQLException {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(resultSet.getInt("id_empresa"));
        empresa.setNombre(resultSet.getString("nombre"));
        empresa.setDireccion(resultSet.getString("direccion"));
        empresa.setTelefono(resultSet.getString("telefono"));
        empresa.setCorreoElectronico(resultSet.getString("correo_electronico"));
        empresa.setDescripcion(resultSet.getString("descripcion"));
        empresa.setSitioWeb(resultSet.getString("sitio_web"));
        return empresa;
    }
}