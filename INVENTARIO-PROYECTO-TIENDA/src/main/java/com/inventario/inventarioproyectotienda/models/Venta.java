package com.inventario.inventarioproyectotienda.models;

import java.util.Date;
import java.util.List;

public class Venta {
    private int idVenta;
    private Date fechaVenta;
    private double montoTotal;
    private Empresa empresa;
    private Cliente cliente;
    private List<DetalleVenta> detallesVenta;

    public Venta() {
    }

    public Venta(int idVenta, Date fechaVenta, double montoTotal, Empresa empresa, Cliente cliente, List<DetalleVenta> detallesVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.empresa = empresa;
        this.cliente = cliente;
        this.detallesVenta = detallesVenta;
    }


    public int getIdVenta() {
        return idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
}
