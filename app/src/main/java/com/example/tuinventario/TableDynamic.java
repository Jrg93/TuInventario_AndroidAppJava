package com.example.tuinventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private Conexion conex;


    public TableDynamic(TableLayout tableLayout, Context context){
        this.tableLayout = tableLayout;
        this.context = context;
    }

    public void addHeader(){
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Nombre","Descripcion", "Precio", "Cantidad"};
        for(String c:headerText) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);
    }

    public void addData(){

        conex = new Conexion(context, "bd_inventario", null, 1);

        SQLiteDatabase db = conex.getReadableDatabase();
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT nombre, descripcion, precio, cantidad FROM producto";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    String outlet_nombre= cursor.getString(cursor.getColumnIndex("nombre"));
                    String outlet_descripcion= cursor.getString(cursor.getColumnIndex("descripcion"));
                    String outlet_precio= cursor.getString(cursor.getColumnIndex("precio"));
                    String outlet_cantidad= cursor.getString(cursor.getColumnIndex("cantidad"));

                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={outlet_nombre+"",outlet_descripcion,outlet_precio, outlet_cantidad};
                    for(String text:colText) {
                        TextView tv = new TextView(context);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }

    public void addDataFiltrado(String codigo){

        conex = new Conexion(context, "bd_inventario", null, 1);

        SQLiteDatabase db = conex.getReadableDatabase();
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT nombre, descripcion, precio, cantidad FROM producto WHERE codigo='"+codigo+"'";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
                    String outlet_nombre= cursor.getString(cursor.getColumnIndex("nombre"));
                    String outlet_descripcion= cursor.getString(cursor.getColumnIndex("descripcion"));
                    String outlet_precio= cursor.getString(cursor.getColumnIndex("precio"));
                    String outlet_cantidad= cursor.getString(cursor.getColumnIndex("cantidad"));

                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={outlet_nombre+"",outlet_descripcion,outlet_precio, outlet_cantidad};
                    for(String text:colText) {
                        TextView tv = new TextView(context);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

}

