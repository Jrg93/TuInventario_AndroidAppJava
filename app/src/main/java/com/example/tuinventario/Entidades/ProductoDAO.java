package com.example.tuinventario.Entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.tuinventario.Conexion;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Conexion conex;

    public ProductoDAO(Context context) {

        conex = new Conexion(context, "bd_inventario", null, 1);

    }

    public void insertar(Producto producto) {
        SQLiteDatabase db = conex.getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put("codigo", producto.getCodigo());
            valores.put("nombre", producto.getNombre());
            valores.put("descripcion", producto.getDescripcion());
            valores.put("precio", producto.getPrecio());
            valores.put("cantidad", producto.getCantidad());
            db.insert("producto", null, valores);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public void actualizarProducto(String codigo, int cantidad){
        SQLiteDatabase db = conex.getWritableDatabase();
        try{

            db.execSQL("UPDATE producto SET cantidad='"+cantidad+"'WHERE codigo='"+codigo+"'");

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            db.close();
        }
    }

    public Cursor comprobarCodigo(String codigo) throws SQLException {
        Cursor mcursor=null;
        mcursor = conex.getReadableDatabase().query("producto", new String[]{"codigo","nombre","descripcion","precio", "cantidad"}, "codigo like '"+codigo+"'"
                , null, null, null, null);
        return mcursor;
    }

    /*public Cursor BuscarCodigo(String codigo) throws SQLException {
        Cursor mcursor=null;
        mcursor = conex.getReadableDatabase().rawQuery("SELECT * FROM producto WHERE codigo='"+codigo+"'", null);
        return mcursor;
    }*/

    public List<Producto> getProducto(){
        SQLiteDatabase db = conex.getWritableDatabase();
        List<Producto>lista = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select * from producto",null);
            while (cursor.moveToNext()){
                Producto p = new Producto(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
        return lista;
    }

}
