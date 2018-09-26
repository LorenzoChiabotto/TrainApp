package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lorenzoch.trainapp.R;

/**
 * Created by loren on 17/04/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =6;
    public static final String DATABASE_NAME = "DataBase.db";

    DataBaseHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TableStructure.Rutinas.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.EjerciciosEstaticos.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.EjerciciosDinamicos.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.Plan.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.Estadisticas.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.EstadisticasDinamicos.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.EstadisticasEstaticosTiempo.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.EstadisticasEstaticosRepeticiones.SQL_CREATE);
        sqLiteDatabase.execSQL(TableStructure.LocationsDinamicos.SQL_CREATE);

        ContentValues values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA, 0);
        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

        values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA,1);
        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

        values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA,2);
        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

        values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA,3);
        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

        values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA,4);
        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

        values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA,5);
        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

        values = new ContentValues();
        values.put(TableStructure.Plan.COLUMN_DIA, 6);

        sqLiteDatabase.insert(TableStructure.Plan.TABLE_NAME, null,values );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TableStructure.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
