package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conector.Conexion;
import modelo.EventoProveedor;

public class EventoProveedorDAO {
	public boolean agregar(EventoProveedor ep) {
        Connection connection = null;
        PreparedStatement ps = null;
        Conexion cn = new Conexion();
        
        try {
            connection = cn.getConnection();
            String sql = "INSERT INTO eventos_proveedores (id_evento, id_proveedor, monto_contrato, descripcion) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, ep.getIdEvento());
            ps.setInt(2, ep.getIdProveedor());
            ps.setDouble(3, ep.getMontoContrato());
            ps.setString(4, ep.getDescripcion());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al agregar evento-proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return false;
    }

    public List<EventoProveedor> listar() {
        List<EventoProveedor> lista = new ArrayList<>();
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        
        try {
            statement = connection.createStatement();
            String sql = "SELECT ep.id, ep.id_evento, ep.id_proveedor, ep.monto_contrato, ep.descripcion, " +
                         "e.nombre AS nombreEvento, p.nombre AS nombreProveedor " +
                         "FROM eventos_proveedores ep " +
                         "INNER JOIN evento e ON ep.id_evento = e.id_evento " +
                         "INNER JOIN proveedor p ON ep.id_proveedor = p.id_proveedor";
            
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                EventoProveedor ep = new EventoProveedor();
                ep.setId(rs.getInt("id"));
                ep.setIdEvento(rs.getInt("id_evento"));
                ep.setIdProveedor(rs.getInt("id_proveedor"));
                ep.setMontoContrato(rs.getDouble("monto_contrato"));
                ep.setDescripcion(rs.getString("descripcion"));
                ep.setNombreEvento(rs.getString("nombreEvento"));
                ep.setNombreProveedor(rs.getString("nombreProveedor"));
                lista.add(ep);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar eventos-proveedores: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return lista;
    }
    
    public boolean actualizar(EventoProveedor ep) {
        Connection connection = null;
        PreparedStatement ps = null;
        Conexion cn = new Conexion();
        
        try {
            connection = cn.getConnection();
            String sql = "UPDATE eventos_proveedores SET id_evento=?, id_proveedor=?, monto_contrato=?, descripcion=? WHERE id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, ep.getIdEvento());
            ps.setInt(2, ep.getIdProveedor());
            ps.setDouble(3, ep.getMontoContrato());
            ps.setString(4, ep.getDescripcion());
            ps.setInt(5, ep.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar evento-proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return false;
    }
    
    public EventoProveedor buscarUno(int id) {
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        EventoProveedor eventoProveedor = null;

        try {
            String sql = "SELECT ep.id, ep.id_evento, ep.id_proveedor, ep.monto_contrato, ep.descripcion, " +
                         "e.nombre AS nombre_evento, p.nombre AS nombre_proveedor " +
                         "FROM eventos_proveedores ep " +
                         "INNER JOIN evento e ON ep.id_evento = e.id_evento " +
                         "INNER JOIN proveedor p ON ep.id_proveedor = p.id_proveedor " +
                         "WHERE ep.id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                eventoProveedor = new EventoProveedor();
                eventoProveedor.setId(rs.getInt("id"));
                eventoProveedor.setIdEvento(rs.getInt("id_evento"));
                eventoProveedor.setIdProveedor(rs.getInt("id_proveedor"));
                eventoProveedor.setMontoContrato(rs.getDouble("monto_contrato"));
                eventoProveedor.setDescripcion(rs.getString("descripcion"));
                eventoProveedor.setNombreEvento(rs.getString("nombre_evento"));
                eventoProveedor.setNombreProveedor(rs.getString("nombre_proveedor"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar EventoProveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return eventoProveedor;
    }

    
    public boolean eliminar(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        Conexion cn = new Conexion();
        
        try {
            connection = cn.getConnection();
            String sql = "DELETE FROM eventos_proveedores WHERE id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar evento-proveedor: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return false;
    }
}
