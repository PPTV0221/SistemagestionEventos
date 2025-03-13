package modelo;

import java.sql.Date;

public class Comunicacion {
	private int idComunicacion;
    private int idAsistente;
    private int idEvento;
    private String tipo;
    private String mensaje;
    private Date fechaEnvio;
    private String nombreEvento;
    private String nombreAsistente;
    
	public int getIdComunicacion() {
		return idComunicacion;
	}
	public void setIdComunicacion(int idComunicacion) {
		this.idComunicacion = idComunicacion;
	}
	public int getIdAsistente() {
		return idAsistente;
	}
	public void setIdAsistente(int idAsistente) {
		this.idAsistente = idAsistente;
	}
	public int getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public String getNombreEvento() {
		return nombreEvento;
	}
	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	public String getNombreAsistente() {
		return nombreAsistente;
	}
	public void setNombreAsistente(String nombreAsistente) {
		this.nombreAsistente = nombreAsistente;
	}
    
    
}
