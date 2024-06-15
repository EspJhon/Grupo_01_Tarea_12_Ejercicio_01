package com.example.grupo_01_tarea_12_ejercicio_01.modelo;

public class Articulo {

    private int idArticulo;
    private int codigo;
    private String descripcion;
    private int stock;

    public Articulo(){}

    public Articulo(int codigo, String descripcion, int stock) {
        this.codigo=codigo;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
