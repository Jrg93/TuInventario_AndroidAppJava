package com.example.tuinventario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tuinventario.Entidades.Producto;
import com.example.tuinventario.Entidades.ProductoDAO;

/**
 * Clase Añadir productos, creada para ingresar productos de forma manual
 */


public class Aniadir_Producto extends AppCompatActivity {

    /**
     * Declaracion de variables
     * @param TextView son los labels asignados a cada EditText
     * @param EditText son las cajas de texto en las que el usuario ingresara la informacion
     * @param Button Botones disponibles en la pantalla de añadir productos.
     */
    private TextView txt_nombre, txt_precio, txt_cantidad, txt_descripcion, txt_codigo;
    private EditText edtxt_nombre, edtxt_precio, edtxt_cantidad, edtxt_descripcion, edtxt_codigo;
    private Button btn_añadir, btn_volver, btnCapturarCode;
    private ProductoDAO productoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir__producto);

        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */
        txt_nombre = (TextView) findViewById(R.id.txt_nombrepro);
        txt_precio = (TextView) findViewById(R.id.txt_preciopro);
        txt_cantidad = (TextView) findViewById(R.id.txt_cantidadpro);
        txt_descripcion = (TextView) findViewById(R.id.txt_descripcionpro);
        txt_codigo = (TextView) findViewById(R.id.txt_codigopro);

        edtxt_nombre = (EditText) findViewById(R.id.edtxt_nombrepro);
        edtxt_precio = (EditText) findViewById(R.id.edtxt_preciopro);
        edtxt_cantidad = (EditText) findViewById(R.id.edtxt_cantidadpro);
        edtxt_descripcion = (EditText) findViewById(R.id.edtxt_descripcionpro);
        edtxt_codigo = (EditText) findViewById(R.id.edtxt_codigopro);

        btn_añadir = (Button) findViewById(R.id.btn_añadirpro);
        btn_volver = (Button) findViewById(R.id.btn_volverpro);
        btnCapturarCode = (Button) findViewById(R.id.scan_button);

        productoDAO = new ProductoDAO(this);


        /**
         * Metodo para boton añadir, al momento de presionar en el boton se ejecutara un
         * mensaje que mostrara al usuario que el producto fue añadido con exito
         */
        btn_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor= productoDAO.comprobarCodigo(edtxt_codigo.getText().toString());

                if(edtxt_nombre.getText().toString().length()>0 && edtxt_codigo.getText().toString().length()>0 && edtxt_cantidad.getText().toString().length()>0 && edtxt_descripcion.getText().toString().length()>0 && edtxt_precio.getText().toString().length()>0) {
                    if(cursor.getCount()>0){
                        dialogoActualizar();
                    }else{
                        guardar(btn_añadir);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Metodo para boton volver, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Menu
         */
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Aniadir_Producto.this, Menu.class);
                startActivity(volver);
            }
        });

        /**
         * Metodo para boton añadir, al momento de presionar en el boton se ejecutara un switch, el cual
         * comprobara si se presiono el boton, luego ejecutaraIntent lo que nos llevara a la activity asignada, en este caso a la activity SimpleScannerActivity
         */

        btnCapturarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aniadir_Producto.this, SimpleScannerActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Metodo de Dialogos, creado para crear dialogos que se mostraran en determinadas
     * circunstancias.
     */
    private void dialogoActualizar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Aniadir_Producto.this);
        builder.setTitle("Mensaje")
                .setMessage("Este producto ya se encuentra en el inventario, " +
                        "si desea actualizar su stock presione ACTUALIZAR")
                .setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Aniadir_Producto.this, Act_Stock.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                final String result = data.getStringExtra("Codigo_Barra");
                edtxt_codigo.setText(result);
            }else{
                Toast.makeText(this, "Ningún dato leído", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void guardar(View view){
        String codigo = edtxt_codigo.getText().toString().trim();
        String nombre = edtxt_nombre.getText().toString().trim();
        String descripcion = edtxt_descripcion.getText().toString().trim();
        int precio = Integer.parseInt(edtxt_precio.getText().toString().trim());
        int cantidad = Integer.parseInt(edtxt_cantidad.getText().toString().trim());

        Producto p = new Producto();
        p.setCodigo(codigo);
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setCantidad(cantidad);
        productoDAO.insertar(p);

        Toast.makeText(getApplicationContext(), "Producto Añadido con exito!", Toast.LENGTH_SHORT).show();

    }

}
