package com.example.grupo_01_tarea_12_ejercicio_01.modelo;

import java.util.Date;

public class Pedido {

    private int idPedido;
    private int codigo;
    private Date fechaEnvio;
    private int idCliente;
    private int idDireccion;

    public Pedido() {
    }

    public Pedido(int codigo, Date fechaEnvio, int idCliente, int idDireccion) {
        this.codigo = codigo;
        this.fechaEnvio = fechaEnvio;
        this.idCliente = idCliente;
        this.idDireccion = idDireccion;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
}
