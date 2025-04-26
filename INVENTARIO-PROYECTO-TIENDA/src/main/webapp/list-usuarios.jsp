<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Usuarios</h1>
    <div class="mb-3 d-flex align-items-center">
        <input type="text" id="searchInputUsuarios" class="form-control me-2" placeholder="Buscar por ID, Nombre, Correo, etc.">
        <button type="button" id="clearSearchBtnUsuarios" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
    </div>
    <p><a href="usuarios?action=new" class="btn btn-primary">Crear Nuevo Usuario</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Empresa</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="usuariosTableBody">
        <c:forEach var="usuario" items="${listaUsuarios}">
            <tr>
                <td>${usuario.idUsuario}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.apellido}</td>
                <td>${usuario.correo}</td>
                <td>${usuario.rol}</td>
                <td>${usuario.empresa != null ? usuario.empresa.nombre : 'N/A'}</td>
                <td>
                    <a href="usuarios?action=edit&id=${usuario.idUsuario}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="usuarios?action=delete&id=${usuario.idUsuario}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <tr id="noResultsRowUsuarios" style="display: none;">
            <td colspan="7" class="text-center">
                No se encontraron resultados.
            </td>
        </tr>
        <c:if test="${empty listaUsuarios}">
            <tr id="initiallyEmptyRowUsuarios">
                <td colspan="7" class="text-center">
                    No hay usuarios registrados.
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
