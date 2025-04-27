<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${venta != null ? 'Editar Venta' : 'Crear Venta'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var mensajeAlerta = "${mensajeAlerta}";
            if (mensajeAlerta !== "") {
                alert(mensajeAlerta);
            }

            var empresaSelect = document.getElementById("empresas_id_empresa");
            var guardarButton = document.getElementById("guardarVentaBtn");


            if (empresaSelect && empresaSelect.value === "") {
                guardarButton.disabled = true;
            }

            if (empresaSelect) {
                empresaSelect.addEventListener('change', function() {
                    if (this.value === "") {
                        guardarButton.disabled = true;
                    } else {
                        guardarButton.disabled = false;
                    }
                });
            }

            const agregarDetalleBtn = document.getElementById('agregarDetalle');
            const detallesVentaTableBody = document.querySelector('#detallesVentaTable tbody');
            const detalleFilaModelo = document.getElementById('detalleFilaModelo');

            if (agregarDetalleBtn) {
                agregarDetalleBtn.addEventListener('click', function() {
                    const nuevaFila = detalleFilaModelo.cloneNode(true);
                    nuevaFila.style.display = '';
                    detallesVentaTableBody.appendChild(nuevaFila);


                    const eliminarBtn = nuevaFila.querySelector('.eliminarFila');
                    if (eliminarBtn) {
                        eliminarBtn.addEventListener('click', function() {
                            nuevaFila.remove();
                        });
                    }
                });
            }


            if (detallesVentaTableBody) {
                detallesVentaTableBody.addEventListener('click', function(event) {
                    if (event.target.classList.contains('eliminarFila')) {
                        event.target.closest('tr').remove();
                    }
                });
            }
        });
    </script>
</head>
<body>
<div class="container mt-5">
    <h1>${venta != null ? 'Editar Venta' : 'Crear Venta'}</h1>
    <form action="ventas?action=save" method="post">
        <c:if test="${venta != null}">
            <input type="hidden" name="id" value="${venta.idVenta}">
        </c:if>
        <div class="mb-3">
            <label for="fecha_venta" class="form-label">Fecha Venta:</label>
            <input type="date" class="form-control" id="fecha_venta" name="fecha_venta" value="${venta.fechaVenta}">
        </div>
        <div class="mb-3">
            <label for="monto_total" class="form-label">Importe Total:</label>
            <input type="number" step="0.01" class="form-control" id="monto_total" name="monto_total" value="${venta.montoTotal}">
        </div>
        <div class="mb-3">
            <label for="empresas_id_empresa" class="form-label">Empresa:</label>
            <select class="form-select" id="empresas_id_empresa" name="empresas_id_empresa">
                <option value="">Seleccionar Empresa</option>
                <c:forEach var="empresa" items="${listaEmpresas}">
                    <option value="${empresa.idEmpresa}" ${venta.empresa != null && venta.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="clientes_dni" class="form-label">Cliente:</label>
            <select class="form-select" id="clientes_dni" name="clientes_dni">
                <option value="">Seleccionar Cliente</option>
                <c:forEach var="cliente" items="${listaClientes}">
                    <option value="${cliente.dni}" ${venta.cliente != null && venta.cliente.dni == cliente.dni ? 'selected' : ''}>${cliente.nombre} ${cliente.apellidos} (${cliente.dni})</option>
                </c:forEach>
            </select>
        </div>

        <h2>Detalles de la Venta</h2>
        <table class="table" id="detallesVentaTable">
            <thead>
            <tr>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Precio Unitario</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <tr id="detalleFilaModelo" style="display:none;">
                <td>
                    <select class="form-select productoSelect" name="producto_id">
                        <option value="">Seleccionar Producto</option>
                        <c:forEach var="producto" items="${listaProductos}">
                            <option value="${producto.idProducto}">${producto.nombre}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="number" class="form-control cantidadInput" name="cantidad" value="1" min="1"></td>
                <td><input type="number" step="0.01" class="form-control precioUnitarioInput" name="precio_unitario" value=""></td>
                <td><button type="button" class="btn btn-danger btn-sm eliminarFila">Eliminar</button></td>
            </tr>
            <tr id="primerDetalleFila">
                <td>
                    <select class="form-select productoSelect" name="producto_id">
                        <option value="">Seleccionar Producto</option>
                        <c:forEach var="producto" items="${listaProductos}">
                            <option value="${producto.idProducto}">${producto.nombre}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="number" class="form-control cantidadInput" name="cantidad" value="1" min="1"></td>
                <td><input type="number" step="0.01" class="form-control precioUnitarioInput" name="precio_unitario" value=""></td>
                <td></td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="4">
                    <button type="button" class="btn btn-success btn-sm" id="agregarDetalle">Agregar Producto</button>
                </td>
            </tr>
            </tfoot>
        </table>

        <button type="submit" class="btn btn-primary" id="guardarVentaBtn" ${not empty mensajeAlerta ? 'disabled' : ''}>Guardar</button>
        <a href="ventas?action=list" class="btn btn-secondary">Volver a la lista</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>