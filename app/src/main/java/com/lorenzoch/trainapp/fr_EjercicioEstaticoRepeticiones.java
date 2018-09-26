package com.lorenzoch.trainapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import Data.EstadisticaEjercicioEstaticoRepeticiones;
import Data.EstaticoRepeticiones;

/**
 * Created by loren on 20/04/2018.
 */

public class fr_EjercicioEstaticoRepeticiones extends Fragment {
    private static final String TAG = "fr_EjercicioEstaticoRep";


    private IStartRutina iStartRutina;
    public EstadisticaEjercicioEstaticoRepeticiones stats;
    EstaticoRepeticiones ejercicio;

    //widgets
    TextView nroRepeticiones, nroSerieX, nroSerieY, nEjercicio;
    Button btnSum, btnInterrupt;
    ImageView imagen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            stats = (EstadisticaEjercicioEstaticoRepeticiones) bundle.getSerializable(getResources().getString(R.string.intent_message));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_ejercicio_estatico_repeticiones, container,false);

        nEjercicio = view.findViewById(R.id.txtejercicioFragmentEjercicioEstaticoRepeticiones);
        nroRepeticiones = view.findViewById(R.id.txtrepeticionesFragmentEjercicioEstaticoRepeticiones);
        nroSerieX = view.findViewById(R.id.txtseriescompletadasFragmentEjercicioEstaticoRepeticiones);
        nroSerieY = view.findViewById(R.id.txtseriestotalesFragmentEjercicioEstaticoRepeticiones);
        imagen = view.findViewById(R.id.imgFragmentEjercicioEstaticoRepeticiones);

        btnInterrupt = view.findViewById(R.id.btinterrumpirFragmentEjercicioEstaticoRepeticiones);
        btnSum = view.findViewById(R.id.btsumFragmentEjercicioEstaticoRepeticiones);

        ejercicio = stats.getEjercicio();

        nEjercicio.setText(ejercicio.getTipoEjercicio());

        nroRepeticiones.setText(Integer.toString(ejercicio.getNroRepeticiones()));
        nroSerieX.setText(stats.getSeriesCompletadas() +"");
        nroSerieY.setText("/"+ejercicio.getNroSeries());

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stats.isCompletado()){
                    iStartRutina.backToLista(stats);
                }
                if(stats.getSeriesCompletadas() < ejercicio.getNroSeries()){
                    stats.sumSerie();
                    nroSerieX.setText(stats.getSeriesCompletadas()+"");


                }
                if(stats.getSeriesCompletadas() == ejercicio.getNroSeries()){
                    btnSum.setText("FINISH");
                    stats.setCompletado(true);
                }
            }
        });
        btnInterrupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iStartRutina.backToLista(stats);
            }
        });

        loadImg();
        return view;
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
