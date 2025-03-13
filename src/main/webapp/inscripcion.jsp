<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.Asistente" %>
<%@page import="modelo.Evento" %>
<%@page import="modelo.Inscripcion" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Asistentes</title>
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
    
    <h2 class="text-center">Lista de Inscripciones</h2>
	<button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#modalAgregar">Agregar Inscripción</button>
	
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
	            <th>Asistente</th>
	            <th>Fecha de Inscripción</th>
	            <th>Acciones</th>
	        </tr>
	    </thead>
	    <tbody>
	        <% List<Inscripcion> listaInscripciones = (List<Inscripcion>) request.getAttribute("listaInscripciones");
	           if (listaInscripciones != null && !listaInscripciones.isEmpty()) {
	               for (Inscripcion inscripcion : listaInscripciones) { %>
	        <tr>
	            <td><%= inscripcion.getIdInscripcion() %></td>
	            <td><%= inscripcion.getNomEvento() %></td>
	            <td><%= inscripcion.getNombreAsistente() %></td>
	            <td><%= inscripcion.getFechaInscripcion() %></td>
	            <td>
	                 <button class="btn btn-warning btn-sm" onclick="cargarInscripcion('<%= inscripcion.getIdInscripcion() %>')">Editar</button>
	            
	                <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#modalEliminar" data-id="<%= inscripcion.getIdInscripcion() %>">Eliminar</button>
	            </td>
	        </tr>
	        <% } } else { %>
	        <tr>
	            <td colspan="5" class="text-center">No hay inscripciones disponibles.</td>
	        </tr>
	        <% } %>
	    </tbody>
	</table>
	
	<!-- Modal Agregar Inscripción -->
	<div class="modal fade" id="modalAgregar" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">Agregar Inscripción</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="InscripcionServlet" method="POST">
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
	                        <label class="form-label">Asistente</label>
	                        <select name="idAsistente" class="form-control" required>
	                            <option value="">Seleccione un asistente</option>
	                            <% List<Asistente> listaAsistentes = (List<Asistente>) request.getAttribute("listaAsistentes");
	                               if (listaAsistentes != null) {
	                                   for (Asistente asistente : listaAsistentes) { %>
	                            <option value="<%= asistente.getIdAsistente() %>"><%= asistente.getNombre() %> <%= asistente.getApellidoP() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Fecha de Inscripción</label>
	                        <input type="date" name="fechaInscripcion" class="form-control" required>
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
	
	
	<div class="modal fade" id="modalEditarInscripcion" tabindex="-1" aria-labelledby="modalEditarInscripcionLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="modalEditarInscripcionLabel">Editar Inscripción</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="InscripcionServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="editar">
	                    
	                    <div class="mb-3">
	                        <label class="form-label">ID Inscripción</label>
	                        <input type="text" name="idInscripcion" id="editIdInscripcion" class="form-control" readonly>
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
	                        <label class="form-label">Asistente</label>
	                        <select name="idAsistente" id="editIdAsistente" class="form-control" required>
	                            <option value="">Seleccione un asistente</option>
	                            <% listaAsistentes = (List<Asistente>) request.getAttribute("listaAsistentes");
	                               if (listaAsistentes != null) {
	                                   for (Asistente asistente : listaAsistentes) { %>
	                            <option value="<%= asistente.getIdAsistente() %>"><%= asistente.getNombre() %> <%= asistente.getApellidoP() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Fecha de Inscripción</label>
	                        <input type="date" name="fechaInscripcion" id="editFechaInscripcion" class="form-control" required>
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
                <form action="AsistenteServlet" method="POST">
                    <div class="modal-body">
                        <input type="hidden" name="metodo" value="eliminar">
                        <input type="hidden" name="idAsistente" id="deleteId">
                        <p>¿Estás seguro de eliminar esta inscripción?</p>
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
	    function cargarInscripcion(idInscripcion) {
	        fetch("InscripcionServlet?metodo=editar&idInscripcion=" + idInscripcion + "&cargar=true")
	            .then(response => response.json())
	            .then(data => {
	                document.getElementById("editIdInscripcion").value = data.idInscripcion;
	                document.getElementById("editFechaInscripcion").value = data.fechaInscripcion;
	
	                let eventoSelect = document.getElementById("editIdEvento");
	                for (let option of eventoSelect.options) {
	                    if (option.value == data.idEvento) {
	                        option.selected = true;
	                        break;
	                    }
	                }
	
	                let asistenteSelect = document.getElementById("editIdAsistente");
	                for (let option of asistenteSelect.options) {
	                    if (option.value == data.idAsistente) {
	                        option.selected = true;
	                        break;
	                    }
	                }
	
	                var modal = new bootstrap.Modal(document.getElementById("modalEditarInscripcion"));
	                modal.show();
	            })
	            .catch(error => console.error("Error al cargar inscripción:", error));
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