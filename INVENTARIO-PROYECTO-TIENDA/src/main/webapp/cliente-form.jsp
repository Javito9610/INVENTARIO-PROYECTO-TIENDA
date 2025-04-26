<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${cliente != null ? 'Editar Cliente' : 'Crear Cliente'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/clientes?action=list" class="btn btn-secondary">Volver a la lista</a>
    </div>
    <h1>${cliente != null ? 'Editar Cliente' : 'Crear Cliente'}</h1>
    <form action="clientes?action=save" method="post">
        <c:if test="${cliente != null && !empty cliente.dni}">
            <input type="hidden" name="originalDni" value="${cliente.dni}">
        </c:if>
        <div class="mb-3">
            <label for="dni" class="form-label">DNI:</label>
            <input type="text" class="form-control" id="dni" name="dni" value="${cliente.dni}" ${cliente != null ? 'readonly' : ''} required>
        </div>
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre:</label>
            <input type="text" class="form-control" id="nombre" name="nombre" value="${cliente.nombre}" required>
        </div>
        <div class="mb-3">
            <label for="apellidos" class="form-label">Apellidos:</label>
            <input type="text" class="form-control" id="apellidos" name="apellidos" value="${cliente.apellidos}" required>
        </div>
        <div class="mb-3">
            <label for="numero_telefono" class="form-label">Número de Teléfono:</label>
            <input type="text" class="form-control" id="numero_telefono" name="numero_telefono" value="${cliente.numeroTelefono}">
        </div>
        <div class="mb-3">
            <label for="correo" class="form-label">Correo:</label>
            <input type="email" class="form-control" id="correo" name="correo" value="${cliente.correo}">
        </div>
        <div class="mb-3">
            <label for="direccion" class="form-label">Dirección:</label>
            <input type="text" class="form-control" id="direccion" name="direccion" value="${cliente.direccion}">
        </div>
        <div class="mb-3">
            <label for="ciudad" class="form-label">Ciudad:</label>
            <input type="text" class="form-control" id="ciudad" name="ciudad" value="${cliente.ciudad}">
        </div>
        <div class="mb-3">
            <label for="codigo_postal" class="form-label">Código Postal:</label>
            <input type="text" class="form-control" id="codigo_postal" name="codigo_postal" value="${cliente.codigoPostal}">
        </div>
        <div class="mb-3">
            <label for="provincia" class="form-label">Provincia:</label>
            <input type="text" class="form-control" id="provincia" name="provincia" value="${cliente.provincia}">
        </div>
        <div class="mb-3">
            <label for="empresas_id_empresa" class="form-label">Empresa:</label>
            <select class="form-select" id="empresas_id_empresa" name="empresas_id_empresa">
                <option value="">Seleccionar Empresa</option>
                <c:forEach var="empresa" items="${listaEmpresas}">
                    <option value="${empresa.idEmpresa}" ${cliente != null && cliente.empresa != null && cliente.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" id="guardarClienteBtn">Guardar</button>
    </form>
</div>
</body>
</html>