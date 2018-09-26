package SQLite;

import android.provider.BaseColumns;

/**
 * Created by loren on 17/04/2018.
 */

public class TableStructure {
    private static String createTable = "CREATE TABLE ";

    public static final String SQL_CREATE_ENTRIES=
            Rutinas.SQL_CREATE+
                    EjerciciosEstaticos.SQL_CREATE+
                    EjerciciosDinamicos.SQL_CREATE+
                    Estadisticas.SQL_CREATE+
                    EstadisticasEstaticosRepeticiones.SQL_CREATE+
                    EstadisticasEstaticosTiempo.SQL_CREATE+
                    EstadisticasDinamicos.SQL_CREATE+
                    LocationsDinamicos.SQL_CREATE
            ;

    public static final String SQL_DELETE_ENTRIES =
            EjerciciosEstaticos.SQL_DELETE+
                    EjerciciosDinamicos.SQL_DELETE+
                    Rutinas.SQL_DELETE+
                    Estadisticas.SQL_DELETE+
                    EstadisticasEstaticosRepeticiones.SQL_DELETE+
                    EstadisticasEstaticosTiempo.SQL_DELETE+
                    EstadisticasDinamicos.SQL_DELETE+
                    LocationsDinamicos.SQL_DELETE
            ;

    private TableStructure(){}

    public static class EjerciciosEstaticos implements BaseColumns {

        public static final String TABLE_NAME = "Ejercicios_Estaticos";
        public static final String COLUMN_EJERCICIO = "ejercicio_Estaticos";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_SERIE = "nroSeries_Estatico";

        public static final String COLUMN_REPETICIONES = "repeticiones_Estatico";
        public static final String COLUMN_TIEMPO = "tiempo_Estatico";
        public static final String COLUMN_DESCANSO = "tiempoEntreSerie_Estatico";

        public static final String COLUMN_RUTINA = "Rutina_Estatico";


        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+
                COLUMN_EJERCICIO + " TEXT NOT NULL" +","+
                COLUMN_SERIE + " INTEGER NO NULL" +","+
                COLUMN_REPETICIONES + " INTEGER" +","+
                COLUMN_TIEMPO+ " LONG" + "," +
                COLUMN_DESCANSO+ " LONG" + "," +
                COLUMN_RUTINA + " INTEGER NOT NULL" + ");";

        public static final String SQL_DELETE = "DROP TABLE " + TABLE_NAME +";";

    }
    public static class EjerciciosDinamicos implements BaseColumns{

        public static final String TABLE_NAME = "Ejercicios_Dinamicos";
        public static final String COLUMN_ID = "_ID";

        public static final String COLUMN_DISTANCIA = "distancia_Dinamicos";
        public static final String COLUMN_TIEMPO = "tiempo_Dinamicos";

        public static final String COLUMN_RUTINA = "Rutina_Dinamico";


        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY" +","+
                COLUMN_DISTANCIA + " FLOAT" +","+
                COLUMN_TIEMPO+ " LONG" + "," +
                COLUMN_RUTINA + " INTEGER NOT NULL" + ");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";
    }
    public static class Rutinas implements BaseColumns{

        public static final String TABLE_NAME = "Rutinas";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_NOMBRE = "nombre_Rutina";
        public static final String COLUMN_BAJA = "baja_Rutina";


        public static final String SQL_CREATE = createTable + TABLE_NAME + " ("+
                COLUMN_ID + " INTEGER PRIMARY KEY" +","+
                COLUMN_BAJA + " BOOLEAN" +","+
                COLUMN_NOMBRE + " TEXT NO NULL" +");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";
    }

    public static class Plan implements BaseColumns{

        public static final String TABLE_NAME = "Plan_Entrenamiento";
        public static final String COLUMN_ID = "_ID";

        public static final String COLUMN_DIA = "dia_Entrenamiento";
        public static final String COLUMN_MAÑANA = "mañana_Entrenamiento";
        public static final String COLUMN_TARDE = "tarde_Entrenamiento";
        public static final String COLUMN_NOCHE = "noche_Entrenamiento";



        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+
                COLUMN_DIA + " TEXT NOT NULL" +","+
                COLUMN_MAÑANA + " INTEGER" +","+
                COLUMN_TARDE + " INTEGER" +","+
                COLUMN_NOCHE+ " INTEGER" + ");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";

    }


    public static class Estadisticas implements BaseColumns{

        public static final String TABLE_NAME = "Estadisticas";
        public static final String COLUMN_ID = "_ID";

        public static final String COLUMN_DIA = "dia_Estadistica";
        public static final String COLUMN_MOMENTO = "momento_Estadistica";
        public static final String COLUMN_RUTINA = "rutina_Estadistica";
        public static final String COLUMN_COMPLETADO = "completado_Estadistica";

        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+

                COLUMN_DIA + " TEXT NOT NULL" +","+
                COLUMN_MOMENTO + " TEXT NOT NULL" +","+
                COLUMN_COMPLETADO + " BOOLEAN NOT NULL" +","+
                COLUMN_RUTINA + " INTEGER NOT NULL" + ");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";

    }

    public static class EstadisticasEstaticosRepeticiones implements BaseColumns{
        public static final String TABLE_NAME = "EstadisticasEstaticosRepeticiones";
        public static final String COLUMN_ID = "_ID";


        public static final String COLUMN_ESTADISTICA = "estadistica_EstadisticasEstaticosRepeticiones";
        public static final String COLUMN_EJERCICIO = "ejercicio_EstadisticasEstaticosRepeticiones";
        public static final String COLUMN_COMPLETADO = "realizado_EstadisticasEstaticosRepeticiones";
        public static final String COLUMN_CALORIAS = "calorias_EstadisticasEstaticosRepeticiones";
        public static final String COLUMN_SERIES = "seriescompletadas_EstadisticasEstaticosRepeticiones";

        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+
                COLUMN_ESTADISTICA + " INT NOT NULL" +","+
                COLUMN_EJERCICIO + " INT NOT NULL" +","+
                COLUMN_CALORIAS + " float" +","+
                COLUMN_COMPLETADO + " BOOLEAN NOT NULL" +","+
                COLUMN_SERIES + " INT NOT NULL" + ");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";
    }

    public static class EstadisticasEstaticosTiempo implements BaseColumns{
        public static final String TABLE_NAME = "EstadisticasEstaticosTiempo";
        public static final String COLUMN_ID = "_ID";


        public static final String COLUMN_ESTADISTICA = "estadistica_EstadisticasEstaticosTiempo";
        public static final String COLUMN_EJERCICIO = "ejercicio_EstadisticasEstaticosTiempo";
        public static final String COLUMN_COMPLETADO = "realizado_EstadisticasEstaticosTiempo";
        public static final String COLUMN_CALORIAS = "calorias_EstadisticasEstaticosTiempo";
        public static final String COLUMN_SERIES = "seriescompletadas_EstadisticasEstaticosTiempo";

        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+

                COLUMN_ESTADISTICA + " INT NOT NULL" +","+
                COLUMN_EJERCICIO + " INT NOT NULL" +","+
                COLUMN_CALORIAS + " FLOAT" +","+
                COLUMN_COMPLETADO + " BOOLEAN NOT NULL" +","+
                COLUMN_SERIES + " INT NOT NULL" + ");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";
    }

    public static class EstadisticasDinamicos implements BaseColumns{

        public static final String TABLE_NAME = "EstadisticasDinamicos";
        public static final String COLUMN_ID = "_ID";


        public static final String COLUMN_ESTADISTICA = "estadistica_EstadisticasDinamicos";
        public static final String COLUMN_EJERCICIO= "ejercicio_EstadisticasDinamicos";
        public static final String COLUMN_COMPLETADO = "realizado_EstadisticasEstaticos";

        public static final String COLUMN_VELOCIDADMEDIA = "velmedia_EstadisticasDinamicos";
        public static final String COLUMN_DISTANCIA= "distancia_EstadisticasDinamicos";
        public static final String COLUMN_TIEMPO = "tiempo_EstadisticasDinamicos";
        public static final String COLUMN_DESCANSO= "descanso_EstadisticasDinamicos";
        public static final String COLUMN_CALORIAS= "calorias_EstadisticasDinamicos";

        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+

                COLUMN_ESTADISTICA + " INT NOT NULL" +","+
                COLUMN_EJERCICIO + " INT NOT NULL" +","+
                COLUMN_COMPLETADO + " BOOLEAN NOT NULL" +","+

                COLUMN_VELOCIDADMEDIA + " FLOAT NOT NULL" +","+
                COLUMN_DISTANCIA + " FLOAT NOT NULL" +","+
                COLUMN_TIEMPO + " Long NOT NULL" +","+
                COLUMN_DESCANSO + " long NOT NULL" +","+
                COLUMN_CALORIAS + " FLOAT" +");";



        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";
    }

    public static class LocationsDinamicos implements BaseColumns{
        public static final String TABLE_NAME = "LocationsDinamicos";
        public static final String COLUMN_ID = "_ID";


        public static final String COLUMN_ESTADISTICA = "estadistica_LocationsDinamicos";
        public static final String COLUMN_LATITUD= "latitud_LocationsDinamicos";
        public static final String COLUMN_LONGITUD= "longitud_LocationsDinamicos";

        public static final String SQL_CREATE = createTable + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY " +","+
                COLUMN_ESTADISTICA + " INT NOT NULL" +","+
                COLUMN_LATITUD + " REAL NOT NULL" +","+
                COLUMN_LONGITUD + " REAL NOT NULL" + ");";

        public static final String SQL_DELETE = "DROP TABLE" + TABLE_NAME +";";
    }
}

