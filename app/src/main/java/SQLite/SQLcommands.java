package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.lorenzoch.trainapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.Ejercicio;
import Data.EjercicioDinamico;
import Data.EjercicioEstatico;
import Data.Estadistica;
import Data.EstadisticaEjercicio;
import Data.EstadisticaEjercicioDinamico;
import Data.EstadisticaEjercicioEstaticoRepeticiones;
import Data.EstadisticaEjercicioEstaticoTiempo;
import Data.EstaticoRepeticiones;
import Data.EstaticoTiempo;
import Data.LatLongStr;
import Data.Rutina;
import Data.RutinaDia;

/**
 * Created by loren on 17/04/2018.
 */

public class SQLcommands {
    private static final String TAG = "SQLcommands";
    static DataBaseHelper dbHelper;
    static SQLiteDatabase db;


    public static int diaSemana(){
        // Creamos una instancia del calendario
        Calendar now = Calendar.getInstance();



        // Array con los dias de la semana
        int[] strDays = new int[]{
                R.string.domingo,
                R.string.lunes,
                R.string.martes,
                R.string.miercoles,
                R.string.jueves,
                R.string.viernes,
                R.string.sabado,};

        // El dia de la semana inicia en el 1 mientras que el array empieza en el 0
        return strDays[now.get(Calendar.DAY_OF_WEEK) - 1];
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

//  Crear Rutina, se crea la rutina y se almacena en BD junto a todos los ejercicios ---------
    public static void createRutina(Context context,Rutina rutina){

        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableStructure.Rutinas.COLUMN_NOMBRE, rutina.getNombre());
        values.put(TableStructure.Rutinas.COLUMN_BAJA, "false");

        long idRutina = db.insert(TableStructure.Rutinas.TABLE_NAME, null, values);

        long id;

        ContentValues valores = new ContentValues();

        for (Data.Ejercicio ej:rutina.getRutina()) {
            if((EjercicioDinamico.class).isAssignableFrom(ej.getClass())){
                if(((EjercicioDinamico)ej).getClass() == DinamicoDistancia.class){

                    valores.put(TableStructure.EjerciciosDinamicos.COLUMN_RUTINA, idRutina);
                    valores.put(TableStructure.EjerciciosDinamicos.COLUMN_DISTANCIA, ((DinamicoDistancia)ej).getDistKm());

                    id = db.insert(TableStructure.EjerciciosDinamicos.TABLE_NAME, null, valores);
                    valores.clear();

                }else {
                    if(((EjercicioDinamico)ej).getClass() == DinamicoTiempo.class){

                        valores.put(TableStructure.EjerciciosDinamicos.COLUMN_RUTINA, idRutina);
                        valores.put(TableStructure.EjerciciosDinamicos.COLUMN_TIEMPO, ((DinamicoTiempo)ej).getTiempo());

                        id = db.insert(TableStructure.EjerciciosDinamicos.TABLE_NAME, null, valores);
                        valores.clear();
                    }
                    else {

                        valores.put(TableStructure.EjerciciosDinamicos.COLUMN_RUTINA, idRutina);

                        id = db.insert(TableStructure.EjerciciosDinamicos.TABLE_NAME, null, valores);
                        valores.clear();
                    }
                }
            }else{
                if(((EjercicioEstatico)ej).getClass() == EstaticoRepeticiones.class){

                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_RUTINA, idRutina);
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_EJERCICIO, ((EstaticoRepeticiones) ej).getTipoEjercicio());
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_SERIE, ((EstaticoRepeticiones) ej).getNroSeries());
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_REPETICIONES, ((EstaticoRepeticiones) ej).getNroRepeticiones());

                    id = db.insert(TableStructure.EjerciciosEstaticos.TABLE_NAME, null, valores);
                    valores.clear();
                }else{

                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_RUTINA, idRutina);
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_EJERCICIO, ((EstaticoTiempo) ej).getTipoEjercicio());
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_SERIE, ((EstaticoTiempo) ej).getNroSeries());
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_TIEMPO, String.valueOf(((EstaticoTiempo) ej).getTiempo()));
                    valores.put(TableStructure.EjerciciosEstaticos.COLUMN_DESCANSO, String.valueOf(((EstaticoTiempo) ej).getTiempoEntreSeries()));

                    id = db.insert(TableStructure.EjerciciosEstaticos.TABLE_NAME, null, valores);
                    valores.clear();
                }
            }
        }
        db.close();
        dbHelper.close();
    }


// Select rutina
    @Nullable
    public static Rutina selectRutinas(Context context, int id){
    Rutina rutina;
    String nombre;

    //dbHelper = new DataBaseHelper(context);

    //db = dbHelper.getReadableDatabase();


    String[] projection = {
            TableStructure.Rutinas.COLUMN_ID,
            TableStructure.Rutinas.COLUMN_NOMBRE,
    };

    String selection = TableStructure.Rutinas.COLUMN_ID +" =?";

    String[] selectionArgs = {String.valueOf(id)};

    Cursor c = db.query(
            TableStructure.Rutinas.TABLE_NAME,                     // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            selectionArgs,                            // The values for the WHERE clause
            null,                                     // don't group the row_dos_campos
            null,                                     // don't filter by row groups
            null                                 // The sort order
    );

    if(!c.moveToFirst()){
        c.close();
        return null;
    }

    c.moveToFirst();

    nombre = c.getString(c.getColumnIndexOrThrow(TableStructure.Rutinas.COLUMN_NOMBRE));

    ArrayList<Ejercicio> ejercicios = new ArrayList<>();

    ejercicios.addAll(selectEstaticos(context,id));
    ejercicios.addAll(selectDinamicos(context,id));

    rutina = new Rutina(id,ejercicios, nombre);

    c.close();
    //db.close();
    //dbHelper.close();
    return rutina;
}

    public static ArrayList<Rutina> selectRutinas(Context context){
        ArrayList<Rutina> rutinas = new ArrayList<>();
        int id;
        String nombre;

        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getReadableDatabase();


        String[] projection = {
                TableStructure.Rutinas.COLUMN_ID,
                TableStructure.Rutinas.COLUMN_NOMBRE,
        };


        String selection="baja_Rutina = ?";

        String[] selectionArgs = {"false"};

        Cursor c = db.query(
                TableStructure.Rutinas.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        boolean next = c.moveToFirst();

        while (next){

            id = c.getInt(c.getColumnIndexOrThrow(TableStructure.Rutinas.COLUMN_ID));
            nombre = c.getString(c.getColumnIndexOrThrow(TableStructure.Rutinas.COLUMN_NOMBRE));

            ArrayList<Ejercicio> ejercicios = new ArrayList<>();
            ejercicios.addAll(selectEstaticos(context,id));
            ejercicios.addAll(selectDinamicos(context,id));

            rutinas.add(new Rutina(id,ejercicios, nombre));
            next = c.moveToNext();
        }

        c.close();
        db.close();
        dbHelper.close();
        return rutinas;
    }

    private static ArrayList<EjercicioEstatico> selectEstaticos(Context context, int idRutina){

        ArrayList<EjercicioEstatico> ejercicios = new ArrayList<>();


        String[] projection = {
                TableStructure.EjerciciosEstaticos.COLUMN_ID,
                TableStructure.EjerciciosEstaticos.COLUMN_RUTINA,
                TableStructure.EjerciciosEstaticos.COLUMN_EJERCICIO,
                TableStructure.EjerciciosEstaticos.COLUMN_SERIE,
                TableStructure.EjerciciosEstaticos.COLUMN_REPETICIONES
        };


        String selection = TableStructure.EjerciciosEstaticos.COLUMN_RUTINA +" =? "+" AND "+
                TableStructure.EjerciciosEstaticos.COLUMN_REPETICIONES + " IS NOT NULL";

        String[] selectionArgs = {String.valueOf(idRutina)};


        Cursor c = db.query(
                TableStructure.EjerciciosEstaticos.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        String ejercicio;
        int rep, ser, id;

        boolean next = c.moveToFirst();

        while (next){

            ejercicio = c.getString(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_EJERCICIO));
            rep =c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_REPETICIONES));
            ser = c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_SERIE));
            id = c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_ID));

            EstaticoRepeticiones ej = new EstaticoRepeticiones(id,ser,ejercicio,rep);

            ejercicios.add(ej);

            next = c.moveToNext();
        }
        c.close();

        projection = new String[]{
                TableStructure.EjerciciosEstaticos.COLUMN_ID,
                TableStructure.EjerciciosEstaticos.COLUMN_RUTINA,
                TableStructure.EjerciciosEstaticos.COLUMN_EJERCICIO,
                TableStructure.EjerciciosEstaticos.COLUMN_SERIE,
                TableStructure.EjerciciosEstaticos.COLUMN_REPETICIONES,
                TableStructure.EjerciciosEstaticos.COLUMN_TIEMPO,
                TableStructure.EjerciciosEstaticos.COLUMN_DESCANSO
        };


        selection = TableStructure.EjerciciosEstaticos.COLUMN_RUTINA + "=? AND "+
                TableStructure.EjerciciosEstaticos.COLUMN_TIEMPO + " IS NOT NULL";

        selectionArgs = new String[]{String.valueOf(idRutina)};


        c = db.query(
                TableStructure.EjerciciosEstaticos.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        long tiempo, descanso;

        next = c.moveToFirst();

        while (next){

            ejercicio = c.getString(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_EJERCICIO));
            ser =c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_SERIE));
            tiempo = c.getLong(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_TIEMPO));
            descanso = c.getLong(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_DESCANSO));
            id = c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosEstaticos.COLUMN_ID));

            EstaticoTiempo ej = new EstaticoTiempo(id,ser,ejercicio,tiempo,descanso);

            ejercicios.add(ej);

            next = c.moveToNext();
        }

        c.close();
        return ejercicios;
    }

    private static ArrayList<EjercicioDinamico> selectDinamicos(Context context, int idRutina){

        ArrayList<EjercicioDinamico> ejercicios = new ArrayList<>();

        String[] projection = {
                TableStructure.EjerciciosDinamicos.COLUMN_ID,
                TableStructure.EjerciciosDinamicos.COLUMN_RUTINA,
                TableStructure.EjerciciosDinamicos.COLUMN_DISTANCIA,
                TableStructure.EjerciciosDinamicos.COLUMN_TIEMPO,
        };

        // EJERCICIO DINAMICO POR DISTANCIA //

        String selection = TableStructure.EjerciciosDinamicos.COLUMN_RUTINA +" =? "+" AND "+
                TableStructure.EjerciciosDinamicos.COLUMN_DISTANCIA + " IS NOT NULL";

        String[] selectionArgs = {String.valueOf(idRutina)};


        Cursor c = db.query(
                TableStructure.EjerciciosDinamicos.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        int rep, ser, id;
        float distancia;

        boolean next = c.moveToFirst();

        while (next){

            id = c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosDinamicos.COLUMN_ID));
            distancia = c.getFloat(c.getColumnIndexOrThrow(TableStructure.EjerciciosDinamicos.COLUMN_DISTANCIA));

            DinamicoDistancia ej = new DinamicoDistancia(id, distancia);

            ejercicios.add(ej);

            next = c.moveToNext();
        }

        c.close();

        // EJERCICIO DINAMICO POR TIEMPO //

        selection = TableStructure.EjerciciosDinamicos.COLUMN_RUTINA +" =? "+" AND "+
                TableStructure.EjerciciosDinamicos.COLUMN_TIEMPO + " IS NOT NULL";

        selectionArgs = new String[]{String.valueOf(idRutina)};


        c = db.query(
                TableStructure.EjerciciosDinamicos.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        next = c.moveToFirst();

        long tiempo;

        while (next){

            id = c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosDinamicos.COLUMN_ID));
            tiempo = Long.parseLong(c.getString(c.getColumnIndexOrThrow(TableStructure.EjerciciosDinamicos.COLUMN_TIEMPO)));

            DinamicoTiempo ej = new DinamicoTiempo(id, tiempo);

            ejercicios.add(ej);

            next = c.moveToNext();
        }

        c.close();

        // EJERCICIO DINAMICO LIBRE //

        selection = TableStructure.EjerciciosDinamicos.COLUMN_RUTINA +" =? "+" AND "+
                TableStructure.EjerciciosDinamicos.COLUMN_TIEMPO + " IS NULL AND "+
                TableStructure.EjerciciosDinamicos.COLUMN_DISTANCIA + " IS NULL;";

        selectionArgs = new String[]{String.valueOf(idRutina)};


        c = db.query(
                TableStructure.EjerciciosDinamicos.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        next = c.moveToFirst();


        while (next){

            id = c.getInt(c.getColumnIndexOrThrow(TableStructure.EjerciciosDinamicos.COLUMN_ID));

            EjercicioDinamico ej = new EjercicioDinamico(id);

            ejercicios.add(ej);

            next = c.moveToNext();
        }

        c.close();

        return ejercicios;
    }

    public static void bajaRutina(Context context, int id){
        DataBaseHelper dbHelper = new DataBaseHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String selection=TableStructure.Rutinas.COLUMN_ID +" = " +id;

        values.put(TableStructure.Rutinas.COLUMN_BAJA, false);

        db.update(TableStructure.Rutinas.TABLE_NAME, values,selection,null);

        values.clear();



        selection=TableStructure.Plan.COLUMN_MAÑANA +" = " +id;

        values.put(TableStructure.Plan.COLUMN_MAÑANA, "NULL");

        db.update(TableStructure.Plan.TABLE_NAME, values,selection,null);

        values.clear();



        selection=TableStructure.Plan.COLUMN_TARDE +" = " +id;

        values.put(TableStructure.Plan.COLUMN_TARDE, "NULL");

        db.update(TableStructure.Plan.TABLE_NAME, values,selection,null);

        values.clear();



        selection=TableStructure.Plan.COLUMN_NOCHE +" = " +id;

        values.put(TableStructure.Plan.COLUMN_NOCHE, "NULL");

        db.update(TableStructure.Plan.TABLE_NAME, values,selection,null);

        db.close();
        dbHelper.close();
    }


// Plan
    public static void addRutinaToPlan(Context context, Rutina rutina, int momento, int dia){

        DataBaseHelper dbHelper = new DataBaseHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        String selection=TableStructure.Plan.COLUMN_DIA +" = " +dia;

        switch (momento){
            case (R.string.manana):{
                values.put(TableStructure.Plan.COLUMN_MAÑANA, rutina.getId());
                break;
            }
            case (R.string.tarde):{
                values.put(TableStructure.Plan.COLUMN_TARDE, rutina.getId());
                break;
            }
            case (R.string.noche):{
                values.put(TableStructure.Plan.COLUMN_NOCHE, rutina.getId());
                break;
            }
            default:{
                return;
            }
        }

        db.update(TableStructure.Plan.TABLE_NAME, values,selection,null);

        db.close();
        dbHelper.close();
    }

    public static void removeRutinaPlan(Context context, int momento, int dia){
        DataBaseHelper dbHelper = new DataBaseHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        String selection=TableStructure.Plan.COLUMN_DIA +" = " +dia;

        switch (momento){
            case (R.string.manana):{
                values.put(TableStructure.Plan.COLUMN_MAÑANA, "null");
                break;
            }
            case (R.string.tarde):{
                values.put(TableStructure.Plan.COLUMN_TARDE, "null");
                break;
            }
            case (R.string.noche):{
                values.put(TableStructure.Plan.COLUMN_NOCHE, "null");
                break;
            }
            default:{
                return;
            }
        }
        db.update(TableStructure.Plan.TABLE_NAME, values,selection,null);

        values.clear();

        db.close();
        dbHelper.close();
    }

    public static TreeMap<Integer,RutinaDia> selectPlanes(Context context){

        TreeMap<Integer,RutinaDia> plan = new TreeMap<Integer,RutinaDia>();
        TreeMap<Integer,Rutina> rutinas;

        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getReadableDatabase();

        String[] projection = {
                TableStructure.Plan.COLUMN_ID,
                TableStructure.Plan.COLUMN_DIA,
                TableStructure.Plan.COLUMN_MAÑANA,
                TableStructure.Plan.COLUMN_TARDE,
                TableStructure.Plan.COLUMN_NOCHE,
        };


        Cursor c = db.query(
                TableStructure.Plan.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        boolean next = c.moveToFirst();
        RutinaDia rut;
        int dia = -1;

        while(next){
            rutinas = new TreeMap<Integer,Rutina>();
            Log.d(TAG, "selectPlanes: Dia " +c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_DIA)));
            dia++;

            if((Integer)(c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_MAÑANA))) != 0 ){
                Log.d(TAG, "selectPlanes: columnManana " + c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_MAÑANA)));
                rutinas.put(R.string.manana,selectRutinas(context, c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_MAÑANA))));
            }else{
                rutinas.put(R.string.manana, null);
            }
            if((Integer)(c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_TARDE))) != 0){
                Log.d(TAG, "selectPlanes: columnTarde " + c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_TARDE)));
                rutinas.put(R.string.tarde,selectRutinas(context, c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_TARDE))));
            }else{
                rutinas.put(R.string.tarde, null);
            }
            if((Integer)(c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_NOCHE))) != 0){
                Log.d(TAG, "selectPlanes: columnNoche " + c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_NOCHE)));
                rutinas.put(R.string.noche,selectRutinas(context, c.getInt(c.getColumnIndexOrThrow(TableStructure.Plan.COLUMN_NOCHE))));
            }else{
                rutinas.put(R.string.noche, null);
            }
            rut = new RutinaDia(rutinas);
            plan.put(dia, rut);
            Log.d(TAG, "selectPlanes: key" +plan.firstKey());
            next = c.moveToNext();
        }

        c.close();
        db.close();
        dbHelper.close();
        return plan;
    }


//Estadisticas

    public static void insertEstadisticas(Context context, Estadistica stats){
        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableStructure.Estadisticas.COLUMN_DIA, stats.getFecha());
        values.put(TableStructure.Estadisticas.COLUMN_MOMENTO, stats.getHora());
        values.put(TableStructure.Estadisticas.COLUMN_RUTINA, stats.getRut().getId());
        values.put(TableStructure.Estadisticas.COLUMN_COMPLETADO, stats.isFinished());

        long idStat = db.insert(TableStructure.Estadisticas.TABLE_NAME, null, values);


        for (EstadisticaEjercicio stat: stats.getStats()) {
            values.clear();

            if((EstadisticaEjercicioDinamico.class).isAssignableFrom(stat.getClass())){
                values.put(TableStructure.EstadisticasDinamicos.COLUMN_ESTADISTICA, idStat);
                values.put(TableStructure.EstadisticasDinamicos.COLUMN_EJERCICIO, stat.getEjercicio().getId());
                values.put(TableStructure.EstadisticasDinamicos.COLUMN_COMPLETADO, stat.isCompletado());

                values.put(TableStructure.EstadisticasDinamicos.COLUMN_VELOCIDADMEDIA, ((EstadisticaEjercicioDinamico)stat).getVelocidadMedia());
                values.put(TableStructure.EstadisticasDinamicos.COLUMN_DISTANCIA, ((EstadisticaEjercicioDinamico)stat).getDistancia());
                values.put(TableStructure.EstadisticasDinamicos.COLUMN_DESCANSO, ((EstadisticaEjercicioDinamico) stat).getDescanso());
                values.put(TableStructure.EstadisticasDinamicos.COLUMN_TIEMPO, ((EstadisticaEjercicioDinamico)stat).getTiempo());

                long idDinamico = db.insert(TableStructure.EstadisticasDinamicos.TABLE_NAME, null, values);
                if(stat.isCompletado()) {
                    for (LatLongStr e : ((EstadisticaEjercicioDinamico) stat).getRecorrido()) {
                        values.clear();

                        values.put(TableStructure.LocationsDinamicos.COLUMN_ESTADISTICA, idDinamico);
                        values.put(TableStructure.LocationsDinamicos.COLUMN_LATITUD, Double.parseDouble(e.getLatitude()));
                        values.put(TableStructure.LocationsDinamicos.COLUMN_LONGITUD, Double.parseDouble(e.getLongitud()));

                        db.insert(TableStructure.LocationsDinamicos.TABLE_NAME, null, values);

                    }
                }
            }else{
                if(EstadisticaEjercicioEstaticoTiempo.class == stat.getClass()){
                    values.put(TableStructure.EstadisticasEstaticosTiempo.COLUMN_ESTADISTICA, idStat);
                    values.put(TableStructure.EstadisticasEstaticosTiempo.COLUMN_EJERCICIO, stat.getEjercicio().getId());
                    values.put(TableStructure.EstadisticasEstaticosTiempo.COLUMN_COMPLETADO, stat.isCompletado());
                    values.put(TableStructure.EstadisticasEstaticosTiempo.COLUMN_SERIES,((EstadisticaEjercicioEstaticoTiempo)stat).getSeriesCompletadas());

                    db.insert(TableStructure.EstadisticasEstaticosTiempo.TABLE_NAME, null, values);

                }else{
                    values.put(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_ESTADISTICA, idStat);
                    values.put(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_EJERCICIO, stat.getEjercicio().getId());
                    values.put(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_COMPLETADO, stat.isCompletado());
                    values.put(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_SERIES, ((EstadisticaEjercicioEstaticoRepeticiones)stat).getSeriesCompletadas());

                    db.insert(TableStructure.EstadisticasEstaticosRepeticiones.TABLE_NAME, null, values);

                }
            }
        }
    }

    public static ArrayList<Estadistica> selectResumenEstadisticas(Context context){

        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getWritableDatabase();

        ArrayList<Estadistica> stats = new ArrayList<>();


        String[] projection = {
                TableStructure.Estadisticas.COLUMN_ID,
                TableStructure.Estadisticas.COLUMN_DIA,
                TableStructure.Estadisticas.COLUMN_MOMENTO,
                TableStructure.Estadisticas.COLUMN_RUTINA,
                TableStructure.Estadisticas.COLUMN_COMPLETADO,
        };


        String selection="";

        String[] selectionArgs = {};


        Cursor c = db.query(
                TableStructure.Estadisticas.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        String ejercicio;
        boolean next = c.moveToLast();

        while (next){
            boolean value = (c.getInt(c.getColumnIndexOrThrow(TableStructure.Estadisticas.COLUMN_COMPLETADO)) > 0);

            Estadistica st = new Estadistica(c.getInt(c.getColumnIndexOrThrow(TableStructure.Estadisticas.COLUMN_ID)),
                    c.getString(c.getColumnIndexOrThrow(TableStructure.Estadisticas.COLUMN_DIA)),
                    c.getString(c.getColumnIndexOrThrow(TableStructure.Estadisticas.COLUMN_MOMENTO)),
                    selectRutinas(context,c.getInt(c.getColumnIndexOrThrow(TableStructure.Estadisticas.COLUMN_RUTINA))),
                    value);

            stats.add(st);

            next = c.moveToPrevious();
        }
        c.close();
        db.close();
        dbHelper.close();

        return stats;
    }

    public static ArrayList<EstadisticaEjercicio> selectDetalleEstadisticaDinamico(Context context, int idEstadistica, ArrayList<Ejercicio> ejercicios){

        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getReadableDatabase();


        ArrayList<EstadisticaEjercicio> stats = new ArrayList<>();

        String[] projection = new String[]{
                TableStructure.EstadisticasDinamicos.COLUMN_ID,
                TableStructure.EstadisticasDinamicos.COLUMN_COMPLETADO,
                TableStructure.EstadisticasDinamicos.COLUMN_EJERCICIO,
                TableStructure.EstadisticasDinamicos.COLUMN_DISTANCIA,
                TableStructure.EstadisticasDinamicos.COLUMN_TIEMPO,
                TableStructure.EstadisticasDinamicos.COLUMN_DESCANSO,
                TableStructure.EstadisticasDinamicos.COLUMN_VELOCIDADMEDIA,
                TableStructure.EstadisticasDinamicos.COLUMN_CALORIAS,
        };


        String selection = TableStructure.EstadisticasDinamicos.COLUMN_ESTADISTICA +"="+ idEstadistica;

        Cursor c = db.query(
                TableStructure.EstadisticasDinamicos.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null
        );
        Log.d(TAG, "selectDetalleEstadisticaDinamico: "+c.getCount());
        int idEjercicio;
        EstadisticaEjercicioDinamico estDn;
        EjercicioDinamico ed = null;
        boolean next = c.moveToFirst();

        while (next){
            idEjercicio =  c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_EJERCICIO));
            for (Ejercicio e:ejercicios) {
                if(EjercicioDinamico.class.isAssignableFrom(e.getClass()) && idEjercicio == e.getId()){
                    ed = (EjercicioDinamico) e;
                    break;
                }
            }
            boolean value = (c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_COMPLETADO)) > 0);

                    estDn = new EstadisticaEjercicioDinamico(c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_ID)),
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_CALORIAS)),
                    c.getLong(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_TIEMPO)),
                    c.getLong(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_DESCANSO)),
                    ed,
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_VELOCIDADMEDIA)),
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.EstadisticasDinamicos.COLUMN_DISTANCIA)),
                    value);

            next = c.moveToNext();

            stats.add(estDn);
        }
        c.close();
        db.close();
        dbHelper.close();

        return stats;
    }

    public static ArrayList<EstadisticaEjercicio> selectDetalleEstadisticaEstaticaRepeticiones(Context context, int idEstadistica, ArrayList<Ejercicio> ejercicios){


        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getReadableDatabase();

        ArrayList<EstadisticaEjercicio> stats = new ArrayList<EstadisticaEjercicio>();

        String[] projection = {
                TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_ID,
                TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_COMPLETADO,
                TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_EJERCICIO,
                TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_CALORIAS,
                TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_SERIES,
        };


        String selection = TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_ESTADISTICA +" =? ";

        String[] selectionArgs = {String.valueOf(idEstadistica)};


        Cursor c = db.query(
                TableStructure.EstadisticasEstaticosRepeticiones.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        EstadisticaEjercicioEstaticoRepeticiones estRep;
        int idEjercicio;
        EstaticoRepeticiones er = null;
        boolean next = c.moveToFirst();

        while (next){
            idEjercicio =  c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_EJERCICIO));
            for (Ejercicio e:ejercicios) {
                if(EstaticoRepeticiones.class.isAssignableFrom(e.getClass()) && idEjercicio == e.getId()){
                    er = (EstaticoRepeticiones) e;
                    break;
                }

            }

            estRep = new EstadisticaEjercicioEstaticoRepeticiones(
                    c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_ID)),
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_CALORIAS)),
                    0,
                    er,
                    c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosRepeticiones.COLUMN_SERIES)));

            next = c.moveToNext();

            stats.add(estRep);
        }

        c.close();
        db.close();
        dbHelper.close();

        return stats;
    }

    public static ArrayList<EstadisticaEjercicio> selectDetalleEstadisticaEstaticaTiempo(Context context, int idEstadistica, ArrayList<Ejercicio> ejercicios){


        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getReadableDatabase();

        ArrayList<EstadisticaEjercicio> stats = new ArrayList<>();
        String[] projection = new String[]{
                TableStructure.EstadisticasEstaticosTiempo.COLUMN_ID,
                TableStructure.EstadisticasEstaticosTiempo.COLUMN_COMPLETADO,
                TableStructure.EstadisticasEstaticosTiempo.COLUMN_EJERCICIO,
                TableStructure.EstadisticasEstaticosTiempo.COLUMN_CALORIAS,
                TableStructure.EstadisticasEstaticosTiempo.COLUMN_SERIES,
        };


        String selection = TableStructure.EstadisticasEstaticosTiempo.COLUMN_ESTADISTICA +" =? ";
        String[] selectionArgs = {String.valueOf(idEstadistica)};

        Cursor c = db.query(
                TableStructure.EstadisticasEstaticosTiempo.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int idEjercicio;

        EstadisticaEjercicioEstaticoTiempo estTp;
        EstaticoTiempo et = null;
        boolean next = c.moveToFirst();

        while (next){
            idEjercicio =  c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosTiempo.COLUMN_EJERCICIO));
            for (Ejercicio e:ejercicios) {
                if(EstaticoTiempo.class.isAssignableFrom(e.getClass()) && idEjercicio == e.getId()){
                    et = (EstaticoTiempo) e;
                    break;
                }
            }

            estTp = new EstadisticaEjercicioEstaticoTiempo(
                    c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosTiempo.COLUMN_ID)),
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosTiempo.COLUMN_CALORIAS)),
                    0,
                    et,
                    c.getInt(c.getColumnIndexOrThrow(TableStructure.EstadisticasEstaticosTiempo.COLUMN_SERIES)));

            next = c.moveToNext();

            stats.add(estTp);
        }

        c.close();
        db.close();
        dbHelper.close();

        return stats;
    }

    public static ArrayList<LatLng> selectRecorrido(Context context, int idEstadistica){

        dbHelper = new DataBaseHelper(context);

        db = dbHelper.getWritableDatabase();

        ArrayList<LatLng> latLngs = new ArrayList<>();


        String[] projection = {
                TableStructure.LocationsDinamicos.COLUMN_ESTADISTICA,
                TableStructure.LocationsDinamicos.COLUMN_LATITUD,
                TableStructure.LocationsDinamicos.COLUMN_LONGITUD,
        };


        String selection= TableStructure.LocationsDinamicos.COLUMN_ESTADISTICA + " =?";

        String[] selectionArgs = {idEstadistica+""};


        Cursor c = db.query(
                TableStructure.LocationsDinamicos.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the row_dos_campos
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        boolean next = c.moveToFirst();

        while (next){

            latLngs.add(new LatLng(
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.LocationsDinamicos.COLUMN_LATITUD)),
                    c.getFloat(c.getColumnIndexOrThrow(TableStructure.LocationsDinamicos.COLUMN_LONGITUD))
            ));

            next = c.moveToNext();
        }
        c.close();
        db.close();
        dbHelper.close();

        return latLngs;

    }
}
































