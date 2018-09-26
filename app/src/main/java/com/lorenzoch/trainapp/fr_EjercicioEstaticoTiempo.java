package com.lorenzoch.trainapp;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import Data.EstadisticaEjercicioEstaticoTiempo;
import Data.EstaticoTiempo;

/**
 * Created by loren on 20/04/2018.
 */


public class fr_EjercicioEstaticoTiempo extends Fragment implements Chronometer.OnChronometerTickListener{
    private static final String TAG = "fr_EjercicioEstaticoTie";
    private IStartRutina iStartRutina;

    EstadisticaEjercicioEstaticoTiempo stats;
    EstaticoTiempo ejercicio;
    boolean descanso = false;
    boolean start = true;


    //widgets
    TextView nroSerieX, nroSerieY, nEjercicio;
    Chronometer cronometro;
    ImageView imagen;
    Button btInterrumpir;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            stats = (EstadisticaEjercicioEstaticoTiempo) bundle.getSerializable(getResources().getString(R.string.intent_message));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_ejercicio_estatico_tiempo, container,false);

        nEjercicio = view.findViewById(R.id.txtejercicioFragmentEjercicioEstaticoTiempo);
        nroSerieX = view.findViewById(R.id.txtseriescompletadasFragmentEjercicioEstaticoTiempo);
        nroSerieY = view.findViewById(R.id.txtseriestotalesFragmentEjercicioEstaticoTiempo);
        imagen = view.findViewById(R.id.imgFragmentEjercicioEstaticoTiempo);
        btInterrumpir = view.findViewById(R.id.btinterrumpirFragmentEjercicioEstaticoTiempo);

        ejercicio = stats.getEjercicio();

        nEjercicio.setText(ejercicio.getTipoEjercicio());
        nroSerieX.setText(stats.getSeriesCompletadas()+"");
        nroSerieY.setText("/"+ejercicio.getNroSeries());

        cronometro = view.findViewById(R.id.cronFragmentEjercicioEstaticoTiempo);

        cronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(start){
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    cronometro.start();
                    start = false;
                }
            }
        });


        btInterrumpir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iStartRutina.backToLista(stats);
            }
        });

        cronometro.setOnChronometerTickListener(this);

        loadImg();

        return view;
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        Log.d(TAG, "onChronometerTick: " +( SystemClock.elapsedRealtime() - cronometro.getBase()));

        if(( SystemClock.elapsedRealtime() - cronometro.getBase()) >= ejercicio.getTiempo() && !descanso){
            chronometer.setBackgroundColor(0xffdc3535);
            stats.sumSerie();
            nroSerieX.setText(stats.getSeriesCompletadas()+"");

            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();

            descanso = true;

        }

        if (stats.getSeriesCompletadas() >= ejercicio.getNroSeries()){
            chronometer.stop();
            stats.setCompletado(true);
            iStartRutina.backToLista(stats);
        }else{
            if(( SystemClock.elapsedRealtime() - cronometro.getBase()) >= ejercicio.getTiempoEntreSeries() && descanso){
                chronometer.setBackgroundColor(0xff8FDC35);
                chronometer.stop();

                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setBackgroundColor(0xff8FDC35);


                nroSerieX.setText(stats.getSeriesCompletadas()+"");
                descanso = false;
            }
        }
    }

    private void loadImg(){
        if(ejercicio.getTipoEjercicio().equals(getResources().getString(R.string.superiores))){
            imagen.setImageResource(R.drawable.img_superiores);
        }
        if(ejercicio.getTipoEjercicio().equals(getResources().getString(R.string.superioresOblicuos))){
            imagen.setImageResource(R.drawable.img_superioresoblicuos);

        }
        if(ejercicio.getTipoEjercicio().equals(getResources().getString(R.string.inferiores))){
            imagen.setImageResource(R.drawable.img_inferiores);

        }
        if(ejercicio.getTipoEjercicio().equals(getResources().getString(R.string.inferioresOblicuos))){
            imagen.setImageResource(R.drawable.img_inferiores);

        }
        if(ejercicio.getTipoEjercicio().equals(getResources().getString(R.string.sentadillas))){
            imagen.setImageResource(R.drawable.img_sentadillas);
        }
        if(ejercicio.getTipoEjercicio().equals(getResources().getString(R.string.flexiones))){
            imagen.setImageResource(R.drawable.img_flexiones);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iStartRutina = (IStartRutina) getActivity();
    }

}

