package com.inventario.inventarioproyectotienda.models;

import java.math.BigDecimal;

public class DetalleVenta {
    private int idDetallesVenta;
    private int cantidad;
    private BigDecimal precioUnidad;
    private int ventaId;
    private int productoId;
    private Venta venta;
    private Producto producto;

    public DetalleVenta() {
    }

    public DetalleVenta(int idDetallesVenta, int cantidad, BigDecimal precioUnidad, int ventaId, int productoId, Venta venta, Producto producto) {
        this.idDetallesVenta = idDetallesVenta;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.venta = venta;
        this.producto = producto;
    }

    public int getIdDetallesVenta() {
        return idDetallesVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public void setIdDetallesVenta(int idDetallesVenta) {
        this.idDetallesVenta = idDetallesVenta;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
