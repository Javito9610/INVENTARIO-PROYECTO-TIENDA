package com.inventario.inventarioproyectotienda.BBDD.DAO;

import com.inventario.inventarioproyectotienda.BBDD.BBDDConnector;
import com.inventario.inventarioproyectotienda.models.Cliente;
import com.inventario.inventarioproyectotienda.models.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO {
    private EmpresaDAO empresaDAO;
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    public ClienteDAO() {
        this.empresaDAO = new EmpresaDAO();
    }

    public Cliente getClienteByDni(String dni) {
        Cliente cliente = null;
        String sql = "SELECT dni, nombre, apellidos, numero_telefono, correo, direccion, ciudad, codigo_postal, provincia, empresas_id_empresa FROM clientes WHERE dni = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dni);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new Cliente();
                    cliente.setDni(resultSet.getString("dni"));
                    cliente.setNombre(resultSet.getString("nombre"));
                    cliente.setApellidos(resultSet.getString("apellidos"));
                    cliente.setNumeroTelefono(resultSet.getString("numero_telefono"));
                    cliente.setCorreo(resultSet.getString("correo"));
                    cliente.setDireccion(resultSet.getString("direccion"));
                    cliente.setCiudad(resultSet.getString("ciudad"));
                    cliente.setCodigoPostal(resultSet.getString("codigo_postal"));
                    cliente.setProvincia(resultSet.getString("provincia"));
                    int idEmpresa = resultSet.getInt("empresas_id_empresa");
                    Empresa empresa = empresaDAO.getEmpresaById(idEmpresa);
                    cliente.setEmpresa(empresa);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el cliente con DNI: " + dni, e);
        }
        return cliente;
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT dni, nombre, apellidos, numero_telefono, correo, direccion, ciudad, codigo_postal, provincia, empresas_id_empresa FROM clientes";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setDni(resultSet.getString("dni"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellidos(resultSet.getString("apellidos"));
                cliente.setNumeroTelefono(resultSet.getString("numero_telefono"));
                cliente.setCorreo(resultSet.getString("correo"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setCiudad(resultSet.getString("ciudad"));
                cliente.setCodigoPostal(resultSet.getString("codigo_postal"));
                cliente.setProvincia(resultSet.getString("provincia"));
                int idEmpresa = resultSet.getInt("empresas_id_empresa");
                Empresa empresa = empresaDAO.getEmpresaById(idEmpresa);
                cliente.setEmpresa(empresa);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los clientes", e);
        }
        return clientes;
    }

    public boolean insertCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, apellidos, numero_telefono, correo, direccion, ciudad, codigo_postal, provincia, empresas_id_empresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getDni());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getApellidos());
            statement.setString(4, cliente.getNumeroTelefono());
            statement.setString(5, cliente.getCorreo());
            statement.setString(6, cliente.getDireccion());
            statement.setString(7, cliente.getCiudad());
            statement.setString(8, cliente.getCodigoPostal());
            statement.setString(9, cliente.getProvincia());
            statement.setInt(10, cliente.getEmpresa().getIdEmpresa());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar el cliente con DNI: " + cliente.getDni(), e);
            return false;
        }
    }

    public boolean updateCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, apellidos = ?, numero_telefono = ?, correo = ?, direccion = ?, ciudad = ?, codigo_postal = ?, provincia = ? , empresas_id_empresa = ? WHERE dni = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellidos());
            statement.setString(3, cliente.getNumeroTelefono());
            statement.setString(4, cliente.getCorreo());
            statement.setString(5, cliente.getDireccion());
            statement.setString(6, cliente.getCiudad());
            statement.setString(7, cliente.getCodigoPostal());
            statement.setString(8, cliente.getProvincia());
            statement.setInt(9, cliente.getEmpresa().getIdEmpresa());
            statement.setString(10, cliente.getDni());
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el cliente con DNI: " + cliente.getDni(), e);
            return false;
        }
    }

    public boolean deleteCliente(String dni) {
        String sql = "DELETE FROM clientes WHERE dni = ?";
        try (Connection connection = BBDDConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dni);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el cliente con DNI: " + dni, e);
            return false;
        }
    }
}