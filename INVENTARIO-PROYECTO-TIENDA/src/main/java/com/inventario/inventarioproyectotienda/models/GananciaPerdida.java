package com.inventario.inventarioproyectotienda.models;


import java.util.Date;

public class GananciaPerdida {
    private int idGananciaPerdida;
    private Date fecha;
    private double ingresos;
    private double costos;
    private double gananciaNeta;
    private Empresa empresa;

    public GananciaPerdida() {
    }

    public GananciaPerdida(int idGananciaPerdida, Date fecha, double ingresos, double costos, double gananciaNeta, Empresa empresa) {
        this.idGananciaPerdida = idGananciaPerdida;
        this.fecha = fecha;
        this.ingresos = ingresos;
        this.costos = costos;
        this.gananciaNeta = gananciaNeta;
        this.empresa = empresa;
    }


    public int getIdGananciaPerdida() {
        return idGananciaPerdida;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getIngresos() {
        return ingresos;
    }

    public double getCostos() {
        return costos;
    }

    public double getGananciaNeta() {
        return gananciaNeta;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setIdGananciaPerdida(int idGananciaPerdida) {
        this.idGananciaPerdida = idGananciaPerdida;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public void setCostos(double costos) {
        this.costos = costos;
    }

    public void setGananciaNeta(double gananciaNeta) {
        this.gananciaNeta = gananciaNeta;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
