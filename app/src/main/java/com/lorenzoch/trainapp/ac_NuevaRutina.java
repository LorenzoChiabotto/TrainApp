package com.lorenzoch.trainapp;

import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import Data.Ejercicio;

public class ac_NuevaRutina extends AppCompatActivity implements INewRutina{
    private static final String TAG = "ac_NuevaRutina";

    ArrayList<Ejercicio> ejercicios = new ArrayList<>();

    //widgets
    TextView txtTitulo;
    ImageButton btGuardar, btEliminar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_fragment_simple);

        txtTitulo = (TextView)findViewById(R.id.txtTituloFragmentSimple);

        btGuardar = (ImageButton) findViewById(R.id.btGuardarFragmentSimple);

        init();
        if(ejercicios.size()>0){
            btGuardar.setVisibility(View.VISIBLE);
        }else {
            btGuardar.setVisibility(View.INVISIBLE);
        }

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = null;
                String tag;

                newFragment = new Dialogs.dg_GuardarRutina();
                tag = getResources().getString(R.string.nombreRutina);
                newFragment.show(getSupportFragmentManager(), tag);
            }
        });

        Log.d(TAG, "onCreate: Done");

    }

    private void init(){
        fr_NuevaRutina fragmento = new fr_NuevaRutina();
        doFragmentTransaction(fragmento, getString(R.string.fr_nuevarutina), false,null);
    }

    public void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, Ejercicio ejercicio){
        FragmentManager fm = getSupportFragmentManager();

        if(ejercicio != null){
            ejercicios.add(ejercicio);

            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }

        FragmentTransaction transaction = fm.beginTransaction();

        txtTitulo.setText(tag);

        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.intent_message), ejercicios);
        fragment.setArguments(bundle);

        if(ejercicios.size()>0){
            btGuardar.setVisibility(View.VISIBLE);
        }else {
            btGuardar.setVisibility(View.INVISIBLE);
        }

        transaction.replace(R.id.fragmentSimple, fragment, tag);

        if (addToBackStack){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    @Override
    public void guardarRutina(String nombre) {
        Logica.altaRutina(this,nombre, ejercicios);
    }

    @Override
    public void addEjercicioToLista(Ejercicio ejercicio) {

    }

    @Override
    public void removeEjercicio(Ejercicio ejercicio){
        ejercicios.remove(ejercicio);

        if(ejercicios.size()>0){
            btGuardar.setVisibility(View.VISIBLE);
        }else {
            btGuardar.setVisibility(View.INVISIBLE);
        }

        doFragmentTransaction(new fr_NuevaRutina(), getString(R.string.fr_listaejercicios),false,null);
    }
}
