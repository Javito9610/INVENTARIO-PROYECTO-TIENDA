document.addEventListener('DOMContentLoaded', function() {

    var mensajeAlerta = "${mensajeAlerta}";
    if (mensajeAlerta && mensajeAlerta !== "" && mensajeAlerta !== "null" && typeof alert === 'function') {
        alert(mensajeAlerta);
    }


    var empresaSelectClienteForm = document.getElementById("empresas_id_empresa");
    var guardarButtonClienteForm = document.getElementById("guardarClienteBtn");

    if (empresaSelectClienteForm && guardarButtonClienteForm) {
        function checkClienteFormButtonState() {
            guardarButtonClienteForm.disabled = empresaSelectClienteForm.value === "" || empresaSelectClienteForm.value === "null";
        }

        empresaSelectClienteForm.addEventListener('change', checkClienteFormButtonState);
        checkClienteFormButtonState();
    }


    const agregarDetalleBtnCompra = document.getElementById("agregarDetalleCompra");
    const tablaDetallesBodyCompra = document.getElementById("detalles-compra-body");
    const templateFilaCompra = document.getElementById("detalleFilaModeloCompra");
    const montoTotalInputCompra = document.getElementById("monto_total");
    const compraFormElement = document.getElementById("compraForm");
    const guardarButtonCompraForm = document.getElementById("guardarCompraBtn");
    const empresaSelectCompraFormCompra = document.getElementById("empresas_id_empresa");

    if (agregarDetalleBtnCompra && tablaDetallesBodyCompra && templateFilaCompra) {
        function calcularTotalCompra() {
            let total = 0;
            tablaDetallesBodyCompra.querySelectorAll('tr:not(#detalleFilaModeloCompra)').forEach(fila => {
                const cantidadInput = fila.querySelector('.cantidad-input');
                const precioInput = fila.querySelector('.precio-unitario-input');
                const cantidad = parseFloat(cantidadInput ? cantidadInput.value : 0) || 0;
                const precio = parseFloat(precioInput ? precioInput.value : 0) || 0;
                total += cantidad * precio;
            });
            montoTotalInputCompra.value = total.toFixed(2);
            checkGuardarCompraButtonState();
        }

        function toggleNewProductFieldsCompra(fila) {
            const checkboxNuevoProducto = fila.querySelector('.nuevo-producto-checkbox');
            const existingFieldsDiv = fila.querySelector('.existing-product-fields');
            const newFieldsDiv = fila.querySelector('.new-product-fields');
            if (checkboxNuevoProducto && existingFieldsDiv && newFieldsDiv) {
                if (checkboxNuevoProducto.checked) {
                    existingFieldsDiv.classList.add('hidden-fields');
                    newFieldsDiv.classList.remove('hidden-fields');
                } else {
                    existingFieldsDiv.classList.remove('hidden-fields');
                    newFieldsDiv.classList.add('hidden-fields');
                }
            }
        }

        function setupRowListenersCompra(fila) {
            const eliminarBtn = fila.querySelector('.eliminar-detalle');
            const cantidadInput = fila.querySelector('.cantidad-input');
            const precioInput = fila.querySelector('.precio-unitario-input');
            const productoSelect = fila.querySelector('.producto-select');
            const checkboxNuevoProducto = fila.querySelector('.nuevo-producto-checkbox');

            if (checkboxNuevoProducto) {
                checkboxNuevoProducto.addEventListener('change', function() {
                    toggleNewProductFieldsCompra(fila);
                    calcularTotalCompra();
                });
                toggleNewProductFieldsCompra(fila);
            } else {
                const detalleTipoInput = fila.querySelector('.detalle-tipo-input');
                const existingFieldsDiv = fila.querySelector('.existing-product-fields');
                const newFieldsDiv = fila.querySelector('.new-product-fields');
                if(detalleTipoInput && detalleTipoInput.value === 'new') {
                    if(existingFieldsDiv) existingFieldsDiv.classList.add('hidden-fields');
                    if(newFieldsDiv) newFieldsDiv.classList.remove('hidden-fields');
                } else {
                    if(existingFieldsDiv) existingFieldsDiv.classList.remove('hidden-fields');
                    if(newFieldsDiv) newFieldsDiv.classList.add('hidden-fields');
                }
            }
            if (eliminarBtn) {
                eliminarBtn.addEventListener('click', function() {
                    const detalleFilas = tablaDetallesBodyCompra.querySelectorAll('tr:not(#detalleFilaModeloCompra)');
                    if (detalleFilas.length > 1) {
                        fila.remove();
                        calcularTotalCompra();
                    } else {
                        alert("Debe haber al menos un producto en la compra.");
                    }
                });
            }
            if (cantidadInput) cantidadInput.addEventListener('input', calcularTotalCompra);
            if (precioInput) precioInput.addEventListener('input', calcularTotalCompra);
            if (productoSelect) productoSelect.addEventListener('change', calcularTotalCompra);
            if (cantidadInput && cantidadInput.min === "") cantidadInput.min = "1";
            if (precioInput && precioInput.min === "") precioInput.min = "0";
        }

        agregarDetalleBtnCompra.addEventListener('click', function() {
            const nuevaFila = templateFilaCompra.cloneNode(true);
            nuevaFila.removeAttribute('id');
            nuevaFila.style.display = '';
            const productoSelectOriginal = templateFilaCompra.querySelector('.producto-select');
            const nuevoProductoSelect = nuevaFila.querySelector('.producto-select');
            if (productoSelectOriginal && nuevoProductoSelect) {
                nuevoProductoSelect.innerHTML = '';
                productoSelectOriginal.querySelectorAll('option').forEach(option => {
                    nuevoProductoSelect.appendChild(option.cloneNode(true));
                });
                nuevoProductoSelect.value = "";
            }
            tablaDetallesBodyCompra.appendChild(nuevaFila);
            setupRowListenersCompra(nuevaFila);
            if (nuevoProductoSelect) nuevoProductoSelect.setAttribute('required', 'required');
            calcularTotalCompra();
        });

        const initialDetalleFilasCompra = tablaDetallesBodyCompra.querySelectorAll('tr:not(#detalleFilaModeloCompra)');
        initialDetalleFilasCompra.forEach(fila => {
            setupRowListenersCompra(fila);
        });

        calcularTotalCompra();

        compraFormElement.addEventListener('submit', function(event) {
            const detalleFilas = tablaDetallesBodyCompra.querySelectorAll('tr:not(#detalleFilaModeloCompra)');
            let formValid = true;
            if (detalleFilas.length === 0) {
                alert("Debe agregar al menos un detalle de producto a la compra.");
                formValid = false;
                event.preventDefault();
                return;
            }
            detalleFilas.forEach(fila => {
                if (!formValid) return;
                const checkbox = fila.querySelector('.nuevo-producto-checkbox');
                const selectProducto = fila.querySelector('.producto-select');
                const inputNuevoNombre = fila.querySelector('.nuevo-producto-nombre');
                const inputCantidad = fila.querySelector('.cantidad-input');
                const inputPrecio = fila.querySelector('.precio-unitario-input');
                const cantidad = parseFloat(inputCantidad ? inputCantidad.value : NaN);
                const precio = parseFloat(inputPrecio ? inputPrecio.value : NaN);
                if (cantidad <= 0 || precio < 0 || isNaN(cantidad) || isNaN(precio) ) {
                    alert("Cantidad y Precio Unitario deben ser valores numéricos válidos (cantidad > 0, precio >= 0) para todos los productos.");
                    formValid = false;
                    event.preventDefault();
                    if(fila) fila.style.border = '1px solid red';
                    return;
                } else {
                    if(fila) fila.style.border = '';
                }
                if (checkbox && checkbox.checked) {
                    if (!inputNuevoNombre || !inputNuevoNombre.value.trim()) {
                        alert("Debe especificar el nombre para el nuevo producto.");
                        formValid = false;
                        event.preventDefault();
                        if(inputNuevoNombre) inputNuevoNombre.focus();
                        if(fila) fila.style.border = '1px solid red';
                        return;
                    }
                    if(fila) fila.style.border = '';
                } else {
                    if (!selectProducto || !selectProducto.value) {
                        alert("Debe seleccionar un producto existente o marcar 'Crear Nuevo Producto'.");
                        formValid = false;
                        event.preventDefault();
                        if(selectProducto) selectProducto.focus();
                        if(fila) fila.style.border = '1px solid red';
                        return;
                    }
                    if(fila) fila.style.border = '';
                }
            });
            if (!formValid) return;
            const proveedorSelect = document.getElementById('proveedores_id_proveedores');
            const empresaSelect = document.getElementById('empresas_id_empresa');
            const fechaInput = document.getElementById('fecha_compra');
            if (!proveedorSelect || !proveedorSelect.value) {
                alert("Debe seleccionar un proveedor.");
                formValid = false;
                event.preventDefault();
                if(proveedorSelect) proveedorSelect.focus();
                return;
            }
            if (!empresaSelect || !empresaSelect.value) {
                alert("Debe seleccionar una empresa.");
                formValid = false;
                event.preventDefault();
                if(empresaSelect) empresaSelect.focus();
                return;
            }
            if (!fechaInput || !fechaInput.value) {
                alert("Debe seleccionar una fecha para la compra.");
                formValid = false;
                event.preventDefault();
                if(fechaInput) fechaInput.focus();
                return;
            }
        });

        function checkGuardarCompraButtonState() {
            const montoTotal = parseFloat(montoTotalInputCompra.value) || 0;
            const hasDetails = tablaDetallesBodyCompra.querySelectorAll('tr:not(#detalleFilaModeloCompra)').length > 0;
            guardarButtonCompraForm.disabled = (mensajeAlerta && mensajeAlerta !== "" && mensajeAlerta !== "null") ||
                (empresaSelectCompraFormCompra && (empresaSelectCompraFormCompra.value === "" || empresaSelectCompraFormCompra.value === "null")) ||
                montoTotal <= 0 || !hasDetails;
        }

        if (empresaSelectCompraFormCompra) {
            empresaSelectCompraFormCompra.addEventListener('change', checkGuardarCompraButtonState);
        }
        checkGuardarCompraButtonState();
    }


    var guardarButtonEnvioForm = document.getElementById("guardarEnvioBtn");
    var empresaSelectEnvioForm = document.getElementById("empresas_id_empresa");

    if (guardarButtonEnvioForm) {
        function checkEnvioFormButtonState() {
            guardarButtonEnvioForm.disabled = empresaSelectEnvioForm && (empresaSelectEnvioForm.value === "" || empresaSelectEnvioForm.value === "null");
        }

        if (empresaSelectEnvioForm) {
            empresaSelectEnvioForm.addEventListener('change', checkEnvioFormButtonState);
        }
        checkEnvioFormButtonState();
    }


    var searchInputClientes = document.getElementById('searchInput');
    var clientsTableBody = document.getElementById('clientsTableBody');
    var clearSearchBtnClientes = document.getElementById('clearSearchBtn');
    var noResultsRowClientes = document.getElementById('noResultsRow');
    var initiallyEmptyRowClientes = document.getElementById('initiallyEmptyRow');

    if (searchInputClientes && clientsTableBody) {
        var rowsClientes = clientsTableBody.getElementsByTagName('tr');

        function filterTableClientes() {
            const filter = searchInputClientes.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowClientes) initiallyEmptyRowClientes.style.display = 'none';

            for (let i = 0; i < rowsClientes.length; i++) {
                if (rowsClientes[i].id === 'noResultsRow' || rowsClientes[i].id === 'initiallyEmptyRow') continue;
                const dni = rowsClientes[i].cells[0].textContent.toLowerCase();
                const nombre = rowsClientes[i].cells[1].textContent.toLowerCase();
                const apellidos = rowsClientes[i].cells[2].textContent.toLowerCase();
                const telefono = rowsClientes[i].cells[3].textContent.toLowerCase();
                const correo = rowsClientes[i].cells[4].textContent.toLowerCase();
                const ciudad = rowsClientes[i].cells[5].textContent.toLowerCase();

                const rowText = `${dni} ${nombre} ${apellidos} ${telefono} ${correo} ${ciudad}`;
                const matches = rowText.includes(filter);
                rowsClientes[i].style.display = matches ? '' : 'none';
                anyRowVisible = anyRowVisible || matches;
            }

            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowClientes) noResultsRowClientes.style.display = 'table-row';
            } else {
                if(noResultsRowClientes) noResultsRowClientes.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowClientes) {
                    if(initiallyEmptyRowClientes) initiallyEmptyRowClientes.style.display = 'table-row';
                }
            }

            if (filter.length > 0) {
                if(clearSearchBtnClientes) clearSearchBtnClientes.style.display = 'inline-block';
            } else {
                if(clearSearchBtnClientes) clearSearchBtnClientes.style.display = 'none';
            }
        }

        searchInputClientes.addEventListener('input', filterTableClientes);

        if(clearSearchBtnClientes) {
            clearSearchBtnClientes.addEventListener('click', function() {
                searchInputClientes.value = '';
                filterTableClientes();
                searchInputClientes.focus();
            });
        }

        filterTableClientes();
    }


    var searchInputComprasElem = document.getElementById('searchInputCompras');
    var comprasTableBodyElem = document.getElementById('comprasTableBody');
    var clearSearchBtnComprasElem = documentgetElementById('clearSearchBtnCompras');
    var noResultsRowComprasElem = document.getElementById('noResultsRowCompras');
    var initiallyEmptyRowComprasElem = document.getElementById('initiallyEmptyRowCompras');

    if (searchInputComprasElem && comprasTableBodyElem) {
        var rowsCompras = comprasTableBodyElem.getElementsByTagName('tr');

        function filterTableComprasFunc() {
            const filter = searchInputComprasElem.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowComprasElem) initiallyEmptyRowComprasElem.style.display = 'none';

            for (let i = 0; i < rowsCompras.length; i++) {
                if (rowsCompras[i].id === 'noResultsRowCompras' || rowsCompras[i].id === 'initiallyEmptyRowCompras') continue;
                const idCompra = rowsCompras[i].cells[0].textContent.toLowerCase();
                const fechaCompra = rowsCompras[i].cells[1].textContent.toLowerCase();
                const montoTotal = rowsCompras[i].cells[2].textContent.toLowerCase();
                const proveedor = rowsCompras[i].cells[3].textContent.toLowerCase();
                const empresa = rowsCompras[i].cells[4].textContent.toLowerCase();

                const rowText = `${idCompra} ${fechaCompra} ${montoTotal} ${proveedor} ${empresa}`;
                const matches = rowText.includes(filter);
                rowsCompras[i].style.display = matches ? '' : 'none';
                anyRowVisible = anyRowVisible || matches;
            }

            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowComprasElem) noResultsRowComprasElem.style.display = 'table-row';
            } else {
                if(noResultsRowComprasElem) noResultsRowComprasElem.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowComprasElem) {
                    if(initiallyEmptyRowComprasElem) initiallyEmptyRowComprasElem.style.display = 'table-row';
                }
            }

            if (filter.length > 0) {
                if(clearSearchBtnComprasElem) clearSearchBtnComprasElem.style.display = 'inline-block';
            } else {
                if(clearSearchBtnComprasElem) clearSearchBtnComprasElem.style.display = 'none';
            }
        }

        searchInputComprasElem.addEventListener('input', filterTableComprasFunc);

        if(clearSearchBtnComprasElem) {
            clearSearchBtnComprasElem.addEventListener('click', function() {
                searchInputComprasElem.value = '';
                filterTableComprasFunc();
                searchInputComprasElem.focus();
            });
        }

        filterTableComprasFunc();
    }


    var searchInputEmpresasElem = document.getElementById('searchInputEmpresas');
    var empresasTableBodyElem = document.getElementById('empresasTableBody');
    var clearSearchBtnEmpresasElem = document.getElementById('clearSearchBtnEmpresas');
    var noResultsRowEmpresasElem = document.getElementById('noResultsRowEmpresas');
    var initiallyEmptyRowEmpresasElem = document.getElementById('initiallyEmptyRowEmpresas');

    if (searchInputEmpresasElem && empresasTableBodyElem) {
        var rowsEmpresas = empresasTableBodyElem.getElementsByTagName('tr');

        function filterTableEmpresasFunc() {
            const filter = searchInputEmpresasElem.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowEmpresasElem) initiallyEmptyRowEmpresasElem.style.display = 'none';

            for (let i = 0; i < rowsEmpresas.length; i++) {
                if (rowsEmpresas[i].id === 'noResultsRowEmpresas' || rowsEmpresas[i].id === 'initiallyEmptyRowEmpresas') continue;
                const idEmpresa = rowsEmpresas[i].cells[0].textContent.toLowerCase();
                const nombre = rowsEmpresas[i].cells[1].textContent.toLowerCase();
                const rfcNit = rowsEmpresas[i].cells[2].textContent.toLowerCase();
                const direccion = rowsEmpresas[i].cells[3].textContent.toLowerCase();
                const ciudad = rowsEmpresas[i].cells[4].textContent.toLowerCase();
                const pais = rowsEmpresas[i].cells[5].textContent.toLowerCase();

                const rowText = `${idEmpresa} ${nombre} ${rfcNit} ${direccion} ${ciudad} ${pais}`;
                const matches = rowText.includes(filter);
                rowsEmpresas[i].style.display = matches ? '' : 'none';
                anyRowVisible = anyRowVisible || matches;
            }

            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowEmpresasElem) noResultsRowEmpresasElem.style.display = 'table-row';
            } else {
                if(noResultsRowEmpresasElem) noResultsRowEmpresasElem.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowEmpresasElem) {
                    if(initiallyEmptyRowEmpresasElem) initiallyEmptyRowEmpresasElem.style.display = 'table-row';
                }
            }

            if (filter.length > 0) {
                if(clearSearchBtnEmpresasElem) clearSearchBtnEmpresasElem.style.display = 'inline-block';
            } else {
                if(clearSearchBtnEmpresasElem) clearSearchBtnEmpresasElem.style.display = 'none';
            }
        }

        searchInputEmpresasElem.addEventListener('input', filterTableEmpresasFunc);

        if(clearSearchBtnEmpresasElem) {
            clearSearchBtnEmpresasElem.addEventListener('click', function() {
                searchInputEmpresasElem.value = '';
                filterTableEmpresasFunc();
                searchInputEmpresasElem.focus();
            });
        }

        filterTableEmpresasFunc();
    }


    var empresaSelectProductoForm = document.getElementById("empresas_id_empresa");
    var proveedorSelectProductoForm = document.getElementById("proveedores_id_proveedores");
    var guardarButtonProductoForm = document.getElementById("guardarProductoBtn");

    if (guardarButtonProductoForm) {
        function checkProductoFormButtonState() {
            const empresaSeleccionada = empresaSelectProductoForm ? empresaSelectProductoForm.value : "";
            const proveedorSeleccionado = proveedorSelectProductoForm ? proveedorSelectProductoForm.value : "";
            guardarButtonProductoForm.disabled = empresaSeleccionada === "" || proveedorSeleccionado === "";
        }

        if (empresaSelectProductoForm) {
            empresaSelectProductoForm.addEventListener('change', checkProductoFormButtonState);
        }
        if (proveedorSelectProductoForm) {
            proveedorSelectProductoForm.addEventListener('change', checkProductoFormButtonState);
        }
        checkProductoFormButtonState();
    }


    var empresaSelectProveedorForm = document.getElementById("empresas_id_empresa");
    var guardarButtonProveedorForm = document.getElementById("guardarProveedorBtn");

    if (guardarButtonProveedorForm) {
        function checkProveedorFormButtonState() {
            guardarButtonProveedorForm.disabled = empresaSelectProveedorForm && (empresaSelectProveedorForm.value === "" || empresaSelectProveedorForm.value === "null");
        }

        if (empresaSelectProveedorForm) {
            empresaSelectProveedorForm.addEventListener('change', checkProveedorFormButtonState);
        }
        checkProveedorFormButtonState();
    }


    var empresaSelectUsuarioForm = document.getElementById("empresaId");
    var guardarButtonUsuarioForm = document.getElementById("guardarUsuarioBtn");

    if (guardarButtonUsuarioForm) {
        function checkUsuarioFormButtonState() {
            guardarButtonUsuarioForm.disabled = empresaSelectUsuarioForm && (empresaSelectUsuarioForm.value === "" || empresaSelectUsuarioForm.value === "null");
        }

        if (empresaSelectUsuarioForm) {
            empresaSelectUsuarioForm.addEventListener('change', checkUsuarioFormButtonState);
        }
        checkUsuarioFormButtonState();
    }


    var empresaSelectVentaForm = document.getElementById("empresas_id_empresa");
    var clienteSelectVentaForm = document.getElementById("clientes_dni");
    var guardarButtonVentaForm = document.getElementById("guardarVentaBtn");
    const agregarDetalleBtnVenta = document.getElementById('agregarDetalle');
    const detallesVentaTableBody = document.querySelector('#detallesVentaTable tbody');
    const detalleFilaModelo = document.getElementById('detalleFilaModelo');

    function checkVentaFormButtonState() {
        const empresaSeleccionadaVenta = empresaSelectVentaForm ? empresaSelectVentaForm.value : "";
        const clienteSeleccionadoVenta = clienteSelectVentaForm ? clienteSelectVentaForm.value : "";
        const detallesCount = detallesVentaTableBody ? detallesVentaTableBody.querySelectorAll('tr:not([style*="display: none"])').length - (detalleFilaModelo && detalleFilaModelo.parentNode === detallesVentaTableBody ? 1 : 0) : 0;
        guardarButtonVentaForm.disabled = empresaSeleccionadaVenta === "" || clienteSeleccionadoVenta === "" || detallesCount === 0;
    }

    if (empresaSelectVentaForm) {
        empresaSelectVentaForm.addEventListener('change', checkVentaFormButtonState);
    }
    if (clienteSelectVentaForm) {
        clienteSelectVentaForm.addEventListener('change', checkVentaFormButtonState);
    }

    if (agregarDetalleBtnVenta) {
        agregarDetalleBtnVenta.addEventListener('click', function() {
            const nuevaFila = detalleFilaModelo.cloneNode(true);
            nuevaFila.style.display = '';
            detallesVentaTableBody.appendChild(nuevaFila);
            attachRemoveButtonListener(nuevaFila);
            checkVentaFormButtonState();
        });
    }

    function attachRemoveButtonListener(row) {
        const eliminarBtn = row.querySelector('.eliminarFila');
        if (eliminarBtn) {
            eliminarBtn.addEventListener('click', function() {
                row.remove();
                checkVentaFormButtonState();
            });
        }
    }


    const initialRow = document.querySelector('#primerDetalleFila');
    if (initialRow && initialRow.id !== 'detalleFilaModelo') {
        attachRemoveButtonListener(initialRow);
    }


    if (detalleFilaModelo) {
        detalleFilaModelo.style.display = 'none';
    }

    checkVentaFormButtonState();


    var searchInputVentas = document.getElementById('searchInputVentas');
    var ventasTableBody = document.getElementById('ventasTableBody');
    var clearSearchBtnVentas = document.getElementById('clearSearchBtnVentas');
    var noResultsRowVentas = document.getElementById('noResultsRowVentas');
    var initiallyEmptyRowVentas = document.getElementById('initiallyEmptyRowVentas');

    if (searchInputVentas && ventasTableBody) {
        var rowsVentas = ventasTableBody.getElementsByTagName('tr');

        function filterTableVentas() {
            const filter = searchInputVentas.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowVentas) initiallyEmptyRowVentas.style.display = 'none';

            for (let i = 0; i < rowsVentas.length; i++) {
                if (rowsVentas[i].id === 'noResultsRowVentas' || rowsVentas[i].id === 'initiallyEmptyRowVentas') continue;
                const idVenta = rowsVentas[i].cells[0].textContent.toLowerCase();
                const fechaVenta = rowsVentas[i].cells[1].textContent.toLowerCase();
                const montoTotal = rowsVentas[i].cells[2].textContent.toLowerCase();
                const empresa = rowsVentas[i].cells[3].textContent.toLowerCase();
                const cliente = rowsVentas[i].cells[4].textContent.toLowerCase();

                const rowText = `${idVenta} ${fechaVenta} ${montoTotal} ${empresa} ${cliente}`;
                const matches = rowText.includes(filter);
                rowsVentas[i].style.display = matches ? '' : 'none';
                anyRowVisible = anyRowVisible || matches;
            }

            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowVentas) noResultsRowVentas.style.display = 'table-row';
            } else {
                if(noResultsRowVentas) noResultsRowVentas.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowVentas) {
                    if(initiallyEmptyRowVentas) initiallyEmptyRowVentas.style.display = 'table-row';
                }
            }

            if (filter.length > 0) {
                if(clearSearchBtnVentas) clearSearchBtnVentas.style.display = 'inline-block';
            } else {
                if(clearSearchBtnVentas) clearSearchBtnVentas.style.display = 'none';
            }
        }

        searchInputVentas.addEventListener('input', filterTableVentas);

        if(clearSearchBtnVentas) {
            clearSearchBtnVentas.addEventListener('click', function() {
                searchInputVentas.value = '';
                filterTableVentas();
                searchInputVentas.focus();
            });
        }

        filterTableVentas();
    }
});