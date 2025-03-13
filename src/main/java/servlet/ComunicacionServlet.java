package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AsistenteDAO;
import dao.ComunicacionDAO;
import dao.EventoDAO;
import modelo.Asistente;
import modelo.Comunicacion;
import modelo.Evento;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet("/ComunicacionServlet")
public class ComunicacionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ComunicacionServlet() {
        super();
    }

    
    public boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        final String remitente = "testpruebapruab@gmail.com";
        final String clave = "test12345.$";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Correo enviado exitosamente a " + destinatario);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String metodo = request.getParameter("metodo");
            HttpSession session = request.getSession();
            ComunicacionDAO comunicacionDao = new ComunicacionDAO();
            EventoDAO eventoDao = new EventoDAO();
            AsistenteDAO asistenteDao = new AsistenteDAO();
            AsistenteDAO asistenteDAO= new AsistenteDAO();
            EventoDAO eventoDAO= new EventoDAO();

            if ("listar".equals(metodo)) {
                List<Comunicacion> comunicaciones = comunicacionDao.listar();
                List<Evento> eventos = eventoDao.listar();
                List<Asistente> asistentes = asistenteDao.listar();
                
                request.setAttribute("listaComunicaciones", comunicaciones);
                request.setAttribute("listaEventos", eventos);
                request.setAttribute("listaAsistentes", asistentes);
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("comunicacion.jsp");
                dispatcher.forward(request, response);
                
            } else if ("agregar".equals(metodo)) {
                int idEvento = Integer.parseInt(request.getParameter("idEvento"));
                int idAsistente = Integer.parseInt(request.getParameter("idAsistente"));
                
                Asistente asistente = asistenteDAO.buscarUno(idAsistente);
                Evento evento = eventoDAO.buscarUno(idEvento);
                
                if(asistente!=null && evento!=null){
                	String tipo = request.getParameter("tipo");
                    String mensaje = request.getParameter("mensaje");
                    Date fechaEnvio = Date.valueOf(request.getParameter("fechaEnvio"));
                    
                    Comunicacion comunicacion = new Comunicacion();
                    comunicacion.setIdEvento(idEvento);
                    comunicacion.setIdAsistente(idAsistente);
                    comunicacion.setTipo(tipo);
                    comunicacion.setMensaje(mensaje);
                    comunicacion.setFechaEnvio(fechaEnvio);
                    
                    try {
                    	enviarCorreo(asistente.getCorreo(),evento.getNombre(),mensaje);
                    }catch(Exception ex) {}

                    boolean estado = comunicacionDao.insertar(comunicacion);
                    session.setAttribute("mensaje", estado ? "Notificación/recordatorio registrada" : "Error al registrar comunicación");
                }
                response.sendRedirect("ComunicacionServlet?metodo=listar");
            }
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
