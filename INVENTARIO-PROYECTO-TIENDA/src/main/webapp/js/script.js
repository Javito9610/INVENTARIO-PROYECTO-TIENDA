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
    var empresaSelectProductoForm = document.getElementById("empresas_id_empresa");
    var guardarButtonProductoForm = document.getElementById("guardarProductoBtn");
    if (empresaSelectProductoForm && guardarButtonProductoForm) {
        function checkProductoFormButtonState() {
            guardarButtonProductoForm.disabled = empresaSelectProductoForm.value === "" || empresaSelectProductoForm.value === "null";
        }
        empresaSelectProductoForm.addEventListener('change', checkProductoFormButtonState);
        checkProductoFormButtonState();
    }
    var empresaSelectProveedorForm = document.getElementById("empresas_id_empresa");
    var guardarButtonProveedorForm = document.getElementById("guardarProveedorBtn");
    if (empresaSelectProveedorForm && guardarButtonProveedorForm) {
        function checkProveedorFormButtonState() {
            guardarButtonProveedorForm.disabled = empresaSelectProveedorForm.value === "" || empresaSelectProveedorForm.value === "null";
        }
        empresaSelectProveedorForm.addEventListener('change', checkProveedorFormButtonState);
        checkProveedorFormButtonState();
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
                const rowText = rowsClientes[i].textContent.toLowerCase();
                rowsClientes[i].style.display = rowText.includes(filter) ? '' : 'none';
                anyRowVisible = anyRowVisible || rowText.includes(filter);
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
    var searchInputCompras = document.getElementById('searchInputCompras');
    var comprasTableBody = document.getElementById('comprasTableBody');
    var clearSearchBtnCompras = document.getElementById('clearSearchBtnCompras');
    var noResultsRowCompras = document.getElementById('noResultsRowCompras');
    var initiallyEmptyRowCompras = document.getElementById('initiallyEmptyRowCompras');
    if (searchInputCompras && comprasTableBody) {
        var rowsCompras = comprasTableBody.getElementsByTagName('tr');
        function filterTableCompras() {
            const filter = searchInputCompras.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowCompras) initiallyEmptyRowCompras.style.display = 'none';
            for (let i = 0; i < rowsCompras.length; i++) {
                if (rowsCompras[i].id === 'noResultsRowCompras' || rowsCompras[i].id === 'initiallyEmptyRowCompras') continue;
                const rowText = rowsCompras[i].textContent.toLowerCase();
                rowsCompras[i].style.display = rowText.includes(filter) ? '' : 'none';
                anyRowVisible = anyRowVisible || rowText.includes(filter);
            }
            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowCompras) noResultsRowCompras.style.display = 'table-row';
            } else {
                if(noResultsRowCompras) noResultsRowCompras.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowCompras) {
                    if(initiallyEmptyRowCompras) initiallyEmptyRowCompras.style.display = 'table-row';
                }
            }
            if (filter.length > 0) {
                if(clearSearchBtnCompras) clearSearchBtnCompras.style.display = 'inline-block';
            } else {
                if(clearSearchBtnCompras) clearSearchBtnCompras.style.display = 'none';
            }
        }
        searchInputCompras.addEventListener('input', filterTableCompras);
        if(clearSearchBtnCompras) {
            clearSearchBtnCompras.addEventListener('click', function() {
                searchInputCompras.value = '';
                filterTableCompras();
                searchInputCompras.focus();
            });
        }
        filterTableCompras();
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
                const rowText = rowsEmpresas[i].textContent.toLowerCase();
                rowsEmpresas[i].style.display = rowText.includes(filter) ? '' : 'none';
                anyRowVisible = anyRowVisible || rowText.includes(filter);
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
    var searchInputEnviosElem = document.getElementById('searchInputEnvios');
    var enviosTableBodyElem = document.getElementById('enviosTableBody');
    var clearSearchBtnEnviosElem = document.getElementById('clearSearchBtnEnvios');
    var noResultsRowEnviosElem = document.getElementById('noResultsRowEnvios');
    var initiallyEmptyRowEnviosElem = document.getElementById('initiallyEmptyRowEnvios');
    if (searchInputEnviosElem && enviosTableBodyElem) {
        var rowsEnvios = enviosTableBodyElem.querySelectorAll('tr:not(#noResultsRowEnvios):not(#initiallyEmptyRowEnvios)');
        function filterTableEnviosFunc() {
            const filter = searchInputEnviosElem.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowEnviosElem) initiallyEmptyRowEnviosElem.style.display = 'none';
            rowsEnvios.forEach(row => {
                const rowText = row.textContent.toLowerCase();
                const includesFilter = rowText.includes(filter);
                row.style.display = includesFilter ? '' : 'none';
                anyRowVisible = anyRowVisible || includesFilter;
            });
            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowEnviosElem) noResultsRowEnviosElem.style.display = 'table-row';
            } else {
                if(noResultsRowEnviosElem) noResultsRowEnviosElem.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowEnviosElem) {
                    if(initiallyEmptyRowEnviosElem) initiallyEmptyRowEnviosElem.style.display = 'table-row';
                }
            }
            if (filter.length > 0) {
                if(clearSearchBtnEnviosElem) clearSearchBtnEnviosElem.style.display = 'inline-block';
            } else {
                if(clearSearchBtnEnviosElem) clearSearchBtnEnviosElem.style.display = 'none';
            }
        }
        searchInputEnviosElem.addEventListener('input', filterTableEnviosFunc);
        if(clearSearchBtnEnviosElem) {
            clearSearchBtnEnviosElem.addEventListener('click', function() {
                searchInputEnviosElem.value = '';
                filterTableEnviosFunc();
                searchInputEnviosElem.focus();
            });
        }
        filterTableEnviosFunc();
    }
    var searchInputProductosElem = document.getElementById('searchInputProductos');
    var productosTableBodyElem = document.getElementById('productosTableBody');
    var clearSearchBtnProductosElem = document.getElementById('clearSearchBtnProductos');
    var noResultsRowProductosElem = document.getElementById('noResultsRowProductos');
    var initiallyEmptyRowProductosElem = document.getElementById('initiallyEmptyRowProductos');
    if (searchInputProductosElem && productosTableBodyElem) {
        var rowsProductos = productosTableBodyElem.querySelectorAll('tr:not(#noResultsRowProductos):not(#initiallyEmptyRowProductos)');
        function filterTableProductosFunc() {
            const filter = searchInputProductosElem.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowProductosElem) initiallyEmptyRowProductosElem.style.display = 'none';
            rowsProductos.forEach(row => {
                const rowText = row.textContent.toLowerCase();
                const includesFilter = rowText.includes(filter);
                row.style.display = includesFilter ? '' : 'none';
                anyRowVisible = anyRowVisible || includesFilter;
            });
            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowProductosElem) noResultsRowProductosElem.style.display = 'table-row';
            } else {
                if(noResultsRowProductosElem) noResultsRowProductosElem.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowProductosElem) {
                    if(initiallyEmptyRowProductosElem) initiallyEmptyRowProductosElem.style.display = 'table-row';
                }
            }
            if (filter.length > 0) {
                if(clearSearchBtnProductosElem) clearSearchBtnProductosElem.style.display = 'inline-block';
            } else {
                if(clearSearchBtnProductosElem) clearSearchBtnProductosElem.style.display = 'none';
            }
        }
        searchInputProductosElem.addEventListener('input', filterTableProductosFunc);
        if(clearSearchBtnProductosElem) {
            clearSearchBtnProductosElem.addEventListener('click', function() {
                searchInputProductosElem.value = '';
                filterTableProductosFunc();
                searchInputProductosElem.focus();
            });
        }
        filterTableProductosFunc();
    }
    var searchInputProveedoresElem = document.getElementById('searchInputProveedores');
    var proveedoresTableBodyElem = document.getElementById('proveedoresTableBody');
    var clearSearchBtnProveedoresElem = document.getElementById('clearSearchBtnProveedores');
    var noResultsRowProveedoresElem = document.getElementById('noResultsRowProveedores');
    var initiallyEmptyRowProveedoresElem = document.getElementById('initiallyEmptyRowProveedores');
    if (searchInputProveedoresElem && proveedoresTableBodyElem) {
        var rowsProveedores = proveedoresTableBodyElem.querySelectorAll('tr:not(#noResultsRowProveedores):not(#initiallyEmptyRowProveedores)');
        function filterTableProveedoresFunc() {
            const filter = searchInputProveedoresElem.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowProveedoresElem) initiallyEmptyRowProveedoresElem.style.display = 'none';
            rowsProveedores.forEach(row => {
                const rowText = row.textContent.toLowerCase();
                const includesFilter = rowText.includes(filter);
                row.style.display = includesFilter ? '' : 'none';
                anyRowVisible = anyRowVisible || includesFilter;
            });
            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowProveedoresElem) noResultsRowProveedoresElem.style.display = 'table-row';
            } else {
                if(noResultsRowProveedoresElem) noResultsRowProveedoresElem.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowProveedoresElem) {
                    if(initiallyEmptyRowProveedoresElem) initiallyEmptyRowProveedoresElem.style.display = 'table-row';
                }
            }
            if (filter.length > 0) {
                if(clearSearchBtnProveedoresElem) clearSearchBtnProveedoresElem.style.display = 'inline-block';
            } else {
                if(clearSearchBtnProveedoresElem) clearSearchBtnProveedoresElem.style.display = 'none';
            }
        }
        searchInputProveedoresElem.addEventListener('input', filterTableProveedoresFunc);
        if(clearSearchBtnProveedoresElem) {
            clearSearchBtnProveedoresElem.addEventListener('click', function() {
                searchInputProveedoresElem.value = '';
                filterTableProveedoresFunc();
                searchInputProveedoresElem.focus();
            });
        }
        filterTableProveedoresFunc();
    }
    var searchInputUsuariosElem = document.getElementById('searchInputUsuarios');
    var usuariosTableBodyElem = document.getElementById('usuariosTableBody');
    var clearSearchBtnUsuariosElem = document.getElementById('clearSearchBtnUsuarios');
    var noResultsRowUsuariosElem = document.getElementById('noResultsRowUsuarios');
    var initiallyEmptyRowUsuariosElem = document.getElementById('initiallyEmptyRowUsuarios');
    if (searchInputUsuariosElem && usuariosTableBodyElem){
        var rowsUsuarios = usuariosTableBodyElem.querySelectorAll('tr:not(#noResultsRowUsuarios):not(#initiallyEmptyRowUsuarios)');
        function filterTableUsuariosFunc() {
            const filter = searchInputUsuariosElem.value.toLowerCase();
            let anyRowVisible = false;
            if(initiallyEmptyRowUsuariosElem) initiallyEmptyRowUsuariosElem.style.display = 'none';
            rowsUsuarios.forEach(row => {
                const rowText = row.textContent.toLowerCase();
                const includesFilter = rowText.includes(filter);
                row.style.display = includesFilter ? '' : 'none';
                anyRowVisible = anyRowVisible || includesFilter;
            });
            if (!anyRowVisible && filter.length > 0) {
                if(noResultsRowUsuariosElem) noResultsRowUsuariosElem.style.display = 'table-row';
            } else {
                if(noResultsRowUsuariosElem) noResultsRowUsuariosElem.style.display = 'none';
                if (filter.length === 0 && !anyRowVisible && initiallyEmptyRowUsuariosElem) {
                    if(initiallyEmptyRowUsuariosElem) initiallyEmptyRowUsuariosElem.style.display = 'table-row';
                }
            }
            if (filter.length > 0) {
                if(clearSearchBtnUsuariosElem) clearSearchBtnUsuariosElem.style.display = 'inline-block';
            } else {
                if(clearSearchBtnUsuariosElem) clearSearchBtnUsuariosElem.style.display = 'none';
            }
        }
        searchInputUsuariosElem.addEventListener('input', filterTableUsuariosFunc);
        if(clearSearchBtnUsuariosElem) {
            clearSearchBtnUsuariosElem.addEventListener('click', function() {
                searchInputUsuariosElem.value = '';
                filterTableUsuariosFunc();
                searchInputUsuariosElem.focus();
            });
        }
        filterTableUsuariosFunc();
    }
    var empresaSelectUsuarioFormElem = document.getElementById("empresaId");
    var guardarButtonUsuarioFormElem = document.getElementById("guardarUsuarioBtn");
    if (empresaSelectUsuarioFormElem && guardarButtonUsuarioFormElem) {
        function checkUsuarioFormButtonStateFunc() {
            guardarButtonUsuarioFormElem.disabled = empresaSelectUsuarioFormElem.value === "" || empresaSelectUsuarioFormElem.value === "null";
        }
        empresaSelectUsuarioFormElem.addEventListener('change', checkUsuarioFormButtonStateFunc);
        checkUsuarioFormButtonStateFunc();
    }
    const compraFormElement = document.getElementById('compraForm');
    const tablaDetallesBodyCompra = document.getElementById('detalles-compra-body');
    const templateFilaCompra = document.getElementById('detalleFilaModeloCompra');
    const agregarDetalleBtnCompra = document.getElementById('agregarDetalleCompra');
    const montoTotalInputCompra = document.getElementById('monto_total');
    const guardarButtonCompraForm = document.getElementById("guardarCompraBtn");
    const empresaSelectCompraForm = document.getElementById("empresas_id_empresa");
    if (compraFormElement && tablaDetallesBodyCompra && templateFilaCompra && agregarDetalleBtnCompra && montoTotalInputCompra && guardarButtonCompraForm && empresaSelectCompraForm) {
        function calcularTotalCompra() {
            let total = 0;
            const detalleFilas = tablaDetallesBodyCompra.querySelectorAll('tr:not(#detalleFilaModeloCompra)');
            console.log("Número de filas encontradas para calcular el total:", detalleFilas.length);
            detalleFilas.forEach(fila => {
                const cantidadInput = fila.querySelector('.cantidad-input');
                const precioInput = fila.querySelector('.precio-unitario-input');
                let cantidad = 0;
                let precio = 0;
                if (cantidadInput) {
                    cantidad = parseFloat(cantidadInput.value) || 0;
                    console.log("Cantidad en fila:", cantidad);
                } else {
                    console.log("No se encontró el elemento cantidad-input en la fila.");
                }
                if (precioInput) {
                    precio = parseFloat(precioInput.value) || 0;
                    console.log("Precio en fila:", precio);
                } else {
                    console.log("No se encontró el elemento precio-unitario-input en la fila.");
                }
                total += cantidad * precio;
                console.log("Subtotal de la fila:", cantidad * precio);
            });
            montoTotalInputCompra.value = total.toFixed(2);
            console.log("Importe total calculado:", total);
            checkGuardarCompraButtonState();
        }
        function toggleNewProductFieldsCompra(fila) {
            const checkbox = fila.querySelector('.nuevo-producto-checkbox');
            const existingFieldsDiv = fila.querySelector('.existing-product-fields');
            const newFieldsDiv = fila.querySelector('.new-product-fields');
            const productoSelect = fila.querySelector('.producto-select');
            const nuevoNombreInput = fila.querySelector('.nuevo-producto-nombre');
            const nuevoDescripcionInput = fila.querySelector('.nuevo-producto-descripcion');
            const detalleTipoInput = fila.querySelector('.detalle-tipo-input');
            if (!checkbox || !existingFieldsDiv || !newFieldsDiv || !productoSelect || !nuevoNombreInput || !nuevoDescripcionInput || !detalleTipoInput) return;
            if (checkbox.checked) {
                existingFieldsDiv.classList.add('hidden-fields');
                newFieldsDiv.classList.remove('hidden-fields');
                detalleTipoInput.value = 'new';
                if (nuevoNombreInput) nuevoNombreInput.setAttribute('required', 'required');
                if (productoSelect) productoSelect.removeAttribute('required');
                if (productoSelect) productoSelect.value = "";
            } else {
                existingFieldsDiv.classList.remove('hidden-fields');
                newFieldsDiv.classList.add('hidden-fields');
                detalleTipoInput.value = 'existing';
                if (nuevoNombreInput) nuevoNombreInput.removeAttribute('required');
                if (productoSelect) productoSelect.setAttribute('required', 'required');
                if (nuevoNombreInput) nuevoNombreInput.value = "";
                if (nuevoDescripcionInput) nuevoDescripcionInput.value = "";
            }
        }
        function setupRowListenersCompra(fila) {
            const checkboxNuevoProducto = fila.querySelector('.nuevo-producto-checkbox');
            const eliminarBtn = fila.querySelector('.eliminar-detalle');
            const cantidadInput = fila.querySelector('.cantidad-input');
            const precioInput = fila.querySelector('.precio-unitario-input');
            const productoSelect = fila.querySelector('.producto-select');
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
                (empresaSelectCompraForm && (empresaSelectCompraForm.value === "" || empresaSelectCompraForm.value === "null")) ||
                montoTotal <= 0 || !hasDetails;
        }
        if (empresaSelectCompraForm) {
            empresaSelectCompraForm.addEventListener('change', checkGuardarCompraButtonState);
        }
        checkGuardarCompraButtonState();
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