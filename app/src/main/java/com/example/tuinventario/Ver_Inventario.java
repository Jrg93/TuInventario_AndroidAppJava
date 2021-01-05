package com.example.tuinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.tuinventario.Entidades.ProductoDAO;

import java.util.ArrayList;

/**
 * Clase Ver Inventario, creada para ver el inventario
 * con todos los productos agregados
 */

public class Ver_Inventario extends AppCompatActivity {

    /**
     * Declaracion de variables
     * @param TableLayout Tabla creada con el fin de mostrar los productos almacenados en la Base de Datos
     * @param Button Botones disponibles en la pantalla de Ver Inventario.
     */
    private TableLayout tableLayout;
    private Button btn_volver, btn_codigo, btn_buscar;
    private EditText edtxt_buscar;
    private ProductoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver__inventario);

        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */
        tableLayout = (TableLayout) findViewById(R.id.table);

        btn_volver = (Button) findViewById(R.id.btn_volverVerInven);
        btn_codigo = (Button) findViewById(R.id.btn_CodigoVerInven);
        btn_buscar = (Button) findViewById(R.id.btn_buscarVerInven);

        edtxt_buscar = (EditText) findViewById(R.id.edtxt_buscarVerInven);

        dao = new ProductoDAO(this);

        TableDynamic tableDynamic = new TableDynamic(tableLayout, getApplicationContext());
        tableDynamic.addHeader();
        tableDynamic.addData();

        /**
         * Metodo para boton volver, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Menu
         */
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Ver_Inventario.this, Menu.class);
                startActivity(volver);
            }
        });

        btn_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ver_Inventario.this, SimpleScannerActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = dao.comprobarCodigo(edtxt_buscar.getText().toString());

                if(c.getCount()>0){
                    tableLayout.removeAllViews();
                    TableDynamic tableDynamic = new TableDynamic(tableLayout, getApplicationContext());
                    tableDynamic.addHeader();
                    tableDynamic.addDataFiltrado(edtxt_buscar.getText().toString().trim());
                }else{
                    Toast.makeText(getApplicationContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final String result = data.getStringExtra("Codigo_Barra");
                edtxt_buscar.setText(result);
            } else {
                Toast.makeText(this, "Ningún dato leído", Toast.LENGTH_LONG).show();
            }
        }
    }















}