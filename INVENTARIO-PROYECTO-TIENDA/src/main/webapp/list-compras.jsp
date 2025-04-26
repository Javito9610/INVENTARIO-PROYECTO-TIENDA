<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Compras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Compras</h1>
    <div class="mb-3 d-flex align-items-center">
        <input type="text" id="searchInputCompras" class="form-control me-2" placeholder="Buscar por ID, Proveedor, etc.">
        <button type="button" id="clearSearchBtnCompras" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
    </div>
    <p><a href="compras?action=new" class="btn btn-primary">Crear Nueva Compra</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Fecha Compra</th>
            <th>Importe Total</th>
            <th>Proveedor</th>
            <th>Empresa</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="comprasTableBody">
        <c:forEach var="compra" items="${listaCompras}">
            <tr>
                <td>${compra.idCompra}</td>
                <td>${compra.fechaCompra}</td>
                <td>${compra.montoTotal}</td>
                <td>${compra.proveedor.nombre}</td>
                <td>${compra.empresa.nombre}</td>
                <td>
                    <a href="compras?action=edit&id=${compra.idCompra}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="compras?action=delete&id=${compra.idCompra}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <tr id="noResultsRowCompras" style="display: none;">
            <td colspan="6" class="text-center">
                No se encontraron resultados.
            </td>
        </tr>
        <c:if test="${empty listaCompras}">
            <tr id="initiallyEmptyRowCompras">
                <td colspan="6" class="text-center">
                    No hay compras registradas.
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