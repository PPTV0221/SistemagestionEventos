package dao;


import modelo.Inscripcion;
import conector.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class InscripcionDAO {

    public boolean nueva(Inscripcion i) {
        PreparedStatement ps = null;
        Connection connection = null;
        Conexion cn = new Conexion();

        try {
            connection = cn.getConnection();
            String sql = "INSERT INTO inscripcion (id_evento, id_asistente, fecha_inscripcion) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(sql);

            ps.setInt(1, i.getIdEvento());
            ps.setInt(2, i.getIdAsistente());
            ps.setDate(3, new Date(i.getFechaInscripcion().getTime()));

            int filaInsertada = ps.executeUpdate();
            return filaInsertada > 0;
        } catch (SQLException ex) {
            System.out.println("Error al registrar inscripción: " + ex.getMessage());
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

    public ArrayList<Inscripcion> listar() {
        ArrayList<Inscripcion> lista = new ArrayList<>();
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();
            String sql = "SELECT i.id_inscripcion, i.id_evento, i.id_asistente, i.fecha_inscripcion, " +
                         "e.nombre AS evento, a.nombre AS asistente " +
                         "FROM inscripcion i " +
                         "INNER JOIN evento e ON i.id_evento = e.id_evento " +
                         "INNER JOIN asistente a ON i.id_asistente = a.id_asistente";

            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("id_inscripcion"));
                inscripcion.setIdEvento(rs.getInt("id_evento"));
                inscripcion.setIdAsistente(rs.getInt("id_asistente"));
                inscripcion.setFechaInscripcion(rs.getDate("fecha_inscripcion"));
                inscripcion.setNomEvento(rs.getString("evento"));
                inscripcion.setNombreAsistente(rs.getString("asistente"));

                lista.add(inscripcion);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar inscripciones: " + ex.getMessage());
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
    
    public ArrayList<Inscripcion> listarPorEvento(int idEvento) {
        ArrayList<Inscripcion> lista = new ArrayList<>();
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT i.id_inscripcion, i.id_evento, i.id_asistente, i.fecha_inscripcion, " +
                         "e.nombre AS evento, a.nombre AS asistente " +
                         "FROM inscripcion i " +
                         "INNER JOIN evento e ON i.id_evento = e.id_evento " +
                         "INNER JOIN asistente a ON i.id_asistente = a.id_asistente " +
                         "WHERE i.id_evento = ?";
            
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idEvento);
            rs = ps.executeQuery();

            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("id_inscripcion"));
                inscripcion.setIdEvento(rs.getInt("id_evento"));
                inscripcion.setIdAsistente(rs.getInt("id_asistente"));
                inscripcion.setFechaInscripcion(rs.getDate("fecha_inscripcion"));
                inscripcion.setNomEvento(rs.getString("evento"));
                inscripcion.setNombreAsistente(rs.getString("asistente"));

                lista.add(inscripcion);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar inscripciones por evento: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return lista;
    }


    public Inscripcion buscarUno(int idInscripcion) {
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Inscripcion inscripcion = null;

        try {
            String sql = "SELECT i.id_inscripcion, i.id_evento, i.id_asistente, i.fecha_inscripcion, " +
                         "e.nombre AS evento, a.nombre AS asistente " +
                         "FROM inscripcion i " +
                         "INNER JOIN evento e ON i.id_evento = e.id_evento " +
                         "INNER JOIN asistente a ON i.id_asistente = a.id_asistente " +
                         "WHERE i.id_inscripcion = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idInscripcion);
            rs = ps.executeQuery();

            if (rs.next()) {
                inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("id_inscripcion"));
                inscripcion.setIdEvento(rs.getInt("id_evento"));
                inscripcion.setIdAsistente(rs.getInt("id_asistente"));
                inscripcion.setFechaInscripcion(rs.getDate("fecha_inscripcion"));
                inscripcion.setNomEvento(rs.getString("evento"));
                inscripcion.setNombreAsistente(rs.getString("asistente"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar inscripción: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return inscripcion;
    }
    
    public Integer buscarInscripcion(int idEvento, int idAsistente) {
        Conexion cn = new Conexion();
        Connection connection = cn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer idInscripcion = null;

        try {
            String sql = "SELECT id_inscripcion FROM inscripcion " +
                         "WHERE id_evento = ? AND id_asistente = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idEvento);
            ps.setInt(2, idAsistente);
            rs = ps.executeQuery();

            if (rs.next()) {
                idInscripcion = rs.getInt("id_inscripcion"); 
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar inscripción: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return idInscripcion; 
    }

    
    public boolean actualizar(Inscripcion inscripcion) {
        PreparedStatement ps = null;
        Conexion cn = new Conexion();
        Connection connection = null;
        
        try {
            connection = cn.getConnection();
            String sql = "UPDATE inscripcion SET id_evento=?, id_asistente=?, fecha_inscripcion=? WHERE id_inscripcion=?";
            ps = connection.prepareStatement(sql);

            ps.setInt(1, inscripcion.getIdEvento());
            ps.setInt(2, inscripcion.getIdAsistente());
            ps.setDate(3, inscripcion.getFechaInscripcion());
            ps.setInt(4, inscripcion.getIdInscripcion());

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar inscripción: " + ex.getMessage());
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


    public boolean eliminar(int idInscripcion) {
        boolean estado = false;
        Connection connection = null;
        PreparedStatement ps = null;
        Conexion cn = new Conexion();

        try {
            connection = cn.getConnection();
            String sql = "DELETE FROM inscripcion WHERE id_inscripcion = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idInscripcion);

            int filaEliminada = ps.executeUpdate();
            estado = filaEliminada > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar inscripción: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return estado;
    }
}
