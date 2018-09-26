package com.lorenzoch.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import Data.Rutina;
import ElementosModificados.RutinasListViewAdapter;
import SQLite.SQLcommands;

/**
 * Created by loren on 13/04/2018.
 */

public class fr_ListaRutinas extends Fragment implements AdapterView.OnItemClickListener, IListaRutina{

    FloatingActionButton button;
    ListView lista;

    RutinasListViewAdapter data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_listaboton,container,false);

        lista = (ListView) view.findViewById(R.id.listLista);

        data = new RutinasListViewAdapter(getContext(),R.layout.row_titulo_detalle, SQLcommands.selectRutinas(getContext()));
        lista.setAdapter(data);



        button = (FloatingActionButton)view.findViewById(R.id.fabaddListaBoton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ac_NuevaRutina.class);
                startActivity(intent);
            }
        });

        lista.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        RutinasListViewAdapter data = new RutinasListViewAdapter(getContext(),R.layout.row_titulo_detalle, SQLcommands.selectRutinas(getContext()));
        lista.setAdapter(data);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Bundle bundle = new Bundle();
            DialogFragment newFragment = new Dialogs.dg_Delete();
            bundle.putInt(getResources().getString(R.string.intent_message_position), R.string.manana);
            String tag = getResources().getString(R.string.rutina);


            bundle.putSerializable(getResources().getString(R.string.intent_message), (Rutina)data.getItem(i));

            newFragment.setArguments(bundle);
            newFragment.setTargetFragment(this,2);
            newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void back() {
        onResume();
    }
}
