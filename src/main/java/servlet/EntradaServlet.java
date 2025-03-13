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

import dao.EntradaDAO;
import dao.EventoDAO;
import dao.InscripcionDAO;
import modelo.Entrada;
import modelo.Evento;
import modelo.Inscripcion;


@WebServlet("/EntradaServlet")
public class EntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EntradaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
            String metodo = request.getParameter("metodo");

            HttpSession session = request.getSession();
            EntradaDAO entradaDAO = new EntradaDAO();
            EventoDAO eventoDAO = new EventoDAO();
            InscripcionDAO inscripcionDAO = new InscripcionDAO();

            if ("listar".equals(metodo)) {
                List<Entrada> entradas = entradaDAO.listar();
                List<Evento> eventos = eventoDAO.listar();
                request.setAttribute("listaEntradas", entradas);
                request.setAttribute("listaEventos", eventos);

                RequestDispatcher dispatcher = request.getRequestDispatcher("entrada.jsp");
                dispatcher.forward(request, response);
            } else if ("agregar".equals(metodo)) {
                int idEvento = Integer.parseInt(request.getParameter("idEvento"));
                int idAsistente = Integer.parseInt(request.getParameter("idAsistente"));
                
                Integer idIscripcion= inscripcionDAO.buscarInscripcion(idEvento, idAsistente);
                if(idIscripcion!=null) {
                	String metodoPago = request.getParameter("metodoPago");
                    Date fechaCompra = Date.valueOf(request.getParameter("fechaCompra"));
                    
                    Entrada entrada = new Entrada();
                    entrada.setIdInscripcion(idIscripcion);
                    entrada.setMetodoPago(metodoPago);
                    entrada.setFechaCompra(fechaCompra);

                    boolean estado = entradaDAO.nueva(entrada);
                    session.setAttribute("mensaje", estado ? "Entrada registrada" : "Error al registrar entrada");

                }else {
                	session.setAttribute("mensaje", "La inscripción no existe");
                }
                
                response.sendRedirect("EntradaServlet?metodo=listar");
            }else if ("buscar_participantes".equals(metodo)) {
                int idEvento = Integer.parseInt(request.getParameter("idEvento"));
                List<Inscripcion> inscripciones = inscripcionDAO.listarPorEvento(idEvento);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                out.print("[");

                for (int i = 0; i < inscripciones.size(); i++) {
                    Inscripcion inscripcion = inscripciones.get(i);
                    out.print("{\"idAsistente\":\"" + inscripcion.getIdAsistente() + "\","
                            + "\"nombre\":\"" + inscripcion.getNombreAsistente() + "\"}");

                    if (i < inscripciones.size() - 1) {
                        out.print(",");
                    }
                }
                
                out.print("]");
                out.flush();
            }else if ("eliminar".equals(metodo)) {
                int idEntrada = Integer.parseInt(request.getParameter("idEntrada"));
                boolean estado = entradaDAO.eliminar(idEntrada);
                session.setAttribute("mensaje", estado ? "Entrada eliminada" : "Error al eliminar entrada");
                response.sendRedirect("EntradaServlet?metodo=listar");
            }

        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
