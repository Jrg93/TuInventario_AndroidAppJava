package com.example.tuinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tuinventario.Entidades.ProductoDAO;


/**
 * Clase Añadir productos, creada para ingresar productos de forma manual
 */

public class Act_Stock extends AppCompatActivity {

    /**
     * Declaracion de variables
     *
     * @param Button Botones disponibles en la pantalla de Actualizar stock.
     */
    private Button btn_volver, btn_codigo, btn_buscar, btn_actualizar;
    private TextView txt_nombre;
    private EditText edtxt_cantidad, edtxt_buscar;
    private ProductoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__stock);

        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */
        btn_volver = (Button) findViewById(R.id.btn_volverActStock);
        btn_codigo = (Button) findViewById(R.id.btn_CodigoActStock);
        btn_buscar = (Button) findViewById(R.id.btn_buscarActStock);
        btn_actualizar = (Button) findViewById(R.id.btn_actulizarActStock);

        txt_nombre = (TextView) findViewById(R.id.txt_nomproActStock);

        edtxt_cantidad = (EditText) findViewById(R.id.txt_cantidadActStock);
        edtxt_buscar = (EditText) findViewById(R.id.edtxt_buscarActStock);

        dao = new ProductoDAO(this);

        /**
         * Metodo para boton volver, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Menu
         */

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Act_Stock.this, Menu.class);
                startActivity(volver);
            }
        });

        btn_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_nombre.setText("");
                edtxt_cantidad.setText("");
                Intent intent = new Intent(Act_Stock.this, SimpleScannerActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatos(edtxt_buscar.getText().toString().trim());
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor= dao.comprobarCodigo(edtxt_buscar.getText().toString().trim());

                if(!edtxt_buscar.getText().toString().isEmpty()){
                    if(cursor.getCount()>0){
                        if(!edtxt_cantidad.getText().toString().isEmpty()){
                            dao.actualizarProducto(edtxt_buscar.getText().toString().trim(), Integer.parseInt(edtxt_cantidad.getText().toString().trim()));
                            Toast.makeText(getApplicationContext(), "Datos actualizados con exito!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Debes ingresar una cantidad", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Debes ingresar un producto", Toast.LENGTH_SHORT).show();
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

    public void cargarDatos(String codigo){

        Cursor cursor= dao.comprobarCodigo(codigo);

        if(cursor!=null && cursor.moveToFirst()){
            String nom = cursor.getString(cursor.getColumnIndex("nombre"));
            String cantidad = cursor.getString(cursor.getColumnIndex("cantidad"));
            edtxt_cantidad.setText(cantidad);
            txt_nombre.setText(nom);
            cursor.close();
        }else{
            Toast.makeText(getApplicationContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
        }

    }

}