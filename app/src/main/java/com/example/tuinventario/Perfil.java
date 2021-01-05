package com.example.tuinventario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuinventario.Entidades.UsuarioDAO;


/**
 * Clase Perfil, creada para ver y editar
 * los datos del usuario
 */


public class Perfil extends AppCompatActivity {

    /**
     * Declaracion de variables
     * @param TextView son los labels asignados a cada EditText
     * @param EditText son las cajas de texto en las que el usuario ingresara la informacion
     * @param Switch switch creado para activar o desactivar el guardado en la nube
     * @param Button Botones disponibles en la pantalla de Perfil.
     */

    private SharedPreferences prefs;

    TextView txt_nombre, txt_email, txt_pass, txt_conecnube;
    EditText edtxt_nombre, edtxt_email, edtxt_pass;
    Switch swt_conecnube;
    Button btn_mod, btn_save, btn_volver, btn_cerrars;
    UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        btn_volver = findViewById(R.id.btn_volverper);
        btn_cerrars = findViewById(R.id.btn_cerrars);
        btn_mod = findViewById(R.id.btn_modificarper);
        btn_save = findViewById(R.id.btn_guardarper);

        edtxt_nombre = findViewById(R.id.edtxt_nombreper);
        edtxt_email = findViewById(R.id.edtxt_emailper);
        edtxt_pass = findViewById(R.id.edtxt_passper);

        swt_conecnube = findViewById(R.id.swt_nube);

        dao = new UsuarioDAO(this);

        cargarUsuario();

        /**
         * Metodo para boton volver, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Menu
         */
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Perfil.this, Menu.class);
                startActivity(volver);
            }
        });

        /**
         * Metodo para boton cerrars, al momento de presionar en el boton se ejecutara
         * un dialogo de confirmacion
         */
        btn_cerrars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoCerrarS();
            }
        });

        /**
         * Metodo para boton mod, al momento de presionar en el boton se habilitaran
         * los editText pudiendo asi editarlos, tambien se habilitara el boton guardar
         * pudiendo salvar la informacion
         */
        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtxt_nombre.setEnabled(true);
                edtxt_email.setEnabled(true);
                edtxt_pass.setEnabled(true);
                swt_conecnube.setEnabled(true);
                btn_save.setEnabled(true);
            }
        });

        /**
         * Metodo para boton cerrars, al momento de presionar en el boton se ejecutara
         * un dialogo de confirmacion
         */
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edtxt_nombre.getText().toString()) && !TextUtils.isEmpty(edtxt_email.getText().toString()) && !TextUtils.isEmpty(edtxt_pass.getText().toString())){
                    String email = Util.getUserMailPrefs(prefs);
                    if(email.equalsIgnoreCase(edtxt_email.getText().toString())){
                        dao.actualizarUsuario(edtxt_email.getText().toString().trim(), email, edtxt_nombre.getText().toString().trim(), edtxt_pass.getText().toString().trim());
                        Util.saveOnPreferences(edtxt_email.getText().toString(), edtxt_pass.getText().toString(), prefs);
                        Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                    }else{
                        Cursor cursor = dao.comprobarCorreo(edtxt_email.getText().toString());
                        if(cursor.getCount()>0){
                            Toast.makeText(getApplicationContext(), "Correo no disponible", Toast.LENGTH_SHORT).show();
                        }else{
                            dao.actualizarUsuario(edtxt_email.getText().toString().trim(), email, edtxt_nombre.getText().toString().trim(), edtxt_pass.getText().toString().trim());
                            Util.saveOnPreferences(edtxt_email.getText().toString(), edtxt_pass.getText().toString(), prefs);
                            Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Metodo de Dialogos, creado para crear dialogos que se mostraran en determinadas
     * circunstancias.
     */
    private void dialogoCerrarS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        builder.setTitle("Cerrar Sesión")
                .setMessage("¿Está seguro que desea cerrar sesión?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeSharedPreferences();
                        Intent intent = new Intent(Perfil.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    /**
     * Metodo de Dialogos, creado para crear dialogos que se mostraran en determinadas
     * circunstancias.
     */
    /*private void dialogoGuardar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        builder.setTitle("Mensaje")
                .setMessage("Perfil modificado con éxito")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtxt_nombre.setEnabled(false);
                        edtxt_email.setEnabled(false);
                        edtxt_pass.setEnabled(false);
                        swt_conecnube.setEnabled(false);
                        btn_save.setEnabled(false);
                    }
                }).show();
    }*/

    private void cargarUsuario() {
        String correo = Util.getUserMailPrefs(prefs);
        String pass = Util.getUserPassPrefs(prefs);
        edtxt_email.setText(correo);
        edtxt_pass.setText(pass);

        Cursor cursor = dao.comprobarCorreo(correo);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String nom = cursor.getString(cursor.getColumnIndex("nombre"));
                edtxt_nombre.setText(nom);
            }
        }
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }




}