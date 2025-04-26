package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Empresa;
import com.inventario.inventarioproyectotienda.models.Producto;
import com.inventario.inventarioproyectotienda.models.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    public Producto getProductoById(int idProducto) {
        Producto producto = null;
        String sql = "SELECT id_producto, nombre, descripcion, precio, anyos_garantia, unidades, proveedores_id_proveedores, empresas_id_empresa FROM productos WHERE id_producto = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    producto = mapResultSetToProducto(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el producto con ID: " + idProducto, e);
        }
        return producto;
    }

    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, anyos_garantia, unidades, proveedores_id_proveedores, empresas_id_empresa FROM productos";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                productos.add(mapResultSetToProducto(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los productos", e);
        }
        return productos;
    }

    public List<Producto> getProductosByProveedorId(int proveedorId) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, descripcion, precio, anyos_garantia, unidades, proveedores_id_proveedores, empresas_id_empresa FROM productos WHERE proveedores_id_proveedores = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, proveedorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productos.add(mapResultSetToProducto(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener productos por proveedor ID: " + proveedorId, e);
        }
        return productos;
    }

    public boolean updateStock(int productoId, int cantidadCambio) {
        String sql = "UPDATE productos SET unidades = unidades + ? WHERE id_producto = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cantidadCambio);
            statement.setInt(2, productoId);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el stock del producto con ID: " + productoId, e);
            return false;
        }
    }

    public boolean insertProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, anyos_garantia, unidades, proveedores_id_proveedores, empresas_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setObject(4, producto.getAnyosGarantia());
            statement.setInt(5, producto.getUnidades());
            statement.setInt(6, producto.getProveedor().getIdProveedores());
            statement.setInt(7, producto.getEmpresa().getIdEmpresa());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setIdProducto(generatedKeys.getInt(1));
                    }
                } catch (SQLException e) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el producto con nombre: " + producto.getNombre(), e);
            return false;
        }
    }

    public boolean updateProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, anyos_garantia = ?, unidades = ?, proveedores_id_proveedores = ?, empresas_id_empresa = ? WHERE id_producto = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setObject(4, producto.getAnyosGarantia());
            statement.setInt(5, producto.getUnidades());
            statement.setInt(6, producto.getProveedor().getIdProveedores());
            statement.setInt(7, producto.getEmpresa().getIdEmpresa());
            statement.setInt(8, producto.getIdProducto());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el producto con ID: " + producto.getIdProducto(), e);
            return false;
        }
    }

    public boolean deleteProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProducto);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el producto con ID: " + idProducto, e);
            return false;
        }
    }

    public boolean existsProductByNameAndCompany(String nombre, int empresaId) {
        String sql = "SELECT 1 FROM productos WHERE nombre = ? AND empresas_id_empresa = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setInt(2, empresaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar existencia de producto por nombre y empresa: " + nombre + ", " + empresaId, e);
            return false;
        }
    }

    private Producto mapResultSetToProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(resultSet.getInt("id_producto"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setDescripcion(resultSet.getString("descripcion"));
        producto.setPrecio(resultSet.getDouble("precio"));
        producto.setAnyosGarantia((Integer) resultSet.getObject("anyos_garantia"));
        producto.setUnidades(resultSet.getInt("unidades"));
        int proveedorId = resultSet.getInt("proveedores_id_proveedores");
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        producto.setProveedor(proveedorDAO.getProveedorById(proveedorId));
        int empresaId = resultSet.getInt("empresas_id_empresa");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        producto.setEmpresa(empresaDAO.getEmpresaById(empresaId));
        return producto;
    }
}