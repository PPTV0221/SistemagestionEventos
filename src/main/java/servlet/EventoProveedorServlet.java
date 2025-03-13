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

import dao.EventoDAO;
import dao.EventoProveedorDAO;
import dao.ProveedorDAO;
import modelo.Evento;
import modelo.EventoProveedor;
import modelo.Proveedor;

@WebServlet("/EventoProveedorServlet")
public class EventoProveedorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public EventoProveedorServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	    
	    try (PrintWriter out = response.getWriter()) {
	        String metodo = request.getParameter("metodo");
	        HttpSession session = request.getSession();
	        EventoProveedorDAO eventoProveedorDao = new EventoProveedorDAO();
	        EventoDAO eventoDao = new EventoDAO();
	        ProveedorDAO proveedorDao = new ProveedorDAO();

	        if ("listar".equals(metodo)) {
	            List<EventoProveedor> eventoProveedores = eventoProveedorDao.listar();
	            List<Evento> eventos = eventoDao.listar();
	            List<Proveedor> proveedores = proveedorDao.listar();

	            request.setAttribute("listaEventoProveedores", eventoProveedores);
	            request.setAttribute("listaEventos", eventos);
	            request.setAttribute("listaProveedores", proveedores);

	            RequestDispatcher dispatcher = request.getRequestDispatcher("evento_proveedor.jsp");
	            dispatcher.forward(request, response);
	        } else if ("agregar".equals(metodo)) {
	            int idEvento = Integer.parseInt(request.getParameter("idEvento"));
	            int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
	            double montoContrato = Double.parseDouble(request.getParameter("montoContrato"));
	            String descripcion = request.getParameter("descripcion");

	            EventoProveedor eventoProveedor = new EventoProveedor();
	            eventoProveedor.setIdEvento(idEvento);
	            eventoProveedor.setIdProveedor(idProveedor);
	            eventoProveedor.setMontoContrato(montoContrato);
	            eventoProveedor.setDescripcion(descripcion);

	            boolean estado = eventoProveedorDao.agregar(eventoProveedor);
	            session.setAttribute("mensaje", estado ? "Evento-Proveedor registrado" : "Error al registrar Evento-Proveedor");
	            response.sendRedirect("EventoProveedorServlet?metodo=listar");
	        }else if ("editar".equals(metodo)) {
	            String idEventoProveedor = request.getParameter("idEventoProveedor");

	            if (request.getParameter("cargar") != null) {
	                EventoProveedor eventoProveedor = eventoProveedorDao.buscarUno(Integer.parseInt(idEventoProveedor));

	                if (eventoProveedor != null) {
	                    response.setContentType("application/json");
	                    response.setCharacterEncoding("UTF-8");

	                    out.print("{\"idEventoProveedor\":\"" + eventoProveedor.getId() + "\","
	                            + "\"idEvento\":\"" + eventoProveedor.getIdEvento() + "\","
	                            + "\"idProveedor\":\"" + eventoProveedor.getIdProveedor() + "\","
	                            + "\"montoContrato\":\"" + eventoProveedor.getMontoContrato() + "\","
	                            + "\"descripcion\":\"" + eventoProveedor.getDescripcion() + "\"}");

	                    out.flush();
	                } else {
	                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Evento-Proveedor no encontrado");
	                }
	            } else {
	                int idEvento = Integer.parseInt(request.getParameter("idEvento"));
	                int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
	                double montoContrato = Double.parseDouble(request.getParameter("montoContrato"));
	                String descripcion = request.getParameter("descripcion");

	                EventoProveedor eventoProveedor = new EventoProveedor();
	                eventoProveedor.setId(Integer.parseInt(idEventoProveedor));
	                eventoProveedor.setIdEvento(idEvento);
	                eventoProveedor.setIdProveedor(idProveedor);
	                eventoProveedor.setMontoContrato(montoContrato);
	                eventoProveedor.setDescripcion(descripcion);

	                boolean estado = eventoProveedorDao.actualizar(eventoProveedor);
	                session.setAttribute("mensaje", estado ? "Evento-Proveedor modificado" : "Error al modificar evento-proveedor");

	                response.sendRedirect("EventoProveedorServlet?metodo=listar");
	            }
	        }

	    }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
