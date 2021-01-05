package com.example.tuinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Clase Menu, creada para ver las diferentes opciones
 * disponibles dentro de la aplicacion
 */


public class Menu extends AppCompatActivity {

    /**
     * Declaracion de variables
     *
     * @param Button Botones disponibles en la pantalla de Menu.
     */
    private Button btn_añadir, btn_perfil, btn_stock, btn_inventario, btn_tutorial;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */
        btn_añadir = findViewById(R.id.btn_añadirMenu);
        btn_perfil = findViewById(R.id.btn_perfilMenu);
        btn_stock = findViewById(R.id.btn_stockMenu);
        btn_inventario = findViewById(R.id.btn_inventarioMenu);
        btn_tutorial = findViewById(R.id.btn_tutorial);

        url = "https://www.youtube.com/watch?v=YqvvDC7Rbhs&feature=emb_err_woyt";

        /**
         * Metodo para boton añadir, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Añadir
         */
        btn_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent añadir = new Intent(Menu.this, Aniadir_Producto.class);
                startActivity(añadir);
            }
        });

        /**
         * Metodo para boton stock, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Actualizar stock
         */
        btn_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stock = new Intent(Menu.this, Act_Stock.class);
                startActivity(stock);
            }
        });

        /**
         * Metodo para boton perfil, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Perfil
         */
        btn_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfil = new Intent(Menu.this, Perfil.class);
                startActivity(perfil);
            }
        });

        /**
         * Metodo para boton Inventario, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Ver Inventario
         */
        btn_inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inventario = new Intent(Menu.this, Ver_Inventario.class);
                startActivity(inventario);
            }
        });

        btn_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });





    }
}