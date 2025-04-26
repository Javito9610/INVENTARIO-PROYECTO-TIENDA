<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${envio != null ? 'Editar Envío' : 'Crear Envío'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/envios?action=list" class="btn btn-secondary">Volver a la lista</a>
    </div>
    <h1>${envio != null ? 'Editar Envío' : 'Crear Envío'}</h1>
    <form action="envios?action=save" method="post">
        <c:if test="${envio != null}">
            <input type="hidden" name="id" value="${envio.idEnvio}">
        </c:if>
        <div class="mb-3">
            <label for="fecha_envio" class="form-label">Fecha Envío:</label>
            <input type="date" class="form-control" id="fecha_envio" name="fecha_envio" value="${envio.fechaEnvio}" required>
        </div>
        <div class="mb-3">
            <label for="fecha_entrega" class="form-label">Fecha Entrega:</label>
            <input type="date" class="form-control" id="fecha_entrega" name="fecha_entrega" value="${envio.fechaEntrega}">
        </div>
        <div class="mb-3">
            <label for="direccion_envio" class="form-label">Dirección Envío:</label>
            <input type="text" class="form-control" id="direccion_envio" name="direccion_envio" value="${envio.direccionEnvio}" required>
        </div>
        <div class="mb-3">
            <label for="ciudad_envio" class="form-label">Ciudad Envío:</label>
            <input type="text" class="form-control" id="ciudad_envio" name="ciudad_envio" value="${envio.ciudadEnvio}">
        </div>
        <div class="mb-3">
            <label for="codigo_postal_envio" class="form-label">Código Postal Envío:</label>
            <input type="text" class="form-control" id="codigo_postal_envio" name="codigo_postal_envio" value="${envio.codigoPostalEnvio}">
        </div>
        <div class="mb-3">
            <label for="estado_envio" class="form-label">Estado Envío:</label>
            <input type="text" class="form-control" id="estado_envio" name="estado_envio" value="${envio.estadoEnvio}" required>
        </div>
        <div class="mb-3">
            <label for="detalles_venta_id_detalles_venta" class="form-label">Detalle de Venta:</label>
            <select class="form-select" id="detalles_venta_id_detalles_venta" name="detalles_venta_id_detalles_venta" required>
                <option value="">Seleccionar Detalle de Venta</option>
                <c:forEach var="detalleVenta" items="${listaDetallesVenta}">
                    <option value="${detalleVenta.idDetallesVenta}" ${envio != null && envio.detalleVenta != null && envio.detalleVenta.idDetallesVenta == detalleVenta.idDetallesVenta ? 'selected' : ''}>
                        ID: ${detalleVenta.idDetallesVenta} - Venta ID: ${detalleVenta.venta.idVenta} - Producto: ${detalleVenta.producto.nombre} - Cantidad: ${detalleVenta.cantidad}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="empresas_id_empresa" class="form-label">Empresa:</label>
            <select class="form-select" id="empresas_id_empresa" name="empresas_id_empresa" required>
                <option value="">Seleccionar Empresa</option>
                <c:forEach var="empresa" items="${listaEmpresas}">
                    <option value="${empresa.idEmpresa}" ${envio != null && envio.empresa != null && envio.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" id="guardarEnvioBtn">Guardar</button>
    </form>
</div>
</body>
</html>