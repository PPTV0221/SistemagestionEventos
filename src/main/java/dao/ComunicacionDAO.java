package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conector.Conexion;
import modelo.Comunicacion;

public class ComunicacionDAO {
	public boolean insertar(Comunicacion c) {
		Conexion cnn= new Conexion();
        String sql = "INSERT INTO comunicacion (id_asistente, id_evento, tipo, mensaje, fecha_envio) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = cnn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, c.getIdAsistente());
            ps.setInt(2, c.getIdEvento());
            ps.setString(3, c.getTipo());
            ps.setString(4, c.getMensaje());
            ps.setDate(5, new Date(c.getFechaEnvio().getTime()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar comunicación: " + e.getMessage());
            return false;
        }
    }

	public List<Comunicacion> listar() {
	    Conexion cnn = new Conexion();
	    List<Comunicacion> lista = new ArrayList<>();
	    String sql = "SELECT c.id_comunicacion, c.id_asistente, c.id_evento, c.tipo, c.mensaje, c.fecha_envio, " +
	                 "e.nombre AS nombre_evento, CONCAT(a.nombre, ' ', a.apellidoP, ' ', a.apellidoM) AS nombre_asistente " +
	                 "FROM comunicacion c " +
	                 "INNER JOIN evento e ON c.id_evento = e.id_evento " +
	                 "INNER JOIN asistente a ON c.id_asistente = a.id_asistente";

	    try (Connection con = cnn.getConnection();
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            Comunicacion c = new Comunicacion();
	            c.setIdComunicacion(rs.getInt("id_comunicacion"));
	            c.setIdAsistente(rs.getInt("id_asistente"));
	            c.setIdEvento(rs.getInt("id_evento"));
	            c.setTipo(rs.getString("tipo"));
	            c.setMensaje(rs.getString("mensaje"));
	            c.setFechaEnvio(rs.getDate("fecha_envio"));
	            c.setNombreEvento(rs.getString("nombre_evento"));
	            c.setNombreAsistente(rs.getString("nombre_asistente"));

	            lista.add(c);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al listar comunicaciones: " + e.getMessage());
	    }
	    return lista;
	}
}
