<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>${producto != null ? 'Editar Producto' : 'Crear Producto'}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/js/script.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <div class="mb-3">
    <a href="${pageContext.request.contextPath}/productos?action=list" class="btn btn-secondary">Volver a la lista</a>
  </div>
  <h1>${producto != null ? 'Editar Producto' : 'Crear Producto'}</h1>
  <form action="productos?action=save" method="post">
    <c:if test="${producto != null}">
      <input type="hidden" name="id" value="${producto.idProducto}">
    </c:if>
    <div class="mb-3">
      <label for="nombre" class="form-label">Nombre:</label>
      <input type="text" class="form-control" id="nombre" name="nombre" value="${producto.nombre}" required>
    </div>
    <div class="mb-3">
      <label for="descripcion" class="form-label">Descripción:</label>
      <textarea class="form-control" id="descripcion" name="descripcion">${producto.descripcion}</textarea>
    </div>
    <div class="mb-3">
      <label for="precio" class="form-label">Precio:</label>
      <input type="number" step="0.01" class="form-control" id="precio" name="precio" value="${producto.precio}" required min="0">
    </div>
    <div class="mb-3">
      <label for="anyos_garantia" class="form-label">Años de Garantía:</label>
      <input type="number" class="form-control" id="anyos_garantia" name="anyos_garantia" value="${producto.anyosGarantia}" min="0">
    </div>
    <div class="mb-3">
      <label for="unidades" class="form-label">Unidades:</label>
      <input type="number" class="form-control" id="unidades" name="unidades" value="${producto.unidades}" required min="0">
    </div>
    <div class="mb-3">
      <label for="proveedores_id_proveedores" class="form-label">Proveedor:</label>
      <select class="form-select" id="proveedores_id_proveedores" name="proveedores_id_proveedores" required>
        <option value="">Seleccionar Proveedor</option>
        <c:forEach var="proveedor" items="${listaProveedores}">
          <option value="${proveedor.idProveedores}" ${producto != null && producto.proveedor != null && producto.proveedor.idProveedores == proveedor.idProveedores ? 'selected' : ''}>${proveedor.nombre}</option>
        </c:forEach>
      </select>
    </div>
    <div class="mb-3">
      <label for="empresas_id_empresa" class="form-label">Empresa:</label>
      <select class="form-select" id="empresas_id_empresa" name="empresas_id_empresa" required>
        <option value="">Seleccionar Empresa</option>
        <c:forEach var="empresa" items="${listaEmpresas}">
          <option value="${empresa.idEmpresa}" ${producto != null && producto.empresa != null && producto.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
        </c:forEach>
      </select>
    </div>
    <button type="submit" class="btn btn-primary" id="guardarProductoBtn">Guardar</button>
  </form>
</div>
</body>
</html>