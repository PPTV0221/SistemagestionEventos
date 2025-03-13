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

import dao.ProveedorDAO;
import modelo.Proveedor;


@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ProveedorServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String metodo = request.getParameter("metodo");
			HttpSession session = request.getSession();
			ProveedorDAO proveedorDAO = new ProveedorDAO();

			if ("listar".equals(metodo)) {
				List<Proveedor> proveedores = proveedorDAO.listar();
				request.setAttribute("listaProveedores", proveedores);
				RequestDispatcher dispatcher = request.getRequestDispatcher("proveedor.jsp");
				dispatcher.forward(request, response);
			} else if ("agregar".equals(metodo)) {
				String nombre = request.getParameter("nombre");
				String servicio = request.getParameter("servicio");
				String telefono = request.getParameter("telefono");
				String correo = request.getParameter("correo");
				String mensaje;

				Proveedor proveedor = new Proveedor();
				proveedor.setNombre(nombre);
				proveedor.setServicio(servicio);
				proveedor.setTelefono(telefono);
				proveedor.setCorreo(correo);

				boolean estado = proveedorDAO.nuevo(proveedor);
				mensaje = estado ? "Proveedor registrado" : "Error al registrar proveedor";
				session.setAttribute("mensaje", mensaje);
				response.sendRedirect("ProveedorServlet?metodo=listar");
			}
			else if ("editar".equals(metodo)) {
			    String idProveedor = request.getParameter("idProveedor");

			    if (request.getParameter("cargar") != null) {
			        Proveedor proveedor = proveedorDAO.buscarUno(Integer.parseInt(idProveedor));

			        if (proveedor != null) {
			            response.setContentType("application/json");
			            response.setCharacterEncoding("UTF-8");

			            out.print("{\"idProveedor\":\"" + proveedor.getIdProveedor() + "\","
			                    + "\"nombre\":\"" + proveedor.getNombre() + "\","
			                    + "\"servicio\":\"" + proveedor.getServicio() + "\","
			                    + "\"telefono\":\"" + proveedor.getTelefono() + "\","
			                    + "\"correo\":\"" + proveedor.getCorreo() + "\"}");

			            out.flush();
			        } else {
			            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Proveedor no encontrado");
			        }
			    } else {
			        String nombre = request.getParameter("nombre");
			        String servicio = request.getParameter("servicio");
			        String telefono = request.getParameter("telefono");
			        String correo = request.getParameter("correo");

			        Proveedor proveedor = new Proveedor();
			        proveedor.setIdProveedor(Integer.parseInt(idProveedor));
			        proveedor.setNombre(nombre);
			        proveedor.setServicio(servicio);
			        proveedor.setTelefono(telefono);
			        proveedor.setCorreo(correo);

			        boolean estado = proveedorDAO.actualizar(proveedor);
			        String mensaje = estado ? "Proveedor modificado" : "Error al modificar proveedor";

			        session.setAttribute("mensaje", mensaje);
			        response.sendRedirect("ProveedorServlet?metodo=listar");
			    }
			}

			else if ("eliminar".equals(metodo)) {
                String idProveedor = request.getParameter("idProveedor");
                boolean estado= proveedorDAO.eliminar(Integer.parseInt(idProveedor));
                if(!estado) {
                	session.setAttribute("mensaje", "Proveedor eliminado");
                }else {
                	session.setAttribute("mensaje", "Proveedor no eliminado");
                }

                response.sendRedirect("ProveedorServlet?metodo=listar");
            }
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
