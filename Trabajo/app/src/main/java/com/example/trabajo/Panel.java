package com.example.trabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabajo.adapters.AlumnoListAdapters;
import com.example.trabajo.models.Alumno;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Panel extends AppCompatActivity {
    private Button btnCerrar;
    private Realm mRealm;
    private TextView probar;

    private AlumnoListAdapters adapter;
    private RecyclerView recyclerView;
    private ArrayList<Alumno> listAlumnos = new ArrayList<Alumno>();
    FrameLayout frameLayout;
    public SharedPreferences prefs;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        setUpRealmConfig();
        mRealm = Realm.getDefaultInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        btnCerrar = findViewById(R.id.btnCerrar);
        frameLayout = findViewById(R.id.frameLayout);

        prefs = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        Bundle bundle = this.getIntent().getExtras();
        SharedPreferences.Editor editor = prefs.edit();



        //Cuando vuelve de vuelve a entrar despues de onDestroy(deberia) guardar estado Entro
        if (prefs.getString("clave","").equals("hola")){

            Alumno alumno = new Alumno(prefs.getString("run","").toString(),prefs.getString("name","").toString(),prefs.getString("pass","").toString(),"Entro");
            mRealm.beginTransaction();
            mRealm.insertOrUpdate(alumno);
            mRealm.commitTransaction();

            adapter = new AlumnoListAdapters(listAlumnos, new AlumnoListAdapters.OnItemClickListener() {
                @Override
                public void OnItemClick(Alumno asig, int position) {

                }

                @Override
                public void OnDeleteClick(Alumno alumno, int position) {

                }
            });

            editor.putString("clave","no");
            editor.apply();
            updateListAlumnos();
        }


        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CerrarSesion();

            }
        });
        updateListAlumnos();

        adapter = new AlumnoListAdapters(listAlumnos, new AlumnoListAdapters.OnItemClickListener() {
            @Override
            public void OnItemClick(Alumno asig, int position) {

            }

            @Override
            public void OnDeleteClick(Alumno alumno, int position) {

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);





    }
    public void CerrarSesion(){
        Alumno alumno = new Alumno(prefs.getString("run",""),prefs.getString("name",""),prefs.getString("pass",""),"Cerrar Sesión");
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(alumno);
        mRealm.commitTransaction();

        //para manejo ver si existe alguien logeado o no en el MainActivity
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre","no");
        editor.apply();

        Intent intent = new Intent(Panel.this, MainActivity.class);
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

    public void updateListAlumnos() {
        /*se agrega a un nuevo arraylist, el listado de alumnos a través de realm*/
        /*Para hacer consultas no es necesario abrir una transacción*/
        listAlumnos = new ArrayList(mRealm.where(Alumno.class).findAll());
    }





    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("clave","hola");
        editor.apply();
        Alumno alumno = new Alumno(prefs.getString("run","").toString(),prefs.getString("name","").toString(),prefs.getString("pass","").toString(),"Destruyo");
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(alumno);
        mRealm.commitTransaction();
        super.onDestroy();

    }

}
