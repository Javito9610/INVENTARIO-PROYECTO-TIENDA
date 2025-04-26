<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lista de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
    <h1>Lista de Productos</h1>
    <div class="mb-3 d-flex align-items-center">
        <input type="text" id="searchInputProductos" class="form-control me-2" placeholder="Buscar por ID, Nombre, Proveedor, etc.">
        <button type="button" id="clearSearchBtnProductos" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
    </div>
    <p><a href="productos?action=new" class="btn btn-primary">Crear Nuevo Producto</a></p>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Unidades</th>
            <th>Proveedor</th>
            <th>Empresa</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="productosTableBody">
        <c:forEach var="producto" items="${listaProductos}">
            <tr>
                <td>${producto.idProducto}</td>
                <td>${producto.nombre}</td>
                <td>${producto.precio}</td>
                <td>${producto.unidades}</td>
                <td>${producto.proveedor != null ? producto.proveedor.nombre : 'N/A'}</td>
                <td>${producto.empresa != null ? producto.empresa.nombre : 'N/A'}</td>
                <td>
                    <a href="productos?action=edit&id=${producto.idProducto}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="productos?action=delete&id=${producto.idProducto}" class="btn btn-sm btn-danger">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <tr id="noResultsRowProductos" style="display: none;">
            <td colspan="7" class="text-center">
                No se encontraron resultados.
            </td>
        </tr>
        <c:if test="${empty listaProductos}">
            <tr id="initiallyEmptyRowProductos">
                <td colspan="7" class="text-center">
                    No hay productos registrados.
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