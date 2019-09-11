package com.example.trabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trabajo.adapters.AlumnoListAdapters;
import com.example.trabajo.models.Alumno;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Registrar extends AppCompatActivity {
    private EditText editRut,editNombre,editPass;
    private Button btnRegistrar;
    private DialogInterface.OnDismissListener onDismissListener;
    private AlumnoListAdapters adapter;
    private ArrayList<Alumno> list;
    private Realm mRealm;
    private SharedPreferences prefs;





    public Registrar setAdapter(AlumnoListAdapters adapter){
        this.adapter = adapter;
        return this;
    }

    public Registrar setArrayList(ArrayList<Alumno> list){
        this.list = list;
        return this;
    }

    public Registrar() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        editRut = findViewById(R.id.editRut);
        editNombre = findViewById(R.id.editNombre);
        editPass = findViewById(R.id.editPass);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();

        prefs = getSharedPreferences("Preference", Context.MODE_PRIVATE);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View view) {
                String rut = editRut.getText().toString();
                String nombre = editNombre.getText().toString();
                String pass = editPass.getText().toString();
                Registrar(rut,nombre,pass);
            }
        });


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

    public void Registrar(String rut, String nombre, String pass){

        Alumno a = mRealm.where(Alumno.class).equalTo("rut",rut).findFirst();
        if(a==null){
            if (isFormularioValido()){
                Alumno alumno = new Alumno(rut,nombre,pass,"Registro");
                /*Ahora se agrega el alumno en realm*/
                mRealm.beginTransaction();
                /*este metodo inserta o actualiza en realm de forma automática*/
                mRealm.insertOrUpdate(alumno);
                mRealm.commitTransaction();


                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("nombre","cristian");

                editor.putString("name",nombre);
                editor.putString("run",rut);
                editor.putString("pass",pass);

                editor.apply();

                Intent intent = new Intent(Registrar.this, MainActivity.class);
                Bundle b = new Bundle();


                intent.putExtras(b);
                startActivity(intent);
            }

        }else{
            editRut.setError("Rut ya registrado");
        }



    }


    public boolean isFormularioValido(){

        boolean r=false;
        if (TextUtils.isEmpty(editRut.getText().toString().trim())){
            editRut.setError("Por favor Ingresa un RUT ");
        }else if (!Utilidades.validarRut(editRut.getText().toString().trim())){
            editRut.setError("Por favor Ingresa un RUT Valido ");
        }
        else if (TextUtils.isEmpty(editNombre.getText().toString().trim())){
            editNombre.setError("Por favor Ingresa un Nombre ");
        }else if (TextUtils.isEmpty(editPass.getText().toString().trim())){
            editPass.setError("Por favor Ingresa un Contraseña ");
        }else{
            r= true;
        }

        return r;
    }
}
