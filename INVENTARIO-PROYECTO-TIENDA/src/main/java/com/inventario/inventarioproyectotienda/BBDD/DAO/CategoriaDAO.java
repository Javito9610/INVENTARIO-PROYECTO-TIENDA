package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaDAO {

    private static final Logger LOGGER = Logger.getLogger(CategoriaDAO.class.getName());

    public Categoria getCategoriaById(int idCategoria) {
        Categoria categoria = null;
        String sql = "SELECT id_categoria, nombre FROM categorias WHERE id_categoria = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCategoria);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    categoria = new Categoria();
                    categoria.setIdCategoria(resultSet.getInt("id_categoria"));
                    categoria.setNombre(resultSet.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la categoría con ID: " + idCategoria, e);
        }
        return categoria;
    }

    public Categoria getCategoriaByNombre(String nombre) {
        Categoria categoria = null;
        String sql = "SELECT id_categoria, nombre FROM categorias WHERE nombre = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    categoria = new Categoria();
                    categoria.setIdCategoria(resultSet.getInt("id_categoria"));
                    categoria.setNombre(resultSet.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la categoría con nombre: " + nombre, e);
        }
        return categoria;
    }

    public List<Categoria> getAllCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre FROM categorias";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(resultSet.getInt("id_categoria"));
                categoria.setNombre(resultSet.getString("nombre"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las categorías", e);
        }
        return categorias;
    }

    public boolean insertCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoria.getNombre());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar la categoría: " + categoria.getNombre(), e);
            return false;
        }
    }

    public boolean updateCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id_categoria = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoria.getNombre());
            statement.setInt(2, categoria.getIdCategoria());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la categoría con ID: " + categoria.getIdCategoria(), e);
            return false;
        }
    }

    public boolean deleteCategoria(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCategoria);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la categoría con ID: " + idCategoria, e);
            return false;
        }
    }
}