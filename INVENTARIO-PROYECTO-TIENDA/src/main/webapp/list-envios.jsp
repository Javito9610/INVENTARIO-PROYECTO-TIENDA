<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Lista de Envíos</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <div class="mb-3">
    <a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-secondary">Volver al Menú Principal</a>
  </div>
  <h1>Lista de Envíos</h1>
  <div class="mb-3 d-flex align-items-center">
    <input type="text" id="searchInputEnvios" class="form-control me-2" placeholder="Buscar por ID, Dirección, Estado, etc.">
    <button type="button" id="clearSearchBtnEnvios" class="btn btn-secondary ms-2" style="display: none;">Limpiar</button>
  </div>
  <p><a href="envios?action=new" class="btn btn-primary">Crear Nuevo Envío</a></p>
  <table class="table table-striped table-hover">
    <thead>
    <tr>
      <th>ID</th>
      <th>Fecha Envío</th>
      <th>Fecha Entrega</th>
      <th>Dirección Envío</th>
      <th>Estado Envío</th>
      <th>Detalle Venta</th>
      <th>Empresa</th>
      <th>Acciones</th>
    </tr>
    </thead>
    <tbody id="enviosTableBody">
    <c:forEach var="envio" items="${listaEnvios}">
      <tr>
        <td>${envio.idEnvio}</td>
        <td>${envio.fechaEnvio}</td>
        <td>${envio.fechaEntrega}</td>
        <td>${envio.direccionEnvio}</td>
        <td>${envio.estadoEnvio}</td>
        <td>${envio.detalleVenta != null ? envio.detalleVenta.idDetallesVenta : 'N/A'}</td>
        <td>${envio.empresa != null ? envio.empresa.nombre : 'N/A'}</td>
        <td>
          <a href="envios?action=edit&id=${envio.idEnvio}" class="btn btn-sm btn-warning">Editar</a>
          <a href="envios?action=delete&id=${envio.idEnvio}" class="btn btn-sm btn-danger">Eliminar</a>
        </td>
      </tr>
    </c:forEach>
    <tr id="noResultsRowEnvios" style="display: none;">
      <td colspan="8" class="text-center">
        No se encontraron resultados.
      </td>
    </tr>
    <c:if test="${empty listaEnvios}">
      <tr id="initiallyEmptyRowEnvios">
        <td colspan="8" class="text-center">
          No hay envíos registrados.
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