package com.lorenzoch.trainapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Data.Ejercicio;
import ElementosModificados.EjerciciosDetalleListViewAdapter;

/**
 * Created by loren on 13/04/2018.
 */

public class fr_NuevaRutina extends Fragment{

    INewRutina iNewRutina;
    private static final String TAG = "fr_NuevaRutina";
    //Widgets
        FloatingActionButton btAddEjercicio;
        ListView listaEjercicios;
    //Vars

        EjerciciosDetalleListViewAdapter data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: running");

        View view = inflater.inflate(R.layout.fr_listaboton, container, false);

        btAddEjercicio = (FloatingActionButton)view.findViewById(R.id.fabaddListaBoton);
        listaEjercicios = (ListView)view.findViewById(R.id.listLista);


        btAddEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iNewRutina.doFragmentTransaction(new fr_ListaEjercicios(), getString(R.string.fr_listaejercicios),true,null);

            }
        });

        Bundle bundle = this.getArguments();
        if(bundle != null){
            data = new EjerciciosDetalleListViewAdapter(getContext(),R.layout.row_titulo_detalle, (ArrayList)bundle.getSerializable(getString(R.string.intent_message)));
            listaEjercicios.setAdapter(data);
        }

        listaEjercicios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                iNewRutina.removeEjercicio((Ejercicio)data.getItem(i));
                return false;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iNewRutina = (ac_NuevaRutina)getActivity();

    }
}
