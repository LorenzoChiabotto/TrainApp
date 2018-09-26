package com.lorenzoch.trainapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import Data.Estadistica;
import Data.EstadisticaEjercicio;
import Data.EstadisticaEjercicioDinamico;


public class ac_DetallesEstadistica extends AppCompatActivity implements IDetallesEstadistica{
    Estadistica estadistica;

    TextView txtTitulo;
    ImageButton btGuardar, btEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_fragment_simple);

        txtTitulo = (TextView)findViewById(R.id.txtTituloFragmentSimple);
        btGuardar = (ImageButton) findViewById(R.id.btGuardarFragmentSimple);
        btGuardar.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras().getBundle(getResources().getString(R.string.intent_message));
        if(bundle!=null){
            estadistica = (Estadistica) bundle.getSerializable(getResources().getString(R.string.intent_message_position));
        }

        init();
    }

    private void init(){
        fr_DetalleEstadistica fragmento = new fr_DetalleEstadistica();
        backToLista();

    }

    public void backToLista() {
        txtTitulo.setText(estadistica.getRut().getNombre() + " - " + estadistica.getFecha() + " - "+ estadistica.getHora());
        fr_DetalleEstadistica fragment = new fr_DetalleEstadistica();

        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.intent_message), estadistica);

        fragment.setArguments(bundle);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentSimple, fragment);

        transaction.commit();
    }

    @Override
    public void goToDetalle(EstadisticaEjercicioDinamico stat) {

        txtTitulo.setText(getResources().getString(R.string.dinamico));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fr_DetalleEstadisticaDinamico fragment = new fr_DetalleEstadisticaDinamico();

        if(stat != null){

            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.intent_message), stat);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragmentSimple, fragment, getResources().getString(R.string.fr_detalleestadisticadinamico));
        transaction.addToBackStack(getResources().getString(R.string.fr_detalleestadisticadinamico));

        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtTitulo.setText(estadistica.getRut().getNombre() + " - " + estadistica.getFecha() + " - "+ estadistica.getHora());
    }
}
