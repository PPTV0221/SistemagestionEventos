<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.Evento" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">

</head>
<body>
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
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    
</body>
</html>