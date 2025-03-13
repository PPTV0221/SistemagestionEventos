<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.Asistente" %>
<%@page import="modelo.Evento" %>
<%@page import="modelo.Entrada" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Entradas</title>
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
    
    <h2 class="text-center">Lista de Entradas</h2>
	<button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#modalAgregar">Agregar Entrada</button>
	
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
	            <th>Fecha de Compra</th>
	            <th>Método de Pago</th>
	            <th>Acciones</th>
	        </tr>
	    </thead>
	    <tbody>
	        <% List<Entrada> listaEntradas = (List<Entrada>) request.getAttribute("listaEntradas");
	           if (listaEntradas != null && !listaEntradas.isEmpty()) {
	               for (Entrada entrada : listaEntradas) { %>
	        <tr>
	            <td><%= entrada.getIdEntrada() %></td>
	            
	            <td><%= entrada.getNombreEvento() %></td>
	            <td><%= entrada.getNombreAsistente() %></td>
	            <td><%= entrada.getFechaCompra() %></td>
	            <td><%= entrada.getMetodoPago() %></td>
	            <td>
	                <button class="btn btn-warning btn-sm" onclick="cargarEntrada('<%= entrada.getIdEntrada() %>')">Editar</button>
	                <button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#modalEliminar" data-id="<%= entrada.getIdEntrada() %>">Cancelar entrada</button>
	            </td>
	        </tr>
	        <% } } else { %>
	        <tr>
	            <td colspan="6" class="text-center">No hay entradas disponibles.</td>
	        </tr>
	        <% } %>
	    </tbody>
	</table>
	
	<!-- Modal Agregar Entrada -->
	<div class="modal fade" id="modalAgregar" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">Agregar Entrada</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="EntradaServlet" method="POST">
	                <div class="modal-body">
	                    <input type="hidden" name="metodo" value="agregar">
	
						<div class="mb-3">
	                        <label class="form-label">Fecha de Compra</label>
	                        <%
							    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							    String fechaHoy = sdf.format(new Date());
							%>

							<input type="date" name="fechaCompra" class="form-control" required readonly value="<%= fechaHoy %>">
	                    </div>
	                    
	                    <div class="mb-3">
	                        <label class="form-label">Evento</label>
	                        <select name="idEvento" id="idEvento" class="form-control" required>
	                            <option value="0">Seleccione un evento</option>
	                            <% List<Evento> listaEventos = (List<Evento>) request.getAttribute("listaEventos");
	                               if (listaEventos != null) {
	                                   for (Evento evento : listaEventos) { %>
	                            <option value="<%= evento.getIdEvento() %>"><%= evento.getNombre() %></option>
	                            <% } } %>
	                        </select>
	                    </div>
	                    
	                    <div class="mb-3">
	                        <label class="form-label">Participantes</label>
	                        <select name="idAsistente" id="idAsistente" class="form-control" required>
	                        </select>
	                    </div>
	
	                    <div class="mb-3">
	                        <label class="form-label">Método de Pago</label>
	                        <select name="metodoPago" class="form-control" required>
	                            <option value="tarjeta">Tarjeta</option>
	                            <option value="paypal">PayPal</option>
	                            <option value="efectivo">Efectivo</option>
	                            <option value="transferencia">Transferencia</option>
	                        </select>
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
	    
	
	
	
	
	<!-- Modal Eliminar -->
	<div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEliminarLabel">Confirmar Eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="EntradaServlet" method="POST">
                    <div class="modal-body">
                        <input type="hidden" name="metodo" value="eliminar">
                        <input type="hidden" name="idEntrada" id="deleteId">
                        <p>¿Estás seguro de eliminar esta entrada?</p>
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
	    
	    function cargar_participantes() {
	        let selectEvento = document.getElementById("idEvento");
	        let id_evento = selectEvento.value;
	        let selectParticipantes = document.getElementById("idAsistente");

	        selectParticipantes.innerHTML = '<option value="">Seleccione un participante</option>';

	        fetch("EntradaServlet?metodo=buscar_participantes&idEvento=" + id_evento)
	            .then(response => response.json())
	            .then(data => {
	                data.forEach(participante => {
	                    let option = document.createElement("option");
	                    option.value = participante.idAsistente;
	                    option.textContent = participante.nombre;
	                    selectParticipantes.appendChild(option);
	                });
	            })
	            .catch(error => console.error("Error al cargar datos:", error));
	    }

	    document.getElementById("idEvento").addEventListener("change", cargar_participantes);

        var modalEliminar = document.getElementById('modalEliminar');
        modalEliminar.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            document.getElementById('deleteId').value = button.getAttribute('data-id');
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>