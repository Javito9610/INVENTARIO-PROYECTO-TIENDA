<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Empresas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Empresas</h1>
    <div class="mb-3 d-flex align-items-center">
        <input type="text" id="searchInputEmpresas" class="form-control me-2" placeholder="Buscar por ID, Nombre, etc.">
        <button type="button" id="clearSearchBtnEmpresas" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
    </div>
    <p><a href="empresas?action=new" class="btn btn-primary">Crear Nueva Empresa</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Dirección</th>
            <th>Teléfono</th>
            <th>Correo Electrónico</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="empresasTableBody">
        <c:forEach var="empresa" items="${listaEmpresas}">
            <tr>
                <td>${empresa.idEmpresa}</td>
                <td>${empresa.nombre}</td>
                <td>${empresa.direccion}</td>
                <td>${empresa.telefono}</td>
                <td>${empresa.correoElectronico}</td>
                <td>
                    <a href="empresas?action=edit&id=${empresa.idEmpresa}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="empresas?action=delete&id=${empresa.idEmpresa}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <tr id="noResultsRowEmpresas" style="display: none;">
            <td colspan="6" class="text-center">
                No se encontraron resultados.
            </td>
        </tr>
        <c:if test="${empty listaEmpresas}">
            <tr id="initiallyEmptyRowEmpresas">
                <td colspan="6" class="text-center">
                    No hay empresas registradas.
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