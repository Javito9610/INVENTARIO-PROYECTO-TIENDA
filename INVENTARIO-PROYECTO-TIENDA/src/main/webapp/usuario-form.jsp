<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${usuario != null ? 'Editar Usuario' : 'Crear Usuario'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/usuarios?action=list" class="btn btn-secondary">Volver a la lista</a>
    </div>
    <h1>${usuario != null ? 'Editar Usuario' : 'Crear Usuario'}</h1>
    <form action="usuarios?action=save" method="post">
        <input type="hidden" name="id" value="${usuario.idUsuario}">
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre:</label>
            <input type="text" class="form-control" id="nombre" name="nombre" value="${usuario.nombre}">
        </div>
        <div class="mb-3">
            <label for="apellido" class="form-label">Apellido:</label>
            <input type="text" class="form-control" id="apellido" name="apellido" value="${usuario.apellido}">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Correo:</label>
            <input type="email" class="form-control" id="email" name="correo" value="${usuario.correo}">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Contraseña:</label>
            <input type="password" class="form-control" id="password" name="contrasena" ${usuario != null ? 'placeholder="Dejar en blanco para no cambiar"' : ''}>
        </div>
        <div class="mb-3">
            <label for="rol" class="form-label">Rol:</label>
            <select class="form-select" id="rol" name="rol">
                <option value="admin" ${usuario != null && usuario.rol == 'admin' ? 'selected' : ''}>Administrador</option>
                <option value="empleado" ${usuario != null && usuario.rol == 'empleado' ? 'selected' : ''}>Empleado</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="empresaId" class="form-label">Empresa:</label>
            <select class="form-select" id="empresaId" name="empresaId">
                <option value="">Seleccionar Empresa</option>
                <c:forEach var="empresa" items="${listaEmpresas}">
                    <option value="${empresa.idEmpresa}" ${usuario != null && usuario.empresa != null && usuario.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" id="guardarUsuarioBtn">Guardar</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>