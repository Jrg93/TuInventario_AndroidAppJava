package com.example.tuinventario.Entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.tuinventario.Conexion;

import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {

    private  Conexion conex;

    public UsuarioDAO(Context context) {

        conex = new Conexion(context, "bd_inventario", null, 1);

    }

    public void insertar(Usuario usuario) {
        SQLiteDatabase db = conex.getWritableDatabase();
        try {
                ContentValues valores = new ContentValues();
                valores.put("nombre", usuario.getNombre());
                valores.put("correo", usuario.getCorreo());
                valores.put("contraseña", usuario.getContraseña());
                db.insert("usuario", null, valores);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
    }

    public Cursor ConsultarUsuario(String user, String pass) throws SQLException {
        Cursor mcursor=null;
        mcursor = conex.getReadableDatabase().query("usuario", new String[]{"id","nombre","correo","contraseña"}, "correo like '"+user+"' and contraseña like '"+pass+"'"
                , null, null, null, null);
        return mcursor;
    }

    public Cursor comprobarCorreo(String email) throws SQLException {
        Cursor mcursor=null;
        mcursor = conex.getReadableDatabase().query("usuario", new String[]{"id","nombre","correo","contraseña"}, "correo like '"+email+"'"
                , null, null, null, null);
        return mcursor;
    }

    public void actualizarUsuario(String emailCambio, String emailComprobacion, String nom, String pass){
        SQLiteDatabase db = conex.getWritableDatabase();
        try{

            db.execSQL("UPDATE usuario SET nombre='"+nom+"', contraseña='"+pass+"', correo='"+emailCambio+"' WHERE correo='"+emailComprobacion+"'");

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            db.close();
        }
    }

    public List<Usuario> getUsuario(){
        SQLiteDatabase db = conex.getWritableDatabase();
        List<Usuario>lista = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select * from usuario",null);
            while (cursor.moveToNext()){
                Usuario u = new Usuario(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));

                lista.add(u);

            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.close();
        }
        return lista;
    }


}
