package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import conector.Conexion;
import modelo.Evento;

public class EventoDAO {
	public boolean nuevo(Evento e) {
        PreparedStatement ps = null;
        Connection connection = null;
        Conexion cn= new Conexion();
        try {
            connection = cn.getConnection();
            String sql = "INSERT INTO evento (nombre, descripcion, fecha_inicio, hora, fecha_fin, precio, ubicacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDescripcion());
            ps.setDate(3, new Date(e.getFechaInicio().getTime()));
            ps.setTime(4, e.getHora());
            ps.setDate(5, new Date(e.getFechaFin().getTime()));
            ps.setDouble(6, e.getPrecio());
            ps.setString(7, e.getUbicacion());

            int filaInsertada = ps.executeUpdate();
            if (filaInsertada > 0) return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
            }
        }
        return false;
    }
	
	public ArrayList<Evento> listar() {
        ArrayList<Evento> lista = new ArrayList<>();
        Conexion cn= new Conexion();
        Connection connection = cn.getConnection();
        Evento evento;
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            String sql = "SELECT id_evento, nombre, descripcion, fecha_inicio, hora, fecha_fin, precio, ubicacion FROM evento";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                evento = new Evento();
                evento.setIdEvento(rs.getInt("id_evento"));
                evento.setNombre(rs.getString("nombre"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFechaInicio(rs.getDate("fecha_inicio"));
                evento.setHora(rs.getTime("hora"));
                evento.setFechaFin(rs.getDate("fecha_fin"));
                evento.setPrecio(rs.getDouble("precio"));
                evento.setUbicacion(rs.getString("ubicacion"));

                lista.add(evento);
            }
        } catch (SQLException ex) {
            System.out.println("Error al realizar la conexión con la base de datos");
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
            }
        }
        return lista;
    }
	
	
	
	 public boolean actualizar(Evento e) {
	        PreparedStatement ps = null;
	        Conexion cn= new Conexion();
	        Connection connection = null;
	        try {
	            connection = cn.getConnection();
	            String sql = "UPDATE evento SET  nombre=?, descripcion=?, fecha_inicio=?, hora=?, fecha_fin=?, precio=?, ubicacion=? WHERE id_evento=?";
	            ps = connection.prepareStatement(sql);

	            ps.setString(1, e.getNombre());
	            ps.setString(2, e.getDescripcion());
	            ps.setDate(3, e.getFechaInicio());
	            ps.setTime(4, e.getHora());
	            ps.setDate(5, e.getFechaFin());
	            ps.setDouble(6, e.getPrecio());
	            ps.setString(7, e.getUbicacion());
	            ps.setInt(8, e.getIdEvento());

	            int filaActualizada = ps.executeUpdate();

	            if (filaActualizada > 0) return true;
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                if (connection != null) connection.close();
	            } catch (SQLException ex) {
	            }
	        }
	        return false;
	    }

	    public boolean eliminar(int idEvento) {
	        boolean estado = false;
	        Connection connection = null;
	        PreparedStatement st = null;
	        Conexion cn= new Conexion();
	        try {
	            connection = cn.getConnection();
	            String sql = "DELETE FROM evento WHERE id_evento = ?";
	            st = connection.prepareStatement(sql);

	            st.setInt(1, idEvento);
	            int filaElimina = st.executeUpdate();
	            if (filaElimina > 0) estado = true;

	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        } finally {
	            try {
	                if (st != null) st.close();
	                if (connection != null) connection.close();
	            } catch (SQLException ex) {
	            }
	        }
	        return estado;
	    }



	    public Evento buscarUno(int idEvento) {
	    	Conexion cn= new Conexion();
	        Connection connection = cn.getConnection();
	        ResultSet rs = null;
	        PreparedStatement ps = null;
	        
	        Evento evento = null;
	        try {
	            String sql = "SELECT id_evento, nombre, descripcion, fecha_inicio, hora, fecha_fin, precio, ubicacion FROM evento WHERE id_evento=?";
	            ps = connection.prepareStatement(sql);
	            ps.setInt(1, idEvento);

	            rs = ps.executeQuery();

	            if (rs.next()) {
	                evento = new Evento();
	                evento.setIdEvento(rs.getInt("id_evento"));
	                evento.setNombre(rs.getString("nombre"));
	                evento.setDescripcion(rs.getString("descripcion"));
	                evento.setFechaInicio(rs.getDate("fecha_inicio"));
	                evento.setHora(rs.getTime("hora"));
	                evento.setFechaFin(rs.getDate("fecha_fin"));
	                evento.setPrecio(rs.getDouble("precio"));
	                evento.setUbicacion(rs.getString("ubicacion"));
	            }

	        } catch (SQLException ex) {
	            System.out.println("Error al realizar la conexión con la base de datos");
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                if (rs != null) rs.close();
	                if (connection != null) connection.close();
	            } catch (SQLException ex) {
	            }
	        }
	        return evento;
	    }
}
