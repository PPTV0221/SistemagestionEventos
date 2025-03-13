<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.Asistente" %>
<%@page import="modelo.Evento" %>
<%@page import="modelo.Comunicacion" %>
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
    
    <h2 class="text-center">Lista de Comunicaciones</h2>
	<button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#modalAgregar">Agregar Comunicación</button>
	
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
	            <th>Tipo</th>
	            <th>Mensaje</th>
	            <th>Fecha de Envío</th>
	            <th>Acciones</th>
	        </tr>
	    </thead>
	    <tbody>
	        <% List<Comunicacion> listaComunicaciones = (List<Comunicacion>) request.getAttribute("listaComunicaciones");
	           if (listaComunicaciones != null && !listaComunicaciones.isEmpty()) {
	               for (Comunicacion comunicacion : listaComunicaciones) { %>
	        <tr>
	            <td><%= comunicacion.getIdComunicacion() %></td>
	            <td><%= comunicacion.getNombreEvento() %></td>
	            <td><%= comunicacion.getNombreAsistente() %></td>
	            <td><%= comunicacion.getTipo() %></td>
	            <td><%= comunicacion.getMensaje() %></td>
	            <td><%= comunicacion.getFechaEnvio() %></td>
	            <td>
	                <button class="btn btn-warning btn-sm" onclick="cargarComunicacion('<%= comunicacion.getIdComunicacion() %>')">Editar</button>
	                <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#modalEliminar" data-id="<%= comunicacion.getIdComunicacion() %>">Eliminar</button>
	            </td>
	        </tr>
	        <% } } else { %>
	        <tr>
	            <td colspan="7" class="text-center">No hay comunicaciones registradas.</td>
	        </tr>
	        <% } %>
	    </tbody>
	</table>
	
	<div class="modal fade" id="modalAgregar" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">Agregar Comunicación</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="ComunicacionServlet" method="POST">
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
	                        <label class="form-label">Tipo</label>
	                        <select name="tipo" class="form-control" required>
	                            <option value="">Seleccione el tipo</option>
	                            <option value="notificacion">Notificación</option>
	                            <option value="recordatorio">Recordatorio</option>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Mensaje</label>
	                        <textarea name="mensaje" class="form-control" required></textarea>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Fecha de Envío</label>
	                        <input type="date" name="fechaEnvio" class="form-control" required>
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
	    
	    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>