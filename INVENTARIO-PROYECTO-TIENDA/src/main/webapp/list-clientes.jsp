<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Clientes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Clientes</h1>
    <div class="mb-3 d-flex align-items-center">
        <input type="text" id="searchInput" class="form-control me-2" placeholder="Buscar por Nombre, DNI, etc.">
        <button type="button" id="clearSearchBtn" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
    </div>
    <p><a href="clientes?action=new" class="btn btn-primary">Crear Nuevo Cliente</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>DNI</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Teléfono</th>
            <th>Correo</th>
            <th>Ciudad</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="clientsTableBody">
        <c:forEach var="cliente" items="${listaClientes}">
            <tr>
                <td>${cliente.dni}</td>
                <td>${cliente.nombre}</td>
                <td>${cliente.apellidos}</td>
                <td>${cliente.numeroTelefono}</td>
                <td>${cliente.correo}</td>
                <td>${cliente.ciudad}</td>
                <td>
                    <a href="clientes?action=edit&dni=${cliente.dni}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="clientes?action=delete&dni=${cliente.dni}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <tr id="noResultsRow" style="display: none;">
            <td colspan="7" class="text-center">
                No se encontraron resultados.
            </td>
        </tr>
        <c:if test="${empty listaClientes}">
            <tr id="initiallyEmptyRow">
                <td colspan="7" class="text-center">
                    No hay clientes registrados.
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>