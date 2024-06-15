package com.example.grupo_01_tarea_12_ejercicio_01.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Pedido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBAdapter {
    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "DB_grupo_01";

    //tabla direccion
    private static final String TABLE_DIRECCION = "table_direccion";
    private static final String KEY_ID_DIRECCION= "idDireccion";
    private static final String KEY_DIRECCION_CODIGO = "codigo";
    private static final String KEY_DIRECCION_NUMERO= "numero";
    private static final String KEY_DIRECCION_CALLE= "calle";
    private static final String KEY_DIRECCION_COMUNA= "comuna";
    private static final String KEY_DIRECCION_CIUDAD= "ciudad";

    //FOREIGN KEY: idCliente


    //tabla cliente
    private static final String TABLE_CLIENTE = "table_cliente";
    private static final String KEY_ID_CLIENTE= "idCliente";
    private static final String KEY_CLIENTE_CODIGO = "codigo";
    private static final String KEY_CLIENTE_NOMBRE= "nombre";
    private static final String KEY_CLIENTE_USERNAME = "username";
    private static final String KEY_CLIENTE_PASSWORD = "password";


    //tabla articulo
    private static final String TABLE_ARTICULO = "table_articulo";
    private static final String KEY_ID_ARTICULO= "idArticulo";
    private static final String KEY_ARTICULO_CODIGO = "codigo";
    private static final String KEY_ARTICULO_DESCRIPCION= "descripcion";
    private static final String KEY_ARTICULO_STOCK = "stock";


    //tabla pedido
    private static final String TABLE_PEDIDO = "table_pedido";
    private static final String KEY_ID_PEDIDO= "idPedido";
    private static final String KEY_PEDIDO_CODIGO = "codigo";
    private static final String KEY_PEDIDO_FECHA= "fechaPedido";

    //FOREIGN KEYS: idCliente, idDireccion


    //tabla detalle
    private static final String TABLE_DETALLE = "table_detalle";
    private static final String KEY_DETALLE_CANTIDAD= "cantidad";

    //PRIMARY KEYS: idArticulo, idPedido


    private static final String TABLE_CREATE_CLIENTE =
            "create table " + TABLE_CLIENTE +
                    "(" +
                    KEY_ID_CLIENTE + " integer primary key autoincrement, " +
                    KEY_CLIENTE_CODIGO + " integer not null, " +
                    KEY_CLIENTE_NOMBRE + " text not null, " +
                    KEY_CLIENTE_USERNAME + " text not null, " +
                    KEY_CLIENTE_PASSWORD + " text not null " +
                    ")";

    private static final String TABLE_CREATE_DIRECCION =
            "create table " + TABLE_DIRECCION +
                    "(" +
                    KEY_ID_DIRECCION + " integer primary key autoincrement, " +
                    KEY_DIRECCION_CODIGO + " integer not null, " +
                    KEY_DIRECCION_NUMERO + " integer not null, " +
                    KEY_DIRECCION_CALLE + " text not null, " +
                    KEY_DIRECCION_COMUNA + " text not null, " +
                    KEY_DIRECCION_CIUDAD + " text not null, " +
                    KEY_ID_CLIENTE + " integer not null, " +
                    "FOREIGN KEY (" + KEY_ID_CLIENTE + ") REFERENCES " + TABLE_CLIENTE + "(" + KEY_ID_CLIENTE + ")" +
                    ")";

    private static final String TABLE_CREATE_PEDIDO =
            "create table " + TABLE_PEDIDO +
                    "(" +
                    KEY_ID_PEDIDO + " integer primary key autoincrement, " +
                    KEY_PEDIDO_CODIGO + " integer not null, " +
                    KEY_PEDIDO_FECHA + " date not null, " +
                    KEY_ID_CLIENTE + " integer not null, " +
                    KEY_ID_DIRECCION + " integer not null, " +
                    "FOREIGN KEY (" + KEY_ID_CLIENTE + ") REFERENCES " + TABLE_CLIENTE + "(" + KEY_ID_CLIENTE + "), " +
                    "FOREIGN KEY (" + KEY_ID_DIRECCION + ") REFERENCES " + TABLE_DIRECCION + "(" + KEY_ID_DIRECCION + ")" +
                    ")";


    private static final String TABLE_CREATE_ARTICULO =
            "create table " + TABLE_ARTICULO +
                    "(" +
                    KEY_ID_ARTICULO + " integer primary key autoincrement, " +
                    KEY_ARTICULO_CODIGO + " integer not null, " +
                    KEY_ARTICULO_DESCRIPCION + " text not null, " +
                    KEY_ARTICULO_STOCK + " integer not null " +
                    ")";

    private static final String TABLE_CREATE_DETALLE =
            "create table " + TABLE_DETALLE +
                    "(" +
                    KEY_ID_PEDIDO + " integer not null, " +
                    KEY_ID_ARTICULO + " integer not null, " +
                    KEY_DETALLE_CANTIDAD + " integer not null, " +
                    "FOREIGN KEY (" + KEY_ID_PEDIDO + ") REFERENCES " + TABLE_PEDIDO + "(" + KEY_ID_PEDIDO + "), " +
                    "FOREIGN KEY (" + KEY_ID_ARTICULO + ") REFERENCES " + TABLE_ARTICULO + "(" + KEY_ID_ARTICULO + ")" +
                    ")";


    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static Context context;

    public DBAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE_CLIENTE);
            db.execSQL(TABLE_CREATE_DIRECCION);
            db.execSQL(TABLE_CREATE_PEDIDO);
            db.execSQL(TABLE_CREATE_ARTICULO);
            db.execSQL(TABLE_CREATE_DETALLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_CLIENTE);
            db.execSQL(TABLE_CREATE_CLIENTE);
            db.execSQL("drop table if exists " + TABLE_DIRECCION);
            db.execSQL(TABLE_CREATE_DIRECCION);
            db.execSQL("drop table if exists " + TABLE_PEDIDO);
            db.execSQL(TABLE_CREATE_PEDIDO);
            db.execSQL("drop table if exists " + TABLE_ARTICULO);
            db.execSQL(TABLE_CREATE_ARTICULO);
            db.execSQL("drop table if exists " + TABLE_DETALLE);
            db.execSQL(TABLE_CREATE_DETALLE);
        }
    }

    public DBAdapter open() throws SQLiteException {
        try{
            db=databaseHelper.getWritableDatabase();
        }catch (SQLiteException ex){
            Toast.makeText(context, "Error al abrir Base de datos", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    public boolean isOpen() {
        if (db == null) {
            return false;
        }
        return db.isOpen();
    }

    public void close() {
        databaseHelper.close();
        db.close();
    }


    public void Insertar_Articulos(Articulo articulo) {
        ContentValues values = new ContentValues();
        values.put(KEY_ARTICULO_CODIGO, articulo.getIdArticulo());
        values.put(KEY_ARTICULO_DESCRIPCION, articulo.getDescripcion());
        values.put(KEY_ARTICULO_STOCK, articulo.getStock());
        db.insert(TABLE_ARTICULO, null, values);
    }

    public long Actualizar_Cliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENTE_NOMBRE, cliente.getCodigo());
        values.put(KEY_CLIENTE_USERNAME, cliente.getUsername());
        values.put(KEY_CLIENTE_PASSWORD, cliente.getPassword());

        String whereClause = KEY_ID_CLIENTE + " = ?";
        String[] whereArgs = { String.valueOf(cliente.getIdCliente())};
        int rowsAffected = db.update(TABLE_CLIENTE, values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(context, "Dirección actualizada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No se pudo actualizar la dirección", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public ArrayList<Articulo> get_all_Articulos() {
        ArrayList<Articulo> articulos = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_ARTICULO;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(cursor.getInt(0));
                    articulo.setCodigo(cursor.getInt(1));
                    articulo.setDescripcion(cursor.getString(2));
                    articulo.setStock(cursor.getInt(3));
                    articulos.add(articulo);
                }while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            return null;
        }
        return articulos;
    }
    public void Eliminar_Articulo(Articulo articulo) {
        ContentValues values = new ContentValues();
        db.delete(TABLE_ARTICULO,KEY_ID_ARTICULO + " = " + articulo.getIdArticulo(),null);
    }
    public void Actualizar_Articulo(Articulo articulo) {
        ContentValues values = new ContentValues();
        values.put(KEY_ARTICULO_CODIGO, articulo.getCodigo());
        values.put(KEY_ARTICULO_DESCRIPCION, articulo.getDescripcion());
        values.put(KEY_ARTICULO_STOCK, articulo.getStock());
        db.update(TABLE_ARTICULO, values,KEY_ID_ARTICULO + " = " + articulo.getIdArticulo(),null);
    }
    public Articulo get_Articulos(int codigo) {
        try {
            String query = "select * from " + TABLE_ARTICULO +
                    " where " + KEY_ID_ARTICULO + " = " + codigo;
            Cursor cursor = db.rawQuery(query,null);
            Articulo articulo = null;
            if (cursor.moveToFirst()){
                do {
                    articulo = new Articulo();
                    articulo.setIdArticulo(cursor.getInt(0));
                    articulo.setCodigo(cursor.getInt(1));
                    articulo.setDescripcion(cursor.getString(2));
                    articulo.setStock(cursor.getInt(3));
                }while (cursor.moveToNext());
            }
            cursor.close();
            return articulo;
        } catch (Exception ex) {
            return null;
        }

    }

    public ArrayList<Cliente> get_all_Clientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_CLIENTE;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(cursor.getInt(0));
                    cliente.setCodigo(cursor.getInt(1));
                    cliente.setNombre(cursor.getString(2));
                    clientes.add(cliente);
                }while (cursor.moveToNext());
            }
        } catch (Exception ex){
            return null;
        }
        return clientes;
    }
    public void insertarCliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENTE_CODIGO, cliente.getCodigo());
        values.put(KEY_CLIENTE_NOMBRE, cliente.getNombre());
        values.put(KEY_CLIENTE_USERNAME, cliente.getUsername());
        values.put(KEY_CLIENTE_PASSWORD, cliente.getPassword());
        db.insert(TABLE_CLIENTE, null, values);
    }
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = { KEY_CLIENTE_USERNAME };
        String selection = KEY_CLIENTE_USERNAME + " = ? AND " + KEY_CLIENTE_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(TABLE_CLIENTE, columns, selection, selectionArgs, null, null, null);

        boolean success = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        return success;
    }

    public Cliente getClienteValidado(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {
                KEY_ID_CLIENTE,
                KEY_CLIENTE_CODIGO,
                KEY_CLIENTE_NOMBRE,
                KEY_CLIENTE_USERNAME,
                KEY_CLIENTE_PASSWORD
        };
        String selection = KEY_CLIENTE_USERNAME + " = ? AND " + KEY_CLIENTE_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(TABLE_CLIENTE, columns, selection, selectionArgs, null, null, null);

        Cliente cliente = null;

        try {
            if (cursor != null && cursor.moveToFirst()) {
                cliente = new Cliente();
                cliente.setIdCliente(cursor.getInt(0));
                cliente.setCodigo(cursor.getInt(1));
                cliente.setNombre(cursor.getString(2));
                cliente.setUsername(cursor.getString(3));
                cliente.setPassword(cursor.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cliente;
    }


    public void InsertarPedido(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put(KEY_PEDIDO_CODIGO, pedido.getCodigo());
        // Convertir Date a String en formato yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEnvioStr = dateFormat.format(pedido.getFechaEnvio());

        values.put(KEY_PEDIDO_FECHA, fechaEnvioStr);
        values.put(KEY_ID_CLIENTE, pedido.getIdCliente());
        values.put(KEY_ID_DIRECCION, pedido.getIdDireccion());
        db.insert(TABLE_PEDIDO, null, values);
    }


    public void ActualizarPedido(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put(KEY_PEDIDO_CODIGO, pedido.getCodigo());
        // Convertir Date a String en formato yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEnvioStr = dateFormat.format(pedido.getFechaEnvio());

        values.put(KEY_PEDIDO_FECHA, fechaEnvioStr);
        values.put(KEY_ID_CLIENTE, pedido.getIdCliente());
        values.put(KEY_ID_DIRECCION, pedido.getIdDireccion());
        db.update(TABLE_PEDIDO, values,KEY_ID_PEDIDO + " = " + pedido.getIdPedido(),null);
    }
    public void EliminarPedido(Pedido pedido) {
        ContentValues values = new ContentValues();
        db.delete(TABLE_PEDIDO,KEY_ID_PEDIDO + " = " + pedido.getIdPedido(),null);
    }
    public ArrayList<Pedido> get_all_Pedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_PEDIDO;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(cursor.getInt(0));
                    pedido.setCodigo(cursor.getInt(1));
                    String fechaEnvioStr = cursor.getString(2);
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaEnvio = dateFormat.parse(fechaEnvioStr);
                        pedido.setFechaEnvio(fechaEnvio);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    pedido.setIdCliente(cursor.getInt(3));
                    pedido.setIdDireccion(cursor.getInt(4));
                    pedidos.add(pedido);
                }while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            return null;
        }
        return pedidos;
    }
    public Pedido getPedidos(int codigo) {
        try {
            String query = "select * from " + TABLE_PEDIDO +
                    " where " + KEY_PEDIDO_CODIGO + " = " + codigo;
            Cursor cursor = db.rawQuery(query,null);
            Pedido pedido = null;
            if (cursor.moveToFirst()){
                do {
                    pedido = new Pedido();
                    pedido.setIdPedido(cursor.getInt(0));
                    pedido.setCodigo(cursor.getInt(1));
                    String fechaEnvioStr = cursor.getString(2);
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaEnvio = dateFormat.parse(fechaEnvioStr);
                        pedido.setFechaEnvio(fechaEnvio);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    pedido.setIdCliente(cursor.getInt(3));
                    pedido.setIdDireccion(cursor.getInt(4));

                }while (cursor.moveToNext());
            }
            cursor.close();
            return pedido;
        } catch (Exception ex) {
            return null;
        }

    }


    public void Insertar_Detalles(Detalle detalle) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID_PEDIDO, detalle.getIdPedido());
        values.put(KEY_ID_ARTICULO, detalle.getIdArticulo());
        values.put(KEY_DETALLE_CANTIDAD, detalle.getCantidad());
        db.insert(TABLE_DETALLE, null, values);
    }
    public ArrayList<Detalle> get_all_Detalles(int codigo){
        ArrayList<Detalle> detalles = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_DETALLE + " where " + KEY_ID_PEDIDO + " = " + codigo;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    Detalle detalle = new Detalle();
                    detalle.setIdPedido(cursor.getInt(0));
                    detalle.setIdArticulo(cursor.getInt(1));
                    detalle.setCantidad(cursor.getInt(2));
                    detalles.add(detalle);
                }while (cursor.moveToNext());
            }
        } catch (Exception ex){
            return null;
        }
        return detalles;
    }
    public void EliminarDetalle(Detalle detalle) {
        ContentValues values = new ContentValues();
        db.delete(TABLE_DETALLE,KEY_ID_PEDIDO + " = " + detalle.getIdPedido(),null);
    }


    public void Actualizar_Detalle(Detalle detalle) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID_PEDIDO, detalle.getIdPedido());
        values.put(KEY_ID_ARTICULO, detalle.getIdArticulo());
        values.put(KEY_DETALLE_CANTIDAD, detalle.getCantidad());
        String whereClause = KEY_ID_PEDIDO + " = ? AND " + KEY_ID_ARTICULO + " = ?";
        // Argumentos para la cláusula WHERE
        String[] whereArgs = { String.valueOf(detalle.getIdPedido()), String.valueOf(detalle.getIdArticulo()) };
        db.update(TABLE_DETALLE, values,whereClause,whereArgs);
    }
    public Detalle get_Detalles(int idPedido, int idArticulo, int Stock) {
        try {
            String query = "select * from " + TABLE_DETALLE +
                    " where " + KEY_ID_PEDIDO + " = " + idPedido +
                    " and " + KEY_ID_ARTICULO + " = " + idArticulo +
                    " and " + KEY_DETALLE_CANTIDAD + " = " + Stock;
            Cursor cursor = db.rawQuery(query,null);
            Detalle detalle = null;
            if (cursor.moveToFirst()){
                do {
                    detalle = new Detalle();
                    detalle.setIdPedido(cursor.getInt(0));
                    detalle.setIdArticulo(cursor.getInt(1));
                    detalle.setCantidad(cursor.getInt(2));
                }while (cursor.moveToNext());
            }
            cursor.close();
            return detalle;
        } catch (Exception ex) {
            return null;
        }

    }



    //DIRECCCION
    public ArrayList<Direccion> get_all_Direcciones(){
        ArrayList<Direccion> direcciones = new ArrayList<>();
        try {
            String query = "select * from " + TABLE_DIRECCION;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(cursor.getInt(0));
                    direccion.setCodigo(cursor.getInt(1));
                    direccion.setNumero(cursor.getInt(2));
                    direccion.setCalle(cursor.getString(3));
                    direccion.setComuna(cursor.getString(4));
                    direccion.setCiudad(cursor.getString(5));
                    direccion.setIdCliente(cursor.getInt(6));
                    direcciones.add(direccion);
                }while (cursor.moveToNext());
            }
        } catch (Exception ex){
            return null;
        }
        return direcciones;
    }




    public Direccion getDireccion(int codigo){

        try {
            String query = "select * from " + TABLE_DIRECCION +
                    " where " + KEY_DIRECCION_CODIGO + " = " + codigo;
            Cursor cursor = db.rawQuery(query,null);
            Direccion direccion = null;
            if (cursor.moveToFirst()){
                do {
                    direccion = new Direccion();
                    direccion.setIdDireccion(cursor.getInt(0));
                    direccion.setCodigo(cursor.getInt(1));
                    direccion.setNumero(cursor.getInt(2));
                    direccion.setCalle(cursor.getString(3));
                    direccion.setComuna(cursor.getString(4));
                    direccion.setCiudad(cursor.getString(5));
                    direccion.setIdCliente(cursor.getInt(6));

                }while (cursor.moveToNext());
            }
            cursor.close();
            return direccion;
        } catch (Exception ex){
            return null;
        }
    }
    public long insertarDireccion(Direccion direccion) {
        ContentValues values = new ContentValues();
        values.put(KEY_DIRECCION_CODIGO, direccion.getCodigo());
        values.put(KEY_DIRECCION_NUMERO, direccion.getNumero());
        values.put(KEY_DIRECCION_CALLE, direccion.getCalle());
        values.put(KEY_DIRECCION_COMUNA, direccion.getComuna());
        values.put(KEY_DIRECCION_CIUDAD, direccion.getCiudad());
        values.put(KEY_ID_CLIENTE, direccion.getIdCliente());
        db.insert(TABLE_DIRECCION, null, values);
        return 0;
    }

    public long Actualizar_Direccion(Direccion direccion) {
        ContentValues values = new ContentValues();
        values.put(KEY_DIRECCION_CODIGO, direccion.getCodigo());
        values.put(KEY_DIRECCION_NUMERO, direccion.getNumero());
        values.put(KEY_DIRECCION_CALLE, direccion.getCalle());
        values.put(KEY_DIRECCION_COMUNA, direccion.getComuna());
        values.put(KEY_DIRECCION_CIUDAD, direccion.getCiudad());
        values.put(KEY_ID_CLIENTE, direccion.getIdCliente());

        String whereClause = KEY_ID_DIRECCION + " = ?";
        String[] whereArgs = { String.valueOf(direccion.getIdDireccion())};
        int rowsAffected = db.update(TABLE_DIRECCION, values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(context, "Dirección actualizada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No se pudo actualizar la dirección", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    //Eliminar
    public void Eliminar_Direccion(Direccion direccion){
        ContentValues values = new ContentValues();
        db.delete(TABLE_DIRECCION,KEY_ID_DIRECCION + " = " + direccion.getIdDireccion(),null);

    }
}


