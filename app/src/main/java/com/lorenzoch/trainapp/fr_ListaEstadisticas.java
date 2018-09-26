package com.lorenzoch.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import Data.Estadistica;
import ElementosModificados.EstadisticasCabeceraDetalleListViewAdapter;
import SQLite.SQLcommands;

/**
 * Created by loren on 13/04/2018.
 */

public class fr_ListaEstadisticas extends Fragment {

    ListView lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_lista,container,false);

        lista = (ListView) view.findViewById(R.id.listLista);


        final EstadisticasCabeceraDetalleListViewAdapter data = new EstadisticasCabeceraDetalleListViewAdapter(getContext(),R.layout.row_cabecera_titulo, SQLcommands.selectResumenEstadisticas(getContext()));
        lista.setAdapter(data);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Estadistica stat = (Estadistica) data.getItem(i);
                stat.setStats(Logica.selectDetalleEstadistica(getContext(),stat.getId(),stat.getRut().getRutina()));

                Intent intent = new Intent(view.getContext(), ac_DetallesEstadistica.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(getResources().getString(R.string.intent_message_position), stat);
                intent.putExtra(getResources().getString(R.string.intent_message), bundle);
                startActivity(intent);
            }
        });


        return view;
    }
}