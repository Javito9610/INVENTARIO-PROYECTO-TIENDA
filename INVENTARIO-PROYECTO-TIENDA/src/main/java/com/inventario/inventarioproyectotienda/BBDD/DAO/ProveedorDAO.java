package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProveedorDAO {

    private static final Logger LOGGER = Logger.getLogger(ProveedorDAO.class.getName());

    public Proveedor getProveedorById(int idProveedores) {
        Proveedor proveedor = null;
        String sql = "SELECT id_proveedores, nombre, contacto, telefono, direccion, ciudad, provincia, codigo_postal, pais, rfc_nit, tipo, sitio_web, fecha_registro, empresas_id_empresa FROM proveedores WHERE id_proveedores = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProveedores);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    proveedor = mapResultSetToProveedor(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el proveedor con ID: " + idProveedores, e);
        }
        return proveedor;
    }

    public List<Proveedor> getAllProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT id_proveedores, nombre, contacto, telefono, direccion, ciudad, provincia, codigo_postal, pais, rfc_nit, tipo, sitio_web, fecha_registro, empresas_id_empresa FROM proveedores";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                proveedores.add(mapResultSetToProveedor(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los proveedores", e);
        }
        return proveedores;
    }

    public boolean insertProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (nombre, contacto, telefono, direccion, ciudad, provincia, codigo_postal, pais, rfc_nit, tipo, sitio_web, fecha_registro, empresas_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getContacto());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getDireccion());
            statement.setString(5, proveedor.getCiudad());
            statement.setString(6, proveedor.getProvincia());
            statement.setString(7, proveedor.getCodigoPostal());
            statement.setString(8, proveedor.getPais());
            statement.setString(9, proveedor.getRfcNit());
            statement.setString(10, proveedor.getTipo());
            statement.setString(11, proveedor.getSitioWeb());
            statement.setDate(12, (proveedor.getFechaRegistro() != null) ? new java.sql.Date(proveedor.getFechaRegistro().getTime()) : null);
            statement.setInt(13, proveedor.getEmpresa().getIdEmpresa());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el proveedor con nombre: " + proveedor.getNombre(), e);
            return false;
        }
    }

    public boolean updateProveedor(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre = ?, contacto = ?, telefono = ?, direccion = ?, ciudad = ?, provincia = ?, codigo_postal = ?, pais = ?, rfc_nit = ?, tipo = ?, sitio_web = ?, fecha_registro = ?, empresas_id_empresa = ? WHERE id_proveedores = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getContacto());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getDireccion());
            statement.setString(5, proveedor.getCiudad());
            statement.setString(6, proveedor.getProvincia());
            statement.setString(7, proveedor.getCodigoPostal());
            statement.setString(8, proveedor.getPais());
            statement.setString(9, proveedor.getRfcNit());
            statement.setString(10, proveedor.getTipo());
            statement.setString(11, proveedor.getSitioWeb());
            statement.setDate(12, new java.sql.Date(proveedor.getFechaRegistro().getTime()));
            statement.setInt(13, proveedor.getEmpresa().getIdEmpresa());
            statement.setInt(14, proveedor.getIdProveedores());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el proveedor con ID: " + proveedor.getIdProveedores(), e);
            return false;
        }
    }

    public boolean deleteProveedor(int idProveedores) {
        String sql = "DELETE FROM proveedores WHERE id_proveedores = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProveedores);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el proveedor con ID: " + idProveedores, e);
            return false;
        }
    }

    private Proveedor mapResultSetToProveedor(ResultSet resultSet) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedores(resultSet.getInt("id_proveedores"));
        proveedor.setNombre(resultSet.getString("nombre"));
        proveedor.setContacto(resultSet.getString("contacto"));
        proveedor.setTelefono(resultSet.getString("telefono"));
        proveedor.setDireccion(resultSet.getString("direccion"));
        proveedor.setCiudad(resultSet.getString("ciudad"));
        proveedor.setProvincia(resultSet.getString("provincia"));
        proveedor.setCodigoPostal(resultSet.getString("codigo_postal"));
        proveedor.setPais(resultSet.getString("pais"));
        proveedor.setRfcNit(resultSet.getString("rfc_nit"));
        proveedor.setTipo(resultSet.getString("tipo"));
        proveedor.setSitioWeb(resultSet.getString("sitio_web"));
        proveedor.setFechaRegistro(resultSet.getDate("fecha_registro"));
        int empresaId = resultSet.getInt("empresas_id_empresa");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.getEmpresaById(empresaId);
        proveedor.setEmpresa(empresa);
        return proveedor;
    }
}