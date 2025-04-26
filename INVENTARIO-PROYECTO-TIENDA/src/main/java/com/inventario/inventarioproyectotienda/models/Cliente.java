package com.inventario.inventarioproyectotienda.models;


public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String numeroTelefono;
    private String correo;
    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String provincia;
    private Empresa empresa;

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellidos, String numeroTelefono, String correo, String direccion, String ciudad, String codigoPostal, String provincia, Empresa empresa) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.correo = correo;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.empresa = empresa;
    }


    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}


