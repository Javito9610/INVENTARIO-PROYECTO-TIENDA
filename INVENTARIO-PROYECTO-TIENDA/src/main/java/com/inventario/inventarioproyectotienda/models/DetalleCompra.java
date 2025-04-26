package com.inventario.inventarioproyectotienda.models;


public class DetalleCompra {
    private int idDetallesCompra;
    private int cantidad;
    private double precioUnidad;
    private Compra compra;
    private Producto producto;
    private Empresa empresa;

    public DetalleCompra() {
    }

    public DetalleCompra(int idDetallesCompra, int cantidad, double precioUnidad, Compra compra, Producto producto, Empresa empresa) {
        this.idDetallesCompra = idDetallesCompra;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
        this.compra = compra;
        this.producto = producto;
        this.empresa = empresa;
    }


    public int getIdDetallesCompra() {
        return idDetallesCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public Compra getCompra() {
        return compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }


    public void setIdDetallesCompra(int idDetallesCompra) {
        this.idDetallesCompra = idDetallesCompra;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}

