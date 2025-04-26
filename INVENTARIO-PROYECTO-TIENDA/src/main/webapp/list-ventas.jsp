<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Ventas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Ventas</h1>
    <form action="ventas" method="get" class="mb-3 d-flex align-items-center">
        <input type="hidden" name="action" value="list">
        <input type="text" name="searchTerm" class="form-control me-2" placeholder="Buscar por ID o Cliente" value="${param.searchTerm}">
        <button type="submit" class="btn btn-primary">Buscar</button>
        <c:if test="${not empty param.searchTerm}">
            <a href="ventas?action=list" class="btn btn-secondary ms-2">Limpiar</a>
        </c:if>
    </form>
    <p><a href="ventas?action=new" class="btn btn-primary">Crear Nueva Venta</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Fecha Venta</th>
            <th>Monto Total</th>
            <th>Empresa</th>
            <th>Cliente</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="venta" items="${listaVentas}">
            <tr>
                <td>${venta.idVenta}</td>
                <td>${venta.fechaVenta}</td>
                <td>${venta.montoTotal}</td>
                <td>${venta.empresa.nombre}</td>
                <td>${venta.cliente.nombre} ${venta.cliente.apellidos} (${venta.cliente.dni})</td>
                <td>
                    <a href="ventas?action=edit&id=${venta.idVenta}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="ventas?action=delete&id=${venta.idVenta}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty listaVentas}">
            <tr>
                <td colspan="6" class="text-center">
                    <c:if test="${not empty param.searchTerm}">
                        No se encontraron ventas para el término de búsqueda "${param.searchTerm}".
                    </c:if>
                    <c:if test="${empty param.searchTerm}">
                        No hay ventas registradas.
                    </c:if>
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