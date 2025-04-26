<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${proveedor != null ? 'Editar Proveedor' : 'Crear Proveedor'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/proveedores?action=list" class="btn btn-secondary">Volver a la lista</a>
    </div>
    <h1>${proveedor != null ? 'Editar Proveedor' : 'Crear Proveedor'}</h1>
    <form action="proveedores?action=save" method="post">
        <c:if test="${proveedor != null}">
            <input type="hidden" name="id" value="${proveedor.idProveedores}">
        </c:if>
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre:</label>
            <input type="text" class="form-control" id="nombre" name="nombre" value="${proveedor.nombre}" required>
        </div>
        <div class="mb-3">
            <label for="contacto" class="form-label">Contacto:</label>
            <input type="text" class="form-control" id="contacto" name="contacto" value="${proveedor.contacto}" required>
        </div>
        <div class="mb-3">
            <label for="telefono" class="form-label">Teléfono:</label>
            <input type="text" class="form-control" id="telefono" name="telefono" value="${proveedor.telefono}" required>
        </div>
        <div class="mb-3">
            <label for="direccion" class="form-label">Dirección:</label>
            <input type="text" class="form-control" id="direccion" name="direccion" value="${proveedor.direccion}">
        </div>
        <div class="mb-3">
            <label for="ciudad" class="form-label">Ciudad:</label>
            <input type="text" class="form-control" id="ciudad" name="ciudad" value="${proveedor.ciudad}">
        </div>
        <div class="mb-3">
            <label for="provincia" class="form-label">Provincia:</label>
            <input type="text" class="form-control" id="provincia" name="provincia" value="${proveedor.provincia}">
        </div>
        <div class="mb-3">
            <label for="codigo_postal" class="form-label">Código Postal:</label>
            <input type="text" class="form-control" id="codigo_postal" name="codigo_postal" value="${proveedor.codigoPostal}">
        </div>
        <div class="mb-3">
            <label for="pais" class="form-label">País:</label>
            <input type="text" class="form-control" id="pais" name="pais" value="${proveedor.pais}">
        </div>
        <div class="mb-3">
            <label for="rfc_nit" class="form-label">RFC/NIT:</label>
            <input type="text" class="form-control" id="rfc_nit" name="rfc_nit" value="${proveedor.rfcNit}">
        </div>
        <div class="mb-3">
            <label for="tipo" class="form-label">Tipo:</label>
            <input type="text" class="form-control" id="tipo" name="tipo" value="${proveedor.tipo}">
        </div>
        <div class="mb-3">
            <label for="sitio_web" class="form-label">Sitio Web:</label>
            <input type="url" class="form-control" id="sitio_web" name="sitio_web" value="${proveedor.sitioWeb}">
        </div>
        <div class="mb-3">
            <label for="fecha_registro" class="form-label">Fecha Registro:</label>
            <input type="date" class="form-control" id="fecha_registro" name="fecha_registro" value="${proveedor.fechaRegistro}">
        </div>
        <div class="mb-3">
            <label for="empresas_id_empresa" class="form-label">Empresa:</label>
            <select class="form-select" id="empresas_id_empresa" name="empresaId" required>
                <option value="">Seleccionar Empresa</option>
                <c:forEach var="empresa" items="${listaEmpresas}">
                    <option value="${empresa.idEmpresa}" ${proveedor != null && proveedor.empresa != null && proveedor.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" id="guardarProveedorBtn">Guardar</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>