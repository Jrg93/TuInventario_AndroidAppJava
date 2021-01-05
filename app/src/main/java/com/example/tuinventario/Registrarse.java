package com.example.tuinventario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tuinventario.Entidades.Usuario;
import com.example.tuinventario.Entidades.UsuarioDAO;


/**
 * Clase Registrarse, creada para registrar
 * los datos del usuario
 */


public class Registrarse extends AppCompatActivity {

    /**
     * Declaracion de variables
     * @param Button Botones disponibles en la pantalla de Registrarse.
     */
    private EditText edtxt_nombre, edtxt_correo, edtxt_contraseña, edtxt_contraseña2;
    private Button btn_regis, btn_volver;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */
        edtxt_nombre = (EditText) findViewById(R.id.edtxt_nombre);
        edtxt_correo = (EditText) findViewById(R.id.edtxt_email);
        edtxt_contraseña = (EditText) findViewById(R.id.edtxt_pass);
        edtxt_contraseña2 = (EditText) findViewById(R.id.edtxt_repass);
        btn_regis = (Button) findViewById(R.id.btn_registrarse);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        usuarioDAO = new UsuarioDAO(this);

        /**
         * Metodo para boton volver, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity MainActivity
         */
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(Registrarse.this, MainActivity.class);
                startActivity(volver);
            }
        });

        /**
         * Metodo para boton regis, al momento de presionar en el boton se ejecutara un
         * mensaje que mostrara al usuario que fue registrado con exito
         */
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor= usuarioDAO.comprobarCorreo(edtxt_correo.getText().toString());

                if(edtxt_nombre.getText().toString().length()>0 && edtxt_correo.getText().toString().length()>0 && edtxt_contraseña.getText().toString().length()>0 && edtxt_contraseña2.getText().toString().length()>0){
                    if (edtxt_correo.getText().toString().matches("[\\w\\.]+@\\w+\\.\\w+") == false) {
                        Toast.makeText(getApplicationContext(), "Email no valido", Toast.LENGTH_SHORT).show();

                    }else{
                        if(edtxt_contraseña.getText().toString().equals(edtxt_contraseña2.getText().toString())){
                            if(cursor.getCount()>0){
                                Toast.makeText(Registrarse.this, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show();
                            }else{
                                guardar(btn_regis);
                                Intent login = new Intent(Registrarse.this, MainActivity.class);
                                startActivity(login);
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void guardar(View view){
        String nombre = edtxt_nombre.getText().toString().trim();
        String email = edtxt_correo.getText().toString().trim();
        String pass = edtxt_contraseña.getText().toString().trim();

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setCorreo(email);
        u.setContraseña(pass);
        usuarioDAO.insertar(u);

        Toast.makeText(this,"Usuario Registrado!",Toast.LENGTH_SHORT).show();

    }

}