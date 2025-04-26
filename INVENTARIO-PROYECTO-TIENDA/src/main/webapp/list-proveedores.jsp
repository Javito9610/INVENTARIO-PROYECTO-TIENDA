<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Proveedores</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Proveedores</h1>
    <div class="mb-3 d-flex align-items-center">
        <input type="text" id="searchInputProveedores" class="form-control me-2" placeholder="Buscar por ID, Nombre, Contacto, etc.">
        <button type="button" id="clearSearchBtnProveedores" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
    </div>
    <p><a href="proveedores?action=new" class="btn btn-primary">Crear Nuevo Proveedor</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Contacto</th>
            <th>Teléfono</th>
            <th>Ciudad</th>
            <th>País</th>
            <th>Fecha Registro</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="proveedoresTableBody">
        <c:forEach var="proveedor" items="${listaProveedores}">
            <tr>
                <td>${proveedor.idProveedores}</td>
                <td>${proveedor.nombre}</td>
                <td>${proveedor.contacto}</td>
                <td>${proveedor.telefono}</td>
                <td>${proveedor.ciudad}</td>
                <td>${proveedor.pais}</td>
                <td>${proveedor.fechaRegistro}</td>
                <td>
                    <a href="proveedores?action=edit&id=${proveedor.idProveedores}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="proveedores?action=delete&id=${proveedor.idProveedores}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <tr id="noResultsRowProveedores" style="display: none;">
            <td colspan="8" class="text-center">
                No se encontraron resultados.
            </td>
        </tr>
        <c:if test="${empty listaProveedores}">
            <tr id="initiallyEmptyRowProveedores">
                <td colspan="8" class="text-center">
                    No hay proveedores registrados.
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