package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lorenzoch.trainapp.IBack;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.fr_Plan;

import java.util.ArrayList;

import Data.Rutina;
import ElementosModificados.RutinasListViewAdapter;

/**
 * Created by loren on 18/04/2018.
 */

public class dg_ListaRutinas extends DialogFragment{
    private static final String TAG = "dg_ListaRutinas";

    //vars
    ArrayList rutinas;
    int idMomento, dia;
    IBack backPlan;

    //widgets
    ListView lista;
    TextView momento, btEliminar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_put_rutina_plan,container,false);

        lista = (ListView) view.findViewById(R.id.listDialogPutRutina);
        momento = (TextView) view.findViewById(R.id.txttituloDialogPutRutina);
        btEliminar = (TextView) view.findViewById(R.id.bttxtDeleteDialogPutRutina);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            rutinas = (ArrayList) bundle.getSerializable(getResources().getString(R.string.intent_message));
            idMomento = bundle.getInt(getResources().getString(R.string.intent_message_position));
            dia = bundle.getInt(getResources().getString(R.string.intent_message_dia));
        }

        final RutinasListViewAdapter data = new RutinasListViewAdapter(getContext(),R.layout.row_titulo_detalle,rutinas);
        lista.setAdapter(data);

        momento.setText(getResources().getString((Logica.getDia(dia))) + " - " + getResources().getString(idMomento));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Rutina rutina = (Rutina)data.getItem(i);
                Logica.putRutinaPlan(getContext(), rutina, idMomento, dia);
                backPlan.backPlan();
                getDialog().dismiss();
            }
        });

        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logica.RemoveRutinaPlan(getContext(), idMomento, dia);
                backPlan.backPlan();
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backPlan =  (fr_Plan) getTargetFragment();
    }
}
