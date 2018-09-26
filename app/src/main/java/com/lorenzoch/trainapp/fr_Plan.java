package com.lorenzoch.trainapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import Data.PlanEntrenamiento;
import Data.Rutina;
import ElementosModificados.ArrayAdapterStringValues;
import SQLite.SQLcommands;

/**
 * Created by loren on 13/04/2018.
 */

public class fr_Plan extends Fragment implements View.OnClickListener, IBack {
    private static final String TAG = "fr_Plan";

    PlanEntrenamiento plan;
    ArrayList rutinas;
    ArrayAdapterStringValues data;
    int selected;



    Spinner spDias;
    TextView rutManana, rutTarde, rutNoche;
    CardView cardManana, cardTarde, cardNoche;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_plan, container,false);

        spDias = (Spinner)view.findViewById(R.id.spinnerdiasPlan);

        cardManana = (CardView)view.findViewById(R.id.cardmananaPlan);
        cardTarde = (CardView)view.findViewById(R.id.cardtardePlan);
        cardNoche = (CardView)view.findViewById(R.id.cardnochePlan);

        rutManana = (TextView) view.findViewById(R.id.txtrutinamananaPlan);
        rutTarde = (TextView)view.findViewById(R.id.txtrutinatardePlan);
        rutNoche = (TextView)view.findViewById(R.id.txtrutinanochePlan);


        data = new ArrayAdapterStringValues(view.getContext(),R.layout.row_simple, Logica.listResourcesDias());

        spDias.setAdapter(data);
        cardManana.setOnClickListener(this);
        cardTarde.setOnClickListener(this);
        cardNoche.setOnClickListener(this);


        plan = Logica.selectPlan(getContext());

        spDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = i;
                if(plan.getPlan().get(selected).getRutina().get(R.string.manana) != null){
                    rutManana.setText(plan.getPlan().get(selected).getRutina().get(R.string.manana).getNombre());
                }else{
                    rutManana.setText("");
                }
                if(plan.getPlan().get(selected).getRutina().get(R.string.tarde) != null){
                    rutTarde.setText(plan.getPlan().get(selected).getRutina().get(R.string.tarde).getNombre());
                }else{
                    rutTarde.setText("");
                }
                if(plan.getPlan().get(selected).getRutina().get(R.string.noche) != null){
                    rutNoche.setText(plan.getPlan().get(selected).getRutina().get(R.string.noche).getNombre());
                }else{
                    rutNoche.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rutinas = SQLcommands.selectRutinas(getContext());

        return view;
    }

    @Override
    public void onClick(View view) {
        DialogFragment newFragment = null;
        String tag="";

        Bundle bundle = new Bundle(2);

        //bundle.putInt(getResources().getString(R.string.intent_message_position), R.string.manana);
        newFragment = new Dialogs.dg_ListaRutinas();

        switch (view.getId()){
            case(R.id.cardmananaPlan):{
                bundle.putInt(getResources().getString(R.string.intent_message_position), R.string.manana);
                tag = getResources().getString(R.string.rutina);
                break;
            }
            case(R.id.cardtardePlan):{
                bundle.putInt(getResources().getString(R.string.intent_message_position), R.string.tarde);
                tag = getResources().getString(R.string.rutina);
                break;
            }
            case(R.id.cardnochePlan):{
                bundle.putInt(getResources().getString(R.string.intent_message_position), R.string.noche);
                tag = getResources().getString(R.string.rutina);
                break;
            }
        }
        bundle.putSerializable(getResources().getString(R.string.intent_message), rutinas);
        bundle.putInt(getResources().getString(R.string.intent_message_dia), spDias.getSelectedItemPosition());
        newFragment.setArguments(bundle);
        newFragment.setTargetFragment(this,2);
        newFragment.show(getFragmentManager(), tag);
    }

    @Override
    public void backPlan() {
        Log.d(TAG, "onResume: onresume");
        plan = Logica.selectPlan(getContext());

        if(plan.getPlan().get(selected).getRutina().get(R.string.manana) != null){
            rutManana.setText(plan.getPlan().get(selected).getRutina().get(R.string.manana).getNombre());
        }else{
            rutManana.setText("");
        }
        if(plan.getPlan().get(selected).getRutina().get(R.string.tarde) != null){
            rutTarde.setText(plan.getPlan().get(selected).getRutina().get(R.string.tarde).getNombre());
        }else{
            rutTarde.setText("");
        }
        if(plan.getPlan().get(selected).getRutina().get(R.string.noche) != null){
            rutNoche.setText(plan.getPlan().get(selected).getRutina().get(R.string.noche).getNombre());
        }else{
            rutNoche.setText("");
        }
    }
}
