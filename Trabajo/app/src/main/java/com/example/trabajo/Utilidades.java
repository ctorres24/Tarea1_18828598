package com.example.trabajo;


import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public  class Utilidades {

    public static String primeraMayuscula(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
    public static void Vibrar(int tipo, Activity a){
        Vibrator vibrator = (Vibrator) a.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {//Si tiene vibrador
            if (tipo == 1) {
                long tiempo = 50; //en milisegundos
                vibrator.vibrate(tiempo);
            }
            if (tipo==2){
                long[] pattern = {400, //sleep
                        600, //vibrate
                        100,300,100,150,100,75};
                vibrator.vibrate(pattern, -1);
            }
        }

    }

    static public String formatearRut(String rut){
        int cont=0;
        String format;
        if(rut.length() == 0){
            return "";
        }else{
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            format = "-"+rut.substring(rut.length()-1);
            for(int i = rut.length()-2;i>=0;i--){
                format = rut.substring(i, i+1)+format;
                cont++;
                if(cont == 3 && i != 0){
                    format = "."+format;
                    cont = 0;
                }
            }
            return format;
        }
    }

    static public boolean validarRut(String rut){
        int suma=0;
        String dvR,dvT;
        int[] serie = {2,3,4,5,6,7};
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        dvR = rut.substring(rut.length()-1);
        for(int i = rut.length()-2;i>=0; i--){
            suma +=  Integer.valueOf(rut.substring(i, i+1))
                    *serie[(rut.length()-2-i)%6];
        }
        dvT=String.valueOf(11-suma%11);
        if(dvT.compareToIgnoreCase("10") == 0){
            dvT = "K";
        }

        if(dvT.compareToIgnoreCase(dvR) == 0){
            return true;
        } else {
            return false;
        }
    }
}