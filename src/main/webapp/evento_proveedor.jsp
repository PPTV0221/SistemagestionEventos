<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.Proveedor" %>
<%@page import="modelo.Evento" %>
<%@page import="modelo.EventoProveedor" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Evento - Proveedor</title>
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
    
    <h2 class="text-center">Lista de Evento - Proveedor</h2>
	<button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#modalAgregar">Agregar Evento-Proveedor</button>
	
	<% String mensaje = (String) session.getAttribute("mensaje");
	   if (mensaje != null) { %>
	<div class="alert alert-info alert-dismissible fade show" role="alert">
	    <%= mensaje %>
	    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
	<% session.removeAttribute("mensaje"); } %>
	
	<table class="table table-bordered">
	    <thead class="table-dark">
	        <tr>
	            <th>ID</th>
	            <th>Evento</th>
	            <th>Proveedor</th>
	            <th>Monto Contrato</th>
	            <th>Descripción</th>
	            <th>Acciones</th>
	        </tr>
	    </thead>
	    <tbody>
	        <% List<EventoProveedor> listaEventoProveedores = (List<EventoProveedor>) request.getAttribute("listaEventoProveedores");
	           if (listaEventoProveedores != null && !listaEventoProveedores.isEmpty()) {
	               for (EventoProveedor eventoProveedor : listaEventoProveedores) { %>
	        <tr>
	            <td><%= eventoProveedor.getId() %></td>
	            <td><%= eventoProveedor.getNombreEvento() %></td>
	            <td><%= eventoProveedor.getNombreProveedor() %></td>
	            <td><%= eventoProveedor.getMontoContrato() %></td>
	            <td><%= eventoProveedor.getDescripcion() %></td>
	            <td>
	                <button class="btn btn-warning btn-sm" onclick="cargarEventoProveedor('<%= eventoProveedor.getId() %>')">Editar</button>
	                <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#modalEliminar" data-id="<%= eventoProveedor.getId() %>">Eliminar</button>
	            </td>
	        </tr>
	        <% } } else { %>
	        <tr>
	            <td colspan="6" class="text-center">No hay registros disponibles.</td>
	        </tr>
	        <% } %>
	    </tbody>
	</table>
	
	<!-- Modal Agregar Evento-Proveedor -->
	<div class="modal fade" id="modalAgregar" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">Agregar Evento-Proveedor</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="EventoProveedorServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="agregar">
	
	                    <div class="mb-3">
	                        <label class="form-label">Evento</label>
	                        <select name="idEvento" class="form-control" required>
	                            <option value="">Seleccione un evento</option>
	                            <% List<Evento> listaEventos = (List<Evento>) request.getAttribute("listaEventos");
	                               if (listaEventos != null) {
	                                   for (Evento evento : listaEventos) { %>
	                            <option value="<%= evento.getIdEvento() %>"><%= evento.getNombre() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Proveedor</label>
	                        <select name="idProveedor" class="form-control" required>
	                            <option value="">Seleccione un proveedor</option>
	                            <% List<Proveedor> listaProveedores = (List<Proveedor>) request.getAttribute("listaProveedores");
	                               if (listaProveedores != null) {
	                                   for (Proveedor proveedor : listaProveedores) { %>
	                            <option value="<%= proveedor.getIdProveedor() %>"><%= proveedor.getNombre() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Monto Contrato</label>
	                        <input type="number" step="0.01" name="montoContrato" class="form-control" required>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Descripción</label>
	                        <textarea name="descripcion" class="form-control" rows="3" required></textarea>
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
	
	<div class="modal fade" id="modalEditarEventoProveedor" tabindex="-1" aria-labelledby="modalEditarEventoProveedorLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="modalEditarEventoProveedorLabel">Editar Evento-Proveedor</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="EventoProveedorServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="editar">
	
	                    <div class="mb-3">
	                        <label class="form-label">ID Evento-Proveedor</label>
	                        <input type="text" name="idEventoProveedor" id="editIdEventoProveedor" class="form-control" readonly>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Evento</label>
	                        <select name="idEvento" id="editIdEvento" class="form-control" required>
	                            <option value="">Seleccione un evento</option>
	                            <% listaEventos = (List<Evento>) request.getAttribute("listaEventos");
	                               if (listaEventos != null) {
	                                   for (Evento evento : listaEventos) { %>
	                            <option value="<%= evento.getIdEvento() %>"><%= evento.getNombre() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Proveedor</label>
	                        <select name="idProveedor" id="editIdProveedor" class="form-control" required>
	                            <option value="">Seleccione un proveedor</option>
	                            <% listaProveedores = (List<Proveedor>) request.getAttribute("listaProveedores");
	                               if (listaProveedores != null) {
	                                   for (Proveedor proveedor : listaProveedores) { %>
	                            <option value="<%= proveedor.getIdProveedor() %>"><%= proveedor.getNombre() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Monto Contrato</label>
	                        <input type="number" step="0.01" name="montoContrato" id="editMontoContrato" class="form-control" required>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Descripción</label>
	                        <textarea name="descripcion" id="editDescripcion" class="form-control" rows="3" required></textarea>
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
		

	<div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEliminarLabel">Confirmar Eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="EventoProveedorServlet" method="POST">
                    <div class="modal-body">
                        <input type="hidden" name="metodo" value="eliminar">
                        <input type="hidden" name="id" id="deleteId">
                        <p>¿Estás seguro de eliminar la asignación de proveedor al evento?</p>
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
	    function cargarEventoProveedor(idEventoProveedor) {
	        fetch("EventoProveedorServlet?metodo=editar&idEventoProveedor=" + idEventoProveedor + "&cargar=true")
	            .then(response => response.json())
	            .then(data => {
	                document.getElementById("editIdEventoProveedor").value = data.idEventoProveedor;
	                document.getElementById("editMontoContrato").value = data.montoContrato;
	                document.getElementById("editDescripcion").value = data.descripcion;
	
	                let eventoSelect = document.getElementById("editIdEvento");
	                for (let option of eventoSelect.options) {
	                    if (option.value == data.idEvento) {
	                        option.selected = true;
	                        break;
	                    }
	                }
	
	                let proveedorSelect = document.getElementById("editIdProveedor");
	                for (let option of proveedorSelect.options) {
	                    if (option.value == data.idProveedor) {
	                        option.selected = true;
	                        break;
	                    }
	                }
	
	                var modal = new bootstrap.Modal(document.getElementById("modalEditarEventoProveedor"));
	                modal.show();
	            })
	            .catch(error => console.error("Error al cargar evento-proveedor:", error));
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