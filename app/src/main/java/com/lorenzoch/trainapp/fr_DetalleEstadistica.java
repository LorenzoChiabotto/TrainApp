package com.lorenzoch.trainapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import Data.Estadistica;
import Data.EstadisticaEjercicioDinamico;
import ElementosModificados.EstadisticasCabeceraDetalleDetalleListViewAdapter;

/**
 * Created by loren on 25/04/2018.
 */

public class fr_DetalleEstadistica extends Fragment{
    private static final String TAG = "fr_DetalleEstadistica";

    IDetallesEstadistica iDetalleEstadistica;

    EstadisticaEjercicioDinamico ejercicioDinamico;

    ListView lista;
    Estadistica estadistica;
    EstadisticasCabeceraDetalleDetalleListViewAdapter data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_lista, container, false);


        lista = (ListView) view.findViewById(R.id.listLista);
        Bundle bundle = getArguments();

        if (bundle!= null){
            estadistica = (Estadistica) bundle.getSerializable(getResources().getString(R.string.intent_message));
            data = new EstadisticasCabeceraDetalleDetalleListViewAdapter(getContext(),R.layout.row_titulo_detalle_detalle, estadistica.getStats());
            lista.setAdapter(data);
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                if(data.getItem(i).getClass().isAssignableFrom(EstadisticaEjercicioDinamico.class)){
                    ejercicioDinamico = (EstadisticaEjercicioDinamico) data.getItem(i);
                    if(ejercicioDinamico.isCompletado()){
                        ejercicioDinamico.setRecorrido(Logica.selectRecorrido(getContext(), ejercicioDinamico));

                        iDetalleEstadistica.goToDetalle(ejercicioDinamico);
                    }else{
                        Toast.makeText(getContext(),getResources().getString(R.string.ejnorealizado),Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(),getResources().getString(R.string.msgNoHayDetalle),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        iDetalleEstadistica = (IDetallesEstadistica)getActivity();
    }
}
