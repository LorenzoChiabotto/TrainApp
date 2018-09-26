package com.lorenzoch.trainapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Data.Ejercicio;
import Data.Estadistica;
import Data.EstadisticaEjercicio;

/**
 * Created by loren on 19/04/2018.
 */

public class ac_StartRutina extends AppCompatActivity implements IStartRutina, View.OnClickListener{
    private static final String TAG = "ac_StartRutina";
    TextView txtTitulo;

    ImageButton btFinish, btEliminar;


    Estadistica estadistica;
    int estadisticaSeleccionada;
    EstadisticaEjercicio dinamicotosend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_fragment_simple);

        txtTitulo = (TextView) findViewById(R.id.txtTituloFragmentSimple);
        btEliminar = (ImageButton) findViewById(R.id.btGuardarFragmentSimple);
        btFinish = (ImageButton) findViewById(R.id.btGuardarFragmentSimple);
        btFinish.setImageResource(R.drawable.ic_done);

        Bundle bundle = getIntent().getExtras().getBundle(getResources().getString(R.string.intent_message));
        if (bundle != null) {
            estadistica = (Estadistica) bundle.getSerializable(getResources().getString(R.string.intent_message_position));
        }
        init();

        btFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Logica.insertEstadistica(this,estadistica);
        this.finish();
    }

    private void init(){
        fr_StartRutina_Ejercicios fragmento = new fr_StartRutina_Ejercicios();
        backToLista(null);
    }


    @Override
    public void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, EstadisticaEjercicio stats) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        txtTitulo.setText(tag);
        if(stats != null){

            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.intent_message), stats);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragmentSimple, fragment, tag);

        if (addToBackStack){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    @Override
    public void inflateFragment(int strFragment, EstadisticaEjercicio stat, int i) {
        estadisticaSeleccionada = i;

        btFinish.setVisibility(View.GONE);
        if (strFragment == R.string.fr_estaticorepeticiones){
            fr_EjercicioEstaticoRepeticiones fragment = new fr_EjercicioEstaticoRepeticiones();
            doFragmentTransaction(fragment, getResources().getString(R.string.fr_estaticorepeticiones), true, stat);
        }
        else if (strFragment == R.string.fr_estaticotiempo){
            fr_EjercicioEstaticoTiempo fragment = new fr_EjercicioEstaticoTiempo();
            doFragmentTransaction(fragment, getResources().getString(R.string.fr_estaticotiempo), true, stat);

        }
        else if (strFragment == R.string.fr_dinamico){
            dinamicotosend = stat;
            getLocationPermission();
        }
    }

    @Override
    public void backToLista(EstadisticaEjercicio stat) {
        txtTitulo.setText(estadistica.getRut().getNombre());
        fr_StartRutina_Ejercicios fragment = new fr_StartRutina_Ejercicios();
        btFinish.setVisibility(View.VISIBLE);

        if(stat != null){
            estadistica.editEstadistica(estadisticaSeleccionada, stat);
        }

        if(estadistica.isFinished()){
            Logica.insertEstadistica(this, estadistica);
            this.finish();
        }else {

            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.intent_message), estadistica);

            fragment.setArguments(bundle);

            FragmentManager fm = getSupportFragmentManager();
            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
            FragmentTransaction transaction = fm.beginTransaction();

            transaction.replace(R.id.fragmentSimple, fragment);
            transaction.commit();
        }
    }





    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 3155;

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                goToDinamico();
            }else {
                ActivityCompat.requestPermissions(this,permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");

        switch (requestCode){
            case(LOCATION_PERMISSION_REQUEST_CODE):{
                if(grantResults.length >0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Log.d(TAG, "onRequestPermissionsResult: permissions failed");

                            goToDinamicoDialog();

                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permissions granted");
                    goToDinamico();
                }
            }
        }
    }

    protected void goToDinamico(){
        fr_EjercicioDinamico fragment = new fr_EjercicioDinamico();
        doFragmentTransaction(fragment, getResources().getString(R.string.fr_dinamico), true, dinamicotosend);
    }

    protected void goToDinamicoDialog(){

        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.intent_message),dinamicotosend);

        DialogFragment newFragment = new Dialogs.dg_CompletDinamico();
        newFragment.setArguments(bundle);
        String tag = getResources().getString(R.string.dinamico);
        newFragment.show(getSupportFragmentManager(), tag);

    }

    public void reloadToolBar(){
        btFinish.setVisibility(View.VISIBLE);
    }

}
