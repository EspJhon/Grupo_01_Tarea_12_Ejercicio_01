package com.example.grupo_01_tarea_12_ejercicio_01.modelo;

public class Detalle {

    private int idPedido;
    private int idArticulo;
    private int cantidad;

    public Detalle() {}

    public Detalle(int idPedido, int idArticulo, int cantidad) {
        this.idPedido = idPedido;
        this.idArticulo = idArticulo;
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
