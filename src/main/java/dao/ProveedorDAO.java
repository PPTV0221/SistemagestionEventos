package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conector.Conexion;
import modelo.Proveedor;

public class ProveedorDAO {
	public boolean nuevo(Proveedor p) {
        Conexion cn = new Conexion();
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = cn.getConnection();
            String sql = "INSERT INTO proveedor (nombre, servicio, telefono, correo) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getServicio());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());

            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al agregar proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();
            String sql = "SELECT id_proveedor, nombre, servicio, telefono, correo FROM proveedor";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setServicio(rs.getString("servicio"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setCorreo(rs.getString("correo"));

                lista.add(proveedor);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar proveedores: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return lista;
    }

    public boolean actualizar(Proveedor p) {
        Conexion cn = new Conexion();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = cn.getConnection();
            String sql = "UPDATE proveedor SET nombre=?, servicio=?, telefono=?, correo=? WHERE id_proveedor=?";
            ps = connection.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getServicio());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            ps.setInt(5, p.getIdProveedor());

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    public boolean eliminar(int idProveedor) {
        Conexion cn = new Conexion();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = cn.getConnection();
            String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
            ps = connection.prepareStatement(sql);

            ps.setInt(1, idProveedor);
            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    public Proveedor buscarUno(int idProveedor) {
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Proveedor proveedor = null;

        try {
            String sql = "SELECT id_proveedor, nombre, servicio, telefono, correo FROM proveedor WHERE id_proveedor=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idProveedor);

            rs = ps.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setServicio(rs.getString("servicio"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return proveedor;
    }
}
