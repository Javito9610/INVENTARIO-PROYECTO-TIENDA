package com.inventario.inventarioproyectotienda.models;

import java.util.Date;

public class Proveedor {
    private int idProveedores;
    private String nombre;
    private String contacto;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private String pais;
    private String rfcNit;
    private String tipo;
    private String sitioWeb;
    private Date fechaRegistro;
    private Empresa empresa;

    public Proveedor() {
    }

    public Proveedor(int idProveedores, String nombre, String contacto, String telefono, String direccion, String ciudad, String provincia, String codigoPostal, String pais, String rfcNit, String tipo, String sitioWeb, Date fechaRegistro, Empresa empresa) {
        this.idProveedores = idProveedores;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.rfcNit = rfcNit;
        this.tipo = tipo;
        this.sitioWeb = sitioWeb;
        this.fechaRegistro = fechaRegistro;
        this.empresa = empresa;
    }


    public int getIdProveedores() {
        return idProveedores;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public String getRfcNit() {
        return rfcNit;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public java.sql.Date getFechaRegistro() {
        return (java.sql.Date) fechaRegistro;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setIdProveedores(int idProveedores) {
        this.idProveedores = idProveedores;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setRfcNit(String rfcNit) {
        this.rfcNit = rfcNit;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}