package com.example.grupo_01_tarea_12_ejercicio_01;

import android.app.Application;

public class MyApp extends Application {

    private static String usuario_codigo;
    private static String usuario_passwoor;

    public static String getUsuario_passwoor() {
        return usuario_passwoor;
    }

    public static void setUsuario_passwoor(String usuario_passwoor) {
        MyApp.usuario_passwoor = usuario_passwoor;
    }

    public static String getUsuario_codigo() {
        return usuario_codigo;
    }

    public static void setUsuario_codigo(String usuario_codigo) {
        MyApp.usuario_codigo = usuario_codigo;
    }
}
