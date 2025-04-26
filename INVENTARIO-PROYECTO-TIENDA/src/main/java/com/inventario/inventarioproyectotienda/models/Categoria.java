package com.inventario.inventarioproyectotienda.models;


import java.util.List;

public class Categoria {
    private int idCategoria;
    private String nombre;
    private Empresa empresa;
    private List<Producto> productos;

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre, Empresa empresa, List<Producto> productos) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.empresa = empresa;
        this.productos = productos;
    }


    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}