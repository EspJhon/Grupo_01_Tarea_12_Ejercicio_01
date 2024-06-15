package com.example.grupo_01_tarea_12_ejercicio_01.db;

import android.content.Context;

import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Pedido;

import java.util.ArrayList;

public class DBHelper {

    private DBAdapter dbAdapter;

    public DBHelper(Context context) {

        dbAdapter = new DBAdapter(context);
    }


    public void Insertar_Detalles(Detalle detalle){
        dbAdapter.open();
        dbAdapter.Insertar_Detalles(detalle);
        dbAdapter.close();
    }
    public ArrayList<Detalle> get_all_Detalles(int codigo){
        dbAdapter.open();
        ArrayList<Detalle> detalles = dbAdapter.get_all_Detalles(codigo);
        dbAdapter.close();
        return detalles;
    }
    public Detalle get_Detalles(int idPedido, int idArticulo, int Stock){
        dbAdapter.open();
        Detalle detalles = dbAdapter.get_Detalles(idPedido, idArticulo, Stock);
        dbAdapter.close();
        return detalles;
    }
    public void Eliminar_Detalle(Detalle detalle) {
        dbAdapter.open();
        dbAdapter.EliminarDetalle(detalle);
        dbAdapter.close();
    }
    public void Actualizar_Detalle(Detalle detalle) {
        dbAdapter.open();
        dbAdapter.Actualizar_Detalle(detalle);
        dbAdapter.close();
    }


    public void Insertar_Pedidos(Pedido pedido){
        dbAdapter.open();
        dbAdapter.InsertarPedido(pedido);
        dbAdapter.close();
    }
    public ArrayList<Pedido> get_all_Pedidos(){
        dbAdapter.open();
        ArrayList<Pedido> pedidos = dbAdapter.get_all_Pedidos();
        dbAdapter.close();
        return pedidos;
    }
    public Pedido getPedidos(int codigo){
        dbAdapter.open();
        Pedido pedidos = dbAdapter.getPedidos(codigo);
        dbAdapter.close();
        return pedidos;
    }
    public void Eliminar_Pedido(Pedido pedido) {
        dbAdapter.open();
        dbAdapter.EliminarPedido(pedido);
        dbAdapter.close();
    }
    public void Actualizar_Pedido(Pedido pedido) {
        dbAdapter.open();
        dbAdapter.ActualizarPedido(pedido);
        dbAdapter.close();
    }


    public void InsertarArticulos(Articulo articulo){
        dbAdapter.open();
        dbAdapter.Insertar_Articulos(articulo);
        dbAdapter.close();
    }
    public ArrayList<Articulo> get_all_Articulos(){
        dbAdapter.open();
        ArrayList<Articulo> articulos = dbAdapter.get_all_Articulos();
        dbAdapter.close();
        return articulos;
    }
    public Articulo get_Articulos(int codigo){
        dbAdapter.open();
        Articulo articulos = dbAdapter.get_Articulos(codigo);
        dbAdapter.close();
        return articulos;
    }
    public void Eliminar_Articulo(Articulo articulo) {
        dbAdapter.open();
        dbAdapter.Eliminar_Articulo(articulo);
        dbAdapter.close();
    }
    public void Actualizar_Articulo(Articulo articulo) {
        dbAdapter.open();
        dbAdapter.Actualizar_Articulo(articulo);
        dbAdapter.close();
    }


    public ArrayList<Cliente> get_all_Clientes(){
        dbAdapter.open();
        ArrayList<Cliente> clientes = dbAdapter.get_all_Clientes();
        dbAdapter.close();
        return clientes;
    }
    public void insertarCliente(Cliente cliente) {
        dbAdapter.open();
        dbAdapter.insertarCliente(cliente);
        dbAdapter.close();
    }
    public boolean authenticateUser(String username, String password) {
        dbAdapter.open();
        boolean success = dbAdapter.authenticateUser(username, password);
        dbAdapter.close();
        return success;
    }

    public void InsertarDireccion(Direccion direccion) {
        dbAdapter.open();
        dbAdapter.insertarDireccion(direccion);
        dbAdapter.close();
    }

    public ArrayList<Direccion> get_all_Direcciones(){
        dbAdapter.open();
        ArrayList<Direccion> direcciones = dbAdapter.get_all_Direcciones();
        dbAdapter.close();
        return direcciones;
    }

    public Direccion getDirecciones(int codigo){
        dbAdapter.open();
        Direccion direcciones = dbAdapter.getDireccion(codigo);
        dbAdapter.close();
        return direcciones;
    }
    public void Actualizar_Direccion(Direccion direccion) {
        dbAdapter.open();
        dbAdapter.Actualizar_Direccion(direccion);
        dbAdapter.close();
    }
    public void EliminarDireccion(Direccion direccion) {
        dbAdapter.open();
        dbAdapter.Eliminar_Direccion(direccion);
        dbAdapter.close();
    }
    public Cliente objclientevalidar (String username, String password){
        dbAdapter.open();
        Cliente objcliente = dbAdapter.getClienteValidado(username, password);
        dbAdapter.close();
        return  objcliente;
    }
    public void ActualizarCliente(Cliente cliente){
        dbAdapter.open();
        dbAdapter.Actualizar_Cliente(cliente);
        dbAdapter.close();
    }

    public void cerrarSesion() {
        dbAdapter.close();
    }


}
