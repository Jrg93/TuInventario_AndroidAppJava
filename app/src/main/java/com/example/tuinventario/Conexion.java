package com.example.tuinventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Conexion extends SQLiteOpenHelper{

    public static final String DB_NAME="bd_inventario";
    public static final int VERSION=1;


    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table usuario(id integer primary key autoincrement," +
                "nombre text,correo text, contraseña text)");

        sqLiteDatabase.execSQL("create table producto(codigo text primary key," +
                "nombre text, descripcion text, precio integer, cantidad integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

}
