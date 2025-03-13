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
import dao.EventoDAO;
import dao.InscripcionDAO;
import modelo.Asistente;
import modelo.Evento;
import modelo.Inscripcion;


@WebServlet("/InscripcionServlet")
public class InscripcionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public InscripcionServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String metodo = request.getParameter("metodo");
            HttpSession session = request.getSession();
            InscripcionDAO inscripcionDao = new InscripcionDAO();
            EventoDAO eventoDao = new EventoDAO();
            AsistenteDAO asistenteDao = new AsistenteDAO();

            if ("listar".equals(metodo)) {
                List<Inscripcion> inscripciones = inscripcionDao.listar();
                List<Evento> eventos = eventoDao.listar();
                List<Asistente> asistentes = asistenteDao.listar();
                
                request.setAttribute("listaInscripciones", inscripciones);
                request.setAttribute("listaEventos", eventos);
                request.setAttribute("listaAsistentes", asistentes);
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("inscripcion.jsp");
                dispatcher.forward(request, response);
            } else if ("agregar".equals(metodo)) {
                int idEvento = Integer.parseInt(request.getParameter("idEvento"));
                int idAsistente = Integer.parseInt(request.getParameter("idAsistente"));
                Date fechaInscripcion = Date.valueOf(request.getParameter("fechaInscripcion"));
                
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdEvento(idEvento);
                inscripcion.setIdAsistente(idAsistente);
                inscripcion.setFechaInscripcion(fechaInscripcion);
                
                boolean estado = inscripcionDao.nueva(inscripcion);
                session.setAttribute("mensaje", estado ? "Inscripción registrada" : "Error al registrar inscripción");
                response.sendRedirect("InscripcionServlet?metodo=listar");
            } else if ("editar".equals(metodo)) {
                String idInscripcion = request.getParameter("idInscripcion");

                if (request.getParameter("cargar") != null) {
                    Inscripcion inscripcion = inscripcionDao.buscarUno(Integer.parseInt(idInscripcion));

                    if (inscripcion != null) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        out.print("{\"idInscripcion\":\"" + inscripcion.getIdInscripcion() + "\","
                                + "\"idEvento\":\"" + inscripcion.getIdEvento() + "\","
                                + "\"idAsistente\":\"" + inscripcion.getIdAsistente() + "\","
                                + "\"fechaInscripcion\":\"" + inscripcion.getFechaInscripcion() + "\"}");

                        out.flush();
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inscripción no encontrada");
                    }
                } else {
                    int idEvento = Integer.parseInt(request.getParameter("idEvento"));
                    int idAsistente = Integer.parseInt(request.getParameter("idAsistente"));
                    Date fechaInscripcion = Date.valueOf(request.getParameter("fechaInscripcion"));

                    Inscripcion inscripcion = new Inscripcion();
                    inscripcion.setIdInscripcion(Integer.parseInt(idInscripcion));
                    inscripcion.setIdEvento(idEvento);
                    inscripcion.setIdAsistente(idAsistente);
                    inscripcion.setFechaInscripcion(fechaInscripcion);

                    boolean estado = inscripcionDao.actualizar(inscripcion);
                    session.setAttribute("mensaje", estado ? "Inscripción modificada" : "Error al modificar inscripción");

                    response.sendRedirect("InscripcionServlet?metodo=listar");
                }
            }
            else if ("eliminar".equals(metodo)) {
                int idInscripcion = Integer.parseInt(request.getParameter("idInscripcion"));
                boolean estado = inscripcionDao.eliminar(idInscripcion);
                session.setAttribute("mensaje", estado ? "Inscripción eliminada" : "Error al eliminar inscripción");
                response.sendRedirect("InscripcionServlet?metodo=listar");
            }
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
