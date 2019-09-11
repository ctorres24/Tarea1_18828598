package com.example.trabajo.models;


import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alumno extends RealmObject {
   
    @PrimaryKey
    private int id;
    private String rut;
    private String nombre;
    private String pass;
    private String estado;



    /*siempre se debe crear un constructor vació*/
    public Alumno() {
    }


    public Alumno(String rut, String nombre, String pass,String estado) {
        this.rut = rut;
        this.nombre = nombre;
        this.pass = pass;
        this.estado = estado;
        /*se agrega el id de forma automática y no se pide como parametro del constructor*/
        this.id=getNextKey();
    }

    /*Métodos Get y Set de cada uno de los atributos*/
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /*metodo para generar el id correlativo de forma automática*/
    public int getNextKey() {
        try {
            Realm realm = Realm.getDefaultInstance();
            /* se consulta por el id max actual guardado*/
            Number number = realm.where(Alumno.class).max("id");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }
}