package com.inventario.inventarioproyectotienda.models;

import java.util.Date;
import java.util.List;

public class Compra {
    private int idCompra;
    private Date fechaCompra;
    private Date fechaDevolucion;
    private double montoTotal;
    private Empresa empresa;
    private Proveedor proveedor;
    private List<DetalleCompra> detallesCompra;

    public Compra() {
    }

    public Compra(int idCompra, Date fechaCompra, Date fechaDevolucion, double montoTotal, Empresa empresa, Proveedor proveedor, List<DetalleCompra> detallesCompra) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.fechaDevolucion = fechaDevolucion;
        this.montoTotal = montoTotal;
        this.empresa = empresa;
        this.proveedor = proveedor;
        this.detallesCompra = detallesCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public java.sql.Date getFechaCompra() {
        return (java.sql.Date) fechaCompra;
    }

    public java.sql.Date getFechaDevolucion() {
        return (java.sql.Date) fechaDevolucion;
    }

    public double getMontoTotal() { // Getter para montoTotal
        return montoTotal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public List<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setMontoTotal(double montoTotal) { // Setter para montoTotal
        this.montoTotal = montoTotal;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setDetallesCompra(List<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }
}