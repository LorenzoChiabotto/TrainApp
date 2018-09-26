package com.lorenzoch.trainapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import SQLite.SQLcommands;

/**
 * Created by loren on 13/04/2018.
 */

public class fr_ListaEjercicios extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "fr_ListaEjercicios";

    //Widgets
    ListView lista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_lista, container, false);


        lista = (ListView) view.findViewById(R.id.listLista);
        ArrayAdapter<String> data=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.arrayTipoEjercicios));
        lista.setAdapter(data);

        lista.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        DialogFragment newFragment = null;
        String tag;

        switch (i){
            case (0):{
                newFragment = new Dialogs.dg_EstaticoRepeticiones();
                tag = getResources().getString(R.string.estaticoRepeticiones);
                newFragment.show(getFragmentManager(), tag);
                break;
            }
            case (1):{
                newFragment = new Dialogs.dg_EstaticoTiempo();
                tag = getResources().getString(R.string.estaticoTiempo);
                newFragment.show(getFragmentManager(), tag);
                break;
            }
            case (2):{
                newFragment = new Dialogs.dg_DinamicoLibre();
                tag = getResources().getString(R.string.dinamicoLibre);
                newFragment.show(getFragmentManager(), tag);
                break;
            }
            case (3):{
                newFragment = new Dialogs.dg_DinamicoTiempo();
                tag = getResources().getString(R.string.dinamicoTiempo);
                newFragment.show(getFragmentManager(), tag);
                break;
            }
            case (4):{
                newFragment = new Dialogs.dg_DinamicoDistancia();
                tag = getResources().getString(R.string.dinamicoDistancia);
                newFragment.show(getFragmentManager(), tag);
                break;
            }
        }
    }

}
