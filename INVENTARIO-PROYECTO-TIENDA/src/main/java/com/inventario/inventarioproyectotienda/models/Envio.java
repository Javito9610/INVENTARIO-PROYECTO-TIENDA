package com.inventario.inventarioproyectotienda.models;

import java.sql.Date;

public class Envio {
    private int idEnvio;
    private Date fechaEnvio;
    private Date fechaEntrega;
    private String direccionEnvio;
    private String ciudadEnvio;
    private String codigoPostalEnvio;
    private String estadoEnvio;
    private int detalleVentaId;
    private DetalleVenta detalleVenta;
    private Empresa empresa;

    public Envio() {
    }

    public Envio(int idEnvio, Date fechaEnvio, Date fechaEntrega, String direccionEnvio, String ciudadEnvio, String codigoPostalEnvio, String estadoEnvio, int detalleVentaId, DetalleVenta detalleVenta, Empresa empresa) {
        this.idEnvio = idEnvio;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntrega = fechaEntrega;
        this.direccionEnvio = direccionEnvio;
        this.ciudadEnvio = ciudadEnvio;
        this.codigoPostalEnvio = codigoPostalEnvio;
        this.estadoEnvio = estadoEnvio;
        this.detalleVentaId = detalleVentaId;
        this.detalleVenta = detalleVenta;
        this.empresa = empresa;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getCiudadEnvio() {
        return ciudadEnvio;
    }

    public void setCiudadEnvio(String ciudadEnvio) {
        this.ciudadEnvio = ciudadEnvio;
    }

    public String getCodigoPostalEnvio() {
        return codigoPostalEnvio;
    }

    public void setCodigoPostalEnvio(String codigoPostalEnvio) {
        this.codigoPostalEnvio = codigoPostalEnvio;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public int getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(int detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public DetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}