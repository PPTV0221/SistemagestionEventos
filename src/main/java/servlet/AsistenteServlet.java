package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AsistenteDAO;
import modelo.Asistente;


@WebServlet("/AsistenteServlet")
public class AsistenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AsistenteServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String metodo = request.getParameter("metodo");

            HttpSession session = request.getSession();
            AsistenteDAO asistenteDAO = new AsistenteDAO();

            if ("listar".equals(metodo)) {
                List<Asistente> asistentes = asistenteDAO.listar();
                request.setAttribute("listaAsistentes", asistentes);

                RequestDispatcher dispatcher = request.getRequestDispatcher("asistente.jsp");
                dispatcher.forward(request, response);
            } else if ("agregar".equals(metodo)) {
                String nombre = request.getParameter("nombre");
                String apellidoP = request.getParameter("apellidoP");
                String apellidoM = request.getParameter("apellidoM");
                String correo = request.getParameter("correo");
                String telefono = request.getParameter("telefono");

                Asistente asistente = new Asistente();
                asistente.setNombre(nombre);
                asistente.setApellidoP(apellidoP);
                asistente.setApellidoM(apellidoM);
                asistente.setCorreo(correo);
                asistente.setTelefono(telefono);

                boolean estado = asistenteDAO.nuevo(asistente);
                session.setAttribute("mensaje", estado ? "Asistente registrado" : "Error al registrar asistente");

                response.sendRedirect("AsistenteServlet?metodo=listar");
            } else if ("editar".equals(metodo)) {
                String idAsistente = request.getParameter("idAsistente");

                if (request.getParameter("cargar") != null) {
                    Asistente asistente = asistenteDAO.buscarUno(Integer.parseInt(idAsistente));

                    if (asistente != null) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        out.print("{\"idAsistente\":\"" + asistente.getIdAsistente() + "\","
                                + "\"nombre\":\"" + asistente.getNombre() + "\","
                                + "\"apellidoP\":\"" + asistente.getApellidoP() + "\","
                                + "\"apellidoM\":\"" + asistente.getApellidoM() + "\","
                                + "\"correo\":\"" + asistente.getCorreo() + "\","
                                + "\"telefono\":\"" + asistente.getTelefono() + "\"}");

                        out.flush();
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asistente no encontrado");
                    }
                } else {
                    String nombre = request.getParameter("nombre");
                    String apellidoP = request.getParameter("apellidoP");
                    String apellidoM = request.getParameter("apellidoM");
                    String correo = request.getParameter("correo");
                    String telefono = request.getParameter("telefono");

                    Asistente asistente = new Asistente();
                    asistente.setIdAsistente(Integer.parseInt(idAsistente));
                    asistente.setNombre(nombre);
                    asistente.setApellidoP(apellidoP);
                    asistente.setApellidoM(apellidoM);
                    asistente.setCorreo(correo);
                    asistente.setTelefono(telefono);

                    boolean estado = asistenteDAO.actualizar(asistente);
                    session.setAttribute("mensaje", estado ? "Asistente modificado" : "Error al modificar asistente");

                    response.sendRedirect("AsistenteServlet?metodo=listar");
                }
            } else if ("eliminar".equals(metodo)) {
                String idAsistente = request.getParameter("idAsistente");
                boolean estado = asistenteDAO.eliminar(Integer.parseInt(idAsistente));
                session.setAttribute("mensaje", estado ? "Asistente eliminado" : "Error al eliminar asistente");

                response.sendRedirect("AsistenteServlet?metodo=listar");
            }
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
