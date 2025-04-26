package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());

    public Usuario getUsuarioById(int idUsuario) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, nombre, apellido, correo, contrasena, rol, empresas_id_empresa FROM usuarios WHERE id_usuario = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = mapResultSetToUsuario(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el usuario con ID: " + idUsuario, e);
        }
        return usuario;
    }

    public Usuario getUsuarioByCorreo(String correo) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, nombre, apellido, correo, contrasena, rol, empresas_id_empresa FROM usuarios WHERE correo = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, correo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = mapResultSetToUsuario(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el usuario con correo: " + correo, e);
        }
        return usuario;
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, apellido, correo, contrasena, rol, empresas_id_empresa FROM usuarios";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                usuarios.add(mapResultSetToUsuario(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los usuarios", e);
        }
        return usuarios;
    }

    public boolean insertUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena, rol, empresas_id_empresa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getContrasena());
            statement.setString(5, usuario.getRol());
            statement.setInt(6, usuario.getEmpresa().getIdEmpresa());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el usuario con correo: " + usuario.getCorreo(), e);
            return false;
        }
    }

    public boolean updateUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, rol = ?, empresas_id_empresa = ? WHERE id_usuario = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getContrasena());
            statement.setString(5, usuario.getRol());
            statement.setInt(6, usuario.getEmpresa().getIdEmpresa());
            statement.setInt(7, usuario.getIdUsuario());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el usuario con ID: " + usuario.getIdUsuario(), e);
            return false;
        }
    }

    public boolean deleteUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el usuario con ID: " + idUsuario, e);
            return false;
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultSet.getInt("id_usuario"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setApellido(resultSet.getString("apellido"));
        usuario.setCorreo(resultSet.getString("correo"));
        usuario.setContrasena(resultSet.getString("contrasena"));
        usuario.setRol(resultSet.getString("rol"));
        int empresaId = resultSet.getInt("empresas_id_empresa");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        usuario.setEmpresa(empresaDAO.getEmpresaById(empresaId));
        return usuario;
    }
}