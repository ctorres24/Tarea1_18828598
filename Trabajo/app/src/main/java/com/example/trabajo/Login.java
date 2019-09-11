package com.example.trabajo;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajo.models.Alumno;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Login extends AppCompatActivity {
    private EditText editUser,editPas;
    private Button btnIngresar;
    private TextView textRegistrar;
    private Realm mRealm;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUser = findViewById(R.id.editUser);
        editPas = findViewById(R.id.editPas);
        btnIngresar = findViewById(R.id.bntIngresar);
        textRegistrar = findViewById(R.id.textRegistrar);
        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("Preference", Context.MODE_PRIVATE);



        textRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registrar.class);
                startActivity(intent);

            }
        });
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alumno a = mRealm.where(Alumno.class).equalTo("rut",editUser.getText().toString()).findFirst();
                if (a!=null){
                    if(mRealm.where(Alumno.class).equalTo("rut",editUser.getText().toString()).findFirst().getPass().equals(editPas.getText().toString())){
                        Logear(a);
                    }else{
                        editPas.setError("Contraseña Invalida");
                    }
                }else {
                    editUser.setError("Usuario No Encontrado");
                }

            }
        });
    }

    public void Logear(Alumno alumnos){
        Alumno alumno = new Alumno(alumnos.getRut(),alumnos.getNombre(),alumnos.getPass(),"Inicio Sesión");

        /*Ahora se agrega el alumno en realm*/
        mRealm.beginTransaction();
        /*este metodo inserta o actualiza en realm de forma automática*/
        mRealm.insertOrUpdate(alumno);
        mRealm.commitTransaction();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre","cristian");
        editor.putString("name",alumnos.getNombre().toString());
        editor.putString("run",alumnos.getRut().toString());
        editor.putString("pass",alumnos.getPass().toString());

        editor.apply();

        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);

    }

    private void setUpRealmConfig() {
        // Se inicializa realm
        Realm.init(this.getApplicationContext());
        // Configuración por defecto en realm
        RealmConfiguration config = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(config);
    }



}