package com.inventario.inventarioproyectotienda.models;


import java.util.List;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int anyosGarantia;
    private int unidades;
    private Proveedor proveedor;
    private Empresa empresa;
    private List<Categoria> categorias;
    private List<DetalleCompra> detallesCompra;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String descripcion, double precio, int anyosGarantia, int unidades, Proveedor proveedor, Empresa empresa, List<Categoria> categorias, List<DetalleCompra> detallesCompra) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.anyosGarantia = anyosGarantia;
        this.unidades = unidades;
        this.proveedor = proveedor;
        this.empresa = empresa;
        this.categorias = categorias;
        this.detallesCompra = detallesCompra;
    }


    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getAnyosGarantia() {
        return anyosGarantia;
    }

    public int getUnidades() {
        return unidades;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setAnyosGarantia(int anyosGarantia) {
        this.anyosGarantia = anyosGarantia;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void setDetallesCompra(List<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }
}

