package com.lorenzoch.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import Data.Estadistica;
import Data.Rutina;
import ElementosModificados.EjerciciosDetalleListViewAdapter;

/**
 * Created by loren on 16/04/2018.
 */

public class fr_RutinaHoy extends Fragment implements  IRutinaHoy, View.OnClickListener{
    //vars
    Rutina rutina;

    //wodgets
    ListView lista;
    TextView textDia, textRutina;
    FloatingActionButton start;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_rutina_hoy, container,false);

        textDia = view.findViewById(R.id.diamomentoRutinaHoy);
        textRutina = view.findViewById(R.id.rutinaRutinaHoy);
        start = (FloatingActionButton)view.findViewById(R.id.startRutinaHoy);

        rutina = Logica.selectRutinaDelDia(getContext());

        textDia.setText(getResources().getString(Logica.getDia(Logica.diaSemana()))+"  -  " + getResources().getString(Logica.momentoDia()));

        textRutina.setOnClickListener(this);
        lista = (ListView) view.findViewById(R.id.listaRutinaHoy);

        start.setOnClickListener(this);
        if(rutina == null){
            return view;
        }

        textRutina.setText(rutina.getNombre());


        EjerciciosDetalleListViewAdapter data= new EjerciciosDetalleListViewAdapter(view.getContext(),R.layout.row_titulo_detalle,rutina.getRutina());

        lista.setAdapter(data);




        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case(R.id.startRutinaHoy):{
                if(rutina != null){
                    Intent intent = new Intent(view.getContext(), ac_StartRutina.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getResources().getString(R.string.intent_message_position), new Estadistica(rutina, getResources().getString(Logica.getDia(Logica.diaSemana()))+" - " + Logica.fechaHoy(),getResources().getString(Logica.momentoDia())));
                    intent.putExtra(getResources().getString(R.string.intent_message), bundle);
                    startActivity(intent);
                }
                break;
            }
            case(R.id.rutinaRutinaHoy):{
                DialogFragment newFragment = new Dialogs.dg_SelectRutina();
                newFragment.setTargetFragment(this,2);
                newFragment.show(getFragmentManager(), "");
                break;
            }
        }
    }


    @Override
    public void back(Rutina rut) {
        rutina = rut;

        textRutina.setText(rutina.getNombre());

        EjerciciosDetalleListViewAdapter data= new EjerciciosDetalleListViewAdapter(getContext(),R.layout.row_titulo_detalle,rutina.getRutina());

        lista.setAdapter(data);
    }
}
