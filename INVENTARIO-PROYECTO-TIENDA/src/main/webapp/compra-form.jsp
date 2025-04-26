<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${compra != null ? 'Editar Compra' : 'Crear Compra'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/compras?action=list" class="btn btn-secondary">Volver a la lista de compras</a>
        <a href="${pageContext.request.contextPath}/productos?action=list" class="btn btn-secondary">Ver Productos</a>
    </div>
    <h1>${compra != null ? 'Editar Compra' : 'Crear Compra'}</h1>
    <form action="compras?action=save" method="post" id="compraForm">
        <c:if test="${compra != null}">
            <input type="hidden" name="id" value="${compra.idCompra}">
        </c:if>
        <div class="mb-3">
            <label for="fecha_compra" class="form-label">Fecha Compra:</label>
            <input type="date" class="form-control" id="fecha_compra" name="fecha_compra" value="${compra.fechaCompra}" required>
        </div>
        <div class="mb-3">
            <label for="monto_total" class="form-label">Importe Total:</label>
            <input type="number" step="0.01" class="form-control" id="monto_total" name="monto_total" value="${compra.montoTotal}" readonly>
            <small class="form-text text-muted">Este importe se calculará automáticamente al añadir los productos.</small>
        </div>
        <div class="mb-3">
            <label for="proveedores_id_proveedores" class="form-label">Proveedor:</label>
            <select class="form-select" id="proveedores_id_proveedores" name="proveedores_id_proveedores" required>
                <option value="">Seleccionar Proveedor</option>
                <c:forEach var="proveedor" items="${listaProveedores}">
                    <option value="${proveedor.idProveedores}" ${compra != null && compra.proveedor != null && compra.proveedor.idProveedores == proveedor.idProveedores ? 'selected' : ''}>${proveedor.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="empresas_id_empresa" class="form-label">Empresa:</label>
            <select class="form-select" id="empresas_id_empresa" name="empresas_id_empresa" required>
                <option value="">Seleccionar Empresa</option>
                <c:forEach var="empresa" items="${listaEmpresas}">
                    <option value="${empresa.idEmpresa}" ${compra != null && compra.empresa != null && compra.empresa.idEmpresa == empresa.idEmpresa ? 'selected' : ''}>${empresa.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <hr class="my-4">
        <h3>Detalles de la Compra</h3>
        <table class="table table-bordered" id="tabla-detalles-compra">
            <thead>
            <tr>
                <th style="width: 40%;">Producto</th>
                <th style="width: 15%;">Cantidad</th>
                <th style="width: 20%;">Precio Unitario</th>
                <th style="width: 10%;">Acciones</th>
            </tr>
            </thead>
            <tbody id="detalles-compra-body">
            <tr id="detalleFilaModeloCompra">
                <td>
                    <input type="hidden" class="detalle-tipo-input" name="detalle_tipo[]" value="existing">
                    <div class="form-check mb-2">
                        <input type="checkbox" class="form-check-input nuevo-producto-checkbox">
                        <label class="form-check-label">Crear Nuevo Producto</label>
                    </div>
                    <div class="existing-product-fields">
                        <select class="form-select producto-select form-select-sm" name="productos_id_producto[]">
                            <option value="">Seleccionar Producto Existente</option>
                            <c:forEach var="producto" items="${listaProductos}">
                                <option value="${producto.idProducto}">${producto.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="new-product-fields hidden-fields">
                        <input type="text" class="form-control form-control-sm mb-1 nuevo-producto-nombre" name="nuevo_producto_nombre[]" placeholder="Nombre del Nuevo Producto">
                        <textarea class="form-control form-control-sm nuevo-producto-descripcion" name="nuevo_producto_descripcion[]" placeholder="Descripción (Opcional)"></textarea>
                    </div>
                </td>
                <td><input type="number" class="form-control cantidad-input form-control-sm" name="cantidad[]" value="1" min="1" required></td>
                <td><input type="number" step="0.01" class="form-control precio-unitario-input form-control-sm" name="precio_unidad[]" value="0.00" min="0" required></td>
                <td><button type="button" class="btn btn-danger btn-sm eliminar-detalle">Eliminar</button></td>
            </tr>
            </tbody>
        </table>
        <button type="button" class="btn btn-success" id="agregarDetalleCompra">Agregar Producto</button>
        <button type="submit" class="btn btn-primary" id="guardarCompraBtn">Guardar</button>
    </form>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const agregarBtn = document.getElementById("agregarDetalleCompra");
        const tabla = document.getElementById("detalles-compra-body");
        const modelo = document.getElementById("detalleFilaModeloCompra");

        function calcularMontoTotal() {
            let total = 0;
            document.querySelectorAll("#detalles-compra-body tr").forEach(row => {
                const cantidad = parseFloat(row.querySelector(".cantidad-input")?.value) || 0;
                const precio = parseFloat(row.querySelector(".precio-unitario-input")?.value) || 0;
                total += cantidad * precio;
            });
            document.getElementById("monto_total").value = total.toFixed(2);
        }

        agregarBtn.addEventListener("click", function () {
            const nueva = modelo.cloneNode(true);
            nueva.removeAttribute("id");
            nueva.querySelectorAll("input, select, textarea").forEach(el => el.value = "");
            tabla.appendChild(nueva);
            calcularMontoTotal();
        });

        tabla.addEventListener("click", function (e) {
            if (e.target.classList.contains("eliminar-detalle")) {
                e.target.closest("tr").remove();
                calcularMontoTotal();
            }
        });

        tabla.addEventListener("input", function (e) {
            if (
                e.target.classList.contains("cantidad-input") ||
                e.target.classList.contains("precio-unitario-input")
            ) {
                calcularMontoTotal();
            }
        });

        calcularMontoTotal();
    });
</script>
</body>
</html>
