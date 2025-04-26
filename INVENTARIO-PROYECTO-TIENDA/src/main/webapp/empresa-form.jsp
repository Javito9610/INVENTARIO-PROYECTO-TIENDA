<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>${empresa != null ? 'Editar Empresa' : 'Crear Empresa'}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/js/script.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <div class="mb-3">
    <a href="${pageContext.request.contextPath}/empresas?action=list" class="btn btn-secondary">Volver a la lista</a>
  </div>
  <h1>${empresa != null ? 'Editar Empresa' : 'Crear Empresa'}</h1>
  <form action="empresas?action=save" method="post">
    <c:if test="${empresa != null}">
      <input type="hidden" name="id" value="${empresa.idEmpresa}">
    </c:if>
    <div class="mb-3">
      <label for="nombre" class="form-label">Nombre:</label>
      <input type="text" class="form-control" id="nombre" name="nombre" value="${empresa.nombre}" required>
    </div>
    <div class="mb-3">
      <label for="direccion" class="form-label">Dirección:</label>
      <input type="text" class="form-control" id="direccion" name="direccion" value="${empresa.direccion}">
    </div>
    <div class="mb-3">
      <label for="telefono" class="form-label">Teléfono:</label>
      <input type="text" class="form-control" id="telefono" name="telefono" value="${empresa.telefono}">
    </div>
    <div class="mb-3">
      <label for="correo_electronico" class="form-label">Correo Electrónico:</label>
      <input type="email" class="form-control" id="correo_electronico" name="correo_electronico" value="${empresa.correoElectronico}" required>
    </div>
    <div class="mb-3">
      <label for="descripcion" class="form-label">Descripción:</label>
      <textarea class="form-control" id="descripcion" name="descripcion">${empresa.descripcion}</textarea>
    </div>
    <div class="mb-3">
      <label for="sitio_web" class="form-label">Sitio Web:</label>
      <input type="url" class="form-control" id="sitio_web" name="sitio_web" value="${empresa.sitioWeb}">
    </div>
    <button type="submit" class="btn btn-primary">Guardar</button>
  </form>
</div>
</body>
</html>