<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Proveedores</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">

</head>
<body class="container mt-4">
     <nav class="navbar navbar-expand-lg navbar-light bg-light">
	    <div class="container-fluid">
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	
	        <div class="collapse navbar-collapse" id="navbarSupportedContent">
	            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	                <li class="nav-item">
	                    <a class="nav-link active" href="EventoServlet?metodo=listar">Eventos</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link active" href="AsistenteServlet?metodo=listar">Asistentes</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link active" href="InscripcionServlet?metodo=listar">Inscripciones</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link active" href="EntradaServlet?metodo=listar">Entradas</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link active" href="ProveedorServlet?metodo=listar">Proveedores</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link active" href="EventoProveedorServlet?metodo=listar">Eventos - Proveedores</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link active" href="ComunicacionServlet?metodo=listar">Enviar Notificación/Recordatorio</a>
	                </li>
	            </ul>
	        </div>
	    </div>
	</nav>
    

    <h2 class="text-center">Lista de Proveedores</h2>
	<button class="btn btn-primary mb-3" data-bs-toggle="modal"
	    data-bs-target="#modalAgregar">Agregar Proveedor</button>
	
	<%
	String mensaje = (String) session.getAttribute("mensaje");
	if (mensaje != null) {
	%>
	<div class="alert alert-info alert-dismissible fade show" role="alert">
	    <%=mensaje%>
	    <button type="button" class="btn-close" data-bs-dismiss="alert"
	        aria-label="Close"></button>
	</div>
	<%
	session.removeAttribute("mensaje");
	}
	%>
	
	<table class="table table-bordered">
	    <thead class="table-dark">
	        <tr>
	            <th>ID</th>
	            <th>Nombre</th>
	            <th>Servicio</th>
	            <th>Teléfono</th>
	            <th>Correo</th>
	            <th>Acciones</th>
	        </tr>
	    </thead>
	    <tbody>
	        <%
	        List<Proveedor> listaProveedores = (List<Proveedor>) request.getAttribute("listaProveedores");
	        if (listaProveedores != null && !listaProveedores.isEmpty()) {
	            for (Proveedor proveedor : listaProveedores) {
	        %>
	        <tr>
	            <td><%=proveedor.getIdProveedor()%></td>
	            <td><%=proveedor.getNombre()%></td>
	            <td><%=proveedor.getServicio()%></td>
	            <td><%=proveedor.getTelefono() != null ? proveedor.getTelefono() : "N/A"%></td>
	            <td><%=proveedor.getCorreo()%></td>
	            <td>
	                <button class="btn btn-warning btn-sm"
	                    onclick="cargarProveedor('<%=proveedor.getIdProveedor()%>')">Editar</button>
	                <button class="btn btn-danger btn-sm" data-bs-toggle="modal"
	                    data-bs-target="#modalEliminar"
	                    data-id="<%=proveedor.getIdProveedor()%>">Eliminar</button>
	            </td>
	        </tr>
	        <%
	        }
	        } else {
	        %>
	        <tr>
	            <td colspan="6" class="text-center">No hay proveedores disponibles.</td>
	        </tr>
	        <%
	        }
	        %>
	    </tbody>
	</table>
	
	
	<!-- Modal Agregar -->
	<div class="modal fade" id="modalAgregar" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="modalAgregarLabel">Agregar Proveedor</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="ProveedorServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="agregar">
	                    
	                    <div class="mb-3">
	                        <label class="form-label">Nombre</label>
	                        <input type="text" name="nombre" class="form-control" required>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Servicio</label>
	                        <input type="text" name="servicio" class="form-control" required>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Teléfono</label>
	                        <input type="text" name="telefono" class="form-control">
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Correo</label>
	                        <input type="email" name="correo" class="form-control" required>
	                    </div>
	
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
	                    <button type="submit" class="btn btn-primary">Guardar</button>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	
	<!-- Modal Editar -->
	<div class="modal fade" id="modalEditar" tabindex="-1" aria-labelledby="modalEditarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="modalEditarLabel">Editar Proveedor</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="ProveedorServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="editar">
	                    
	                    <div class="mb-3">
	                        <label class="form-label">ID Proveedor</label>
	                        <input type="text" name="idProveedor" id="editId" class="form-control" readonly>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Nombre</label>
	                        <input type="text" name="nombre" id="editNombre" class="form-control" required>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Servicio</label>
	                        <input type="text" name="servicio" id="editServicio" class="form-control" required>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Teléfono</label>
	                        <input type="text" name="telefono" id="editTelefono" class="form-control">
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Correo</label>
	                        <input type="email" name="correo" id="editCorreo" class="form-control" required>
	                    </div>
	
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
	                    <button type="submit" class="btn btn-warning">Actualizar</button>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	

    <!-- Modal Eliminar -->
    <div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEliminarLabel">Confirmar Eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="ProveedorServlet" method="POST">
                    <div class="modal-body">
                        <input type="hidden" name="metodo" value="eliminar">
                        <input type="hidden" name="idProveedor" id="deleteId">
                        <p>¿Estás seguro de eliminar este proveedor?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-danger">Eliminar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="modalEliminarLabel">Confirmar Eliminación</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="EventoServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="eliminar">
	                    <input type="hidden" name="idEvento" id="deleteId">
	                    <p>¿Estás seguro de eliminar este evento?</p>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
	                    <button type="submit" class="btn btn-danger">Eliminar</button>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	    
    <script>
	    function cargarProveedor(idProveedor) {
	        fetch("ProveedorServlet?metodo=editar&idProveedor=" + idProveedor + "&cargar=true")
	            .then(response => {
	                if (!response.ok) {
	                    throw new Error("Error al obtener datos del proveedor");
	                }
	                return response.json();
	            })
	            .then(data => {
	                document.getElementById("editId").value = data.idProveedor;
	                document.getElementById("editNombre").value = data.nombre;
	                document.getElementById("editServicio").value = data.servicio;
	                document.getElementById("editTelefono").value = data.telefono;
	                document.getElementById("editCorreo").value = data.correo;
	
	                var modal = new bootstrap.Modal(document.getElementById("modalEditar"));
	                modal.show();
	            })
	            .catch(error => console.error("Error al cargar datos:", error));
	    }

	

	    var modalEliminar = document.getElementById('modalEliminar');
	    modalEliminar.addEventListener('show.bs.modal', function (event) {
	        var button = event.relatedTarget;
	        document.getElementById('deleteId').value = button.getAttribute('data-id');
	    });
	</script>
    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

</body>
</html>