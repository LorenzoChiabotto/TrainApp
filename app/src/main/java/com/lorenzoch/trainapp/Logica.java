package com.lorenzoch.trainapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.Ejercicio;
import Data.EjercicioDinamico;
import Data.Estadistica;
import Data.EstadisticaEjercicio;
import Data.EstadisticaEjercicioDinamico;
import Data.EstaticoRepeticiones;
import Data.EstaticoTiempo;
import Data.PlanEntrenamiento;
import Data.Rutina;
import Data.RutinaDia;
import SQLite.SQLcommands;

/**
 * Created by loren on 17/04/2018.
 */

public class Logica {


    public static String formatTime(long tiempo){
        String hms = String.format(Locale.getDefault(),"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(tiempo),
                TimeUnit.MILLISECONDS.toMinutes(tiempo) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tiempo)),
                TimeUnit.MILLISECONDS.toSeconds(tiempo) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tiempo)));

        return hms;
    }

    public static int diaSemana(){
        // Creamos una instancia del calendario
        Calendar now = Calendar.getInstance();



        // Array con los dias de la semana
        int[] strDays = new int[]{
                6,0,1,2,3,4,5};

        // El dia de la semana inicia en el 1 mientras que el array empieza en el 0
        return strDays[now.get(Calendar.DAY_OF_WEEK) - 1];
    }
    public static String fechaHoy(){
        Calendar now = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        return sdf.format(now.getTime());
    }

    public static int momentoDia(){
        Calendar now = Calendar.getInstance();

        int hora = now.get(Calendar.HOUR_OF_DAY);

        if (hora >= 4 && hora <12){
            return R.string.manana;
        }else{
            if(hora >= 12 && hora <= 20){
                return R.string.tarde;
            }
            else{
                return R.string.noche;
            }
        }
    }

    public static int getDia(int id){
        switch (id){
            case(0):{
                return R.string.lunes;
            }
            case(1):{
                return R.string.martes;
            }
            case(2):{
                return R.string.miercoles;
            }
            case(3):{
                return R.string.jueves;
            }
            case(4):{
                return R.string.viernes;
            }
            case(5):{
                return R.string.sabado;
            }
            case(6):{
                return R.string.domingo;
            }
            default:{
                return 0;
            }
        }
    }

    public static Integer[] listResourcesDias(){
        Integer [] ids = new Integer[7];

        ids[0] = R.string.lunes;
        ids[1] = R.string.martes;
        ids[2] = R.string.miercoles;
        ids[3] = R.string.jueves;
        ids[4] = R.string.viernes;
        ids[5] = R.string.sabado;
        ids[6] = R.string.domingo;

        return ids;
    }

    public static Integer[] listResourcesTipoEjercicios(Context context){
        Integer [] ids = new Integer[6];

        ids[0] = R.string.superiores;
        ids[1] = R.string.superioresOblicuos;
        ids[2] = R.string.inferiores;
        ids[3] = R.string.intent_message;
        ids[4] = R.string.sentadillas;
        ids[5] = R.string.flexiones;

        return ids;
    }

    //--------- Crear Ejercicios, unicamente el objeto, no se almacena en BD ---------

    //Ejercicio estatico por repeticiones
    public  static EstaticoRepeticiones createEjercicio(int series, int repeticiones, String tipo){

        EstaticoRepeticiones ej = new EstaticoRepeticiones(series, tipo, repeticiones);

        //rutina.add(ej);

        return ej;
    }

    //Ejercicio estatico por tiempo
    public  static EstaticoTiempo createEjercicio(int series, String tipo, int MM, int SS, int sMM, int sSS){

        EstaticoTiempo ej = new EstaticoTiempo(series, tipo, MM*60000 + SS*1000,  sMM*60000 + sSS*1000);

        //rutina.add(ej);

        return ej;
    }

    //Ejercicio dinamico por tiempo
    public  static DinamicoTiempo createEjercicio(int HH, int MM, int SS){
        long tiempo = HH*3600000 + MM*60000 + SS*1000;
        DinamicoTiempo ej = new DinamicoTiempo(tiempo);

        //rutina.add(ej);

        return ej;
    }

    //Ejercicio dinamico por distancia
    public  static DinamicoDistancia createEjercicio(float distancia){

        DinamicoDistancia ej = new DinamicoDistancia(distancia);

        //rutina.add(ej);

        return ej;
    }

    //Ejercicio dinamico libre
    public  static EjercicioDinamico createEjercicio(){
        EjercicioDinamico ej = new EjercicioDinamico();

        //rutina.add(ej);

        return ej;
    }

    public static void altaRutina(Context context,String nombre, ArrayList<Ejercicio> rutina){
        Rutina rt = new Rutina(rutina, nombre);

        SQLcommands.createRutina(context,rt);
        rutina.clear();
    }

    public static void bajaRutina(Context context, Rutina rutina){
        SQLcommands.bajaRutina(context, rutina.getId());
    }

    public static PlanEntrenamiento selectPlan(Context context) {
        return new PlanEntrenamiento(SQLcommands.selectPlanes(context));
    }

    public static void RemoveRutinaPlan(Context context, int momento, int dia){
        SQLcommands.removeRutinaPlan(context,momento,dia);
    }
    public static void putRutinaPlan(Context context, Rutina rutina, int momento, int dia){
        SQLcommands.addRutinaToPlan(context,rutina,momento,dia);
    }

    public static Rutina selectRutinaDelDia(Context context){
        PlanEntrenamiento plan = selectPlan(context);

        return plan.getPlan().get(diaSemana()).getRutina().get(momentoDia());
    }

    public static void insertEstadistica(Context context, Estadistica stats){
        SQLcommands.insertEstadisticas(context, stats);
    }

    public static ArrayList<EstadisticaEjercicio> selectDetalleEstadistica(Context context, int idEstadistica, ArrayList<Ejercicio> ejercicios){

        ArrayList<EstadisticaEjercicio> stats = new ArrayList<>();

        stats.addAll(SQLcommands.selectDetalleEstadisticaDinamico(context,idEstadistica, ejercicios));
        stats.addAll(SQLcommands.selectDetalleEstadisticaEstaticaRepeticiones(context,idEstadistica, ejercicios));
        stats.addAll(SQLcommands.selectDetalleEstadisticaEstaticaTiempo(context,idEstadistica, ejercicios));

        return stats;

    }

    public static ArrayList<LatLng> selectRecorrido(Context context,EstadisticaEjercicioDinamico ejercicioDinamico){

        return SQLcommands.selectRecorrido(context, ejercicioDinamico.getId());
    }
}
