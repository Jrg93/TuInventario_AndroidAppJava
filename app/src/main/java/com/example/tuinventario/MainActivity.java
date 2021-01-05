package com.example.tuinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuinventario.Entidades.UsuarioDAO;

/**
 * {@link android.os.Parcelable.Creator} by Jorge Alvarez, Gustavo Badilla, Cristian Hernandez
 */


public class MainActivity extends AppCompatActivity {

    /**
     * Declaracion de variables
     * @param TextView son los labels asignados a cada EditText
     * @param EditText son las cajas de texto en las que el usuario ingresara la informacion
     * @param Button Botones disponibles en la pantalla de inicio de sesion.
     */

    private SharedPreferences prefs;

    private TextView txt_titulo, txt_sesion, txt_email, txt_pass;
    private EditText edtxt_email, edtxt_pass;
    private Button btn_entrar, btn_registrarse, btn_repass;
    private UsuarioDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        /**
         *Enlaces de recursos de la interfaz a la aplicacion
         */
        txt_titulo = (TextView) findViewById(R.id.txt_titulo);
        txt_sesion = (TextView) findViewById(R.id.txt_sesion);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_pass = (TextView) findViewById(R.id.txt_pass);
        edtxt_email = (EditText) findViewById(R.id.edtxt_email_login);
        edtxt_pass = (EditText) findViewById(R.id.edtxt_pass_login);
        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        btn_registrarse = (Button) findViewById(R.id.btn_registrarse);
        btn_repass = (Button) findViewById(R.id.btn_recuperpass);
        dao = new UsuarioDAO(this);


        /**
         * Metodo para boton registrarse, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity Registrarse
         */
        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_regis = new Intent(MainActivity.this, Registrarse.class);
                startActivity(btn_regis);
            }
        });

        /**
         * Metodo para boton entrar, al momento de presionar en el boton se ejecutara Intent
         * lo que nos llevara a la activity asignada, en este caso a la activity {@link Menu}
         */
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = dao.ConsultarUsuario(edtxt_email.getText().toString().trim(), edtxt_pass.getText().toString().trim());
                if(cursor.getCount()>0) {
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Util.saveOnPreferences(edtxt_email.getText().toString().trim(), edtxt_pass.getText().toString().trim(), prefs);
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /*private void setCredentialsIfExists(){
        String correo = Util.getUserMailPrefs(prefs);
        String pass = Util.getUserPassPrefs(prefs);
        if(!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(pass)){
            edtxt_email.setText(correo);
            edtxt_pass.setText(pass);
        }
    }*/

}