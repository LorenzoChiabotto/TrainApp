package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.lorenzoch.trainapp.IStartRutina;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.ac_StartRutina;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.EstadisticaEjercicioDinamico;

/**
 * Created by loren on 1/6/2018.
 */

public class dg_CompletDinamico extends DialogFragment {

    IStartRutina iStartRutina;
    EstadisticaEjercicioDinamico estadistica;

    float distancia;
    long tiempo;
    //widgets
    EditText dist, hh, mm, ss;
    TextView guardar, cancelar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_complet_dinamico, container, false );

        dist = (EditText) view.findViewById(R.id.txtDistanciaDialogCompletDinamico);
        hh = (EditText) view.findViewById(R.id.txtHTDialogCompletDinamico);
        mm = (EditText) view.findViewById(R.id.txtMTDialogCompletDinamico);
        ss = (EditText) view.findViewById(R.id.txtSTDialogCompletDinamico);

        guardar = (TextView) view.findViewById(R.id.bttxtOkDialogCompletDinamico);
        cancelar = (TextView) view.findViewById(R.id.bttxtCancelDialogCompletDinamico);


        Bundle bundle = getArguments();
        if (bundle!= null){
            estadistica = (EstadisticaEjercicioDinamico) bundle.getSerializable(getResources().getString(R.string.intent_message));

            if(estadistica.getEjercicio() instanceof DinamicoDistancia){
                dist.setEnabled(false);
                dist.setText(String.valueOf(((DinamicoDistancia) estadistica.getEjercicio()).getDistKm()) + " km");

                distancia =((DinamicoDistancia) estadistica.getEjercicio()).getDistKm();
            }else{
                if (estadistica.getEjercicio() instanceof DinamicoTiempo){
                    hh.setEnabled(false);
                    mm.setEnabled(false);
                    ss.setEnabled(false);
                    ss.setText(String.format(Locale.getDefault(),"%02d",TimeUnit.MILLISECONDS.toSeconds(((DinamicoTiempo) estadistica.getEjercicio()).getTiempo())
                                                                                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(((DinamicoTiempo) estadistica.getEjercicio()).getTiempo()))));
                    mm.setText(String.format(Locale.getDefault(),"%02d",TimeUnit.MILLISECONDS.toMinutes(((DinamicoTiempo) estadistica.getEjercicio()).getTiempo())
                                                                                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(((DinamicoTiempo) estadistica.getEjercicio()).getTiempo()))));
                    hh.setText(String.format(Locale.getDefault(),"%02d",TimeUnit.MILLISECONDS.toHours(((DinamicoTiempo) estadistica.getEjercicio()).getTiempo())));

                    tiempo = ((DinamicoTiempo) estadistica.getEjercicio()).getTiempo();
                }
            }
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((hh.getText().toString().equals("")) || (mm.getText().toString().equals(""))
                        || (ss.getText().toString().equals("")) || dist.getText().toString().equals(""))){
                    Toast.makeText(getContext(),getResources().getString(R.string.msgCompletarCampos),Toast.LENGTH_SHORT).show();
                }else{
                    if(estadistica.getEjercicio() instanceof DinamicoDistancia){
                       tiempo = Long.parseLong(hh.getText().toString())*3600000 +
                               Long.parseLong(mm.getText().toString())*60000 +
                               Long.parseLong(ss.getText().toString())*1000;
                    }else{
                        if(estadistica.getEjercicio() instanceof DinamicoTiempo){
                            distancia = Float.parseFloat(dist.getText().toString());
                        }else{
                            tiempo = Long.parseLong(hh.getText().toString())*3600000 +
                                    Long.parseLong(mm.getText().toString())*60000 +
                                    Long.parseLong(ss.getText().toString())*1000;

                            distancia = Float.parseFloat(dist.getText().toString());
                        }
                    }
                    estadistica.setDescanso(0);
                    estadistica.setTiempo(tiempo);
                    estadistica.setCompletado(true);
                    estadistica.setRecorrido(new ArrayList<LatLng>());
                    estadistica.setVelocidadMedia(distancia / (tiempo*Float.valueOf("2.77778e-7")));
                    estadistica.setDistancia(distancia);

                    iStartRutina.backToLista(estadistica);

                    dismiss();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iStartRutina = (ac_StartRutina) getActivity();
    }
}
