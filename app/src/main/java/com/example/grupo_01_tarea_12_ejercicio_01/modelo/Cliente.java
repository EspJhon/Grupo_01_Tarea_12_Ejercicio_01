package com.example.grupo_01_tarea_12_ejercicio_01.modelo;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int idCliente;
    private int codigo;
    private String nombre;
    private String username;
    private String password;
    public Cliente() {}

    public Cliente(int codigo, String nombre, String username, String password) {
        this.codigo=codigo;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

