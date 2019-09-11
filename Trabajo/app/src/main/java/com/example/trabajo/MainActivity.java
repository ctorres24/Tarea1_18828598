package com.example.trabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.trabajo.models.Alumno;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //se instancia las sharedPreference
        prefs = getSharedPreferences("Preference", Context.MODE_PRIVATE);

        if (prefs.getString("nombre","").equals("cristian")){
            Intent intent = new Intent(MainActivity.this,Panel.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }


    }


}

