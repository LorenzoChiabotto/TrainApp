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
import com.lorenzoch.trainapp.IRutinaHoy;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.fr_Plan;
import com.lorenzoch.trainapp.fr_RutinaHoy;

import java.util.ArrayList;

import Data.Rutina;
import ElementosModificados.RutinasListViewAdapter;
import SQLite.SQLcommands;

/**
 * Created by loren on 23/05/2018.
 */

public class dg_SelectRutina extends DialogFragment{
    private static final String TAG = "dg_SelectRutina";

    //vars
    ArrayList rutinas;
    IRutinaHoy iRutinaHoy;
    RutinasListViewAdapter data;
    //widgets
    ListView lista;
    TextView momento;
    TextView remover;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_put_rutina_plan,container,false);

        lista = (ListView) view.findViewById(R.id.listDialogPutRutina);
        momento = (TextView) view.findViewById(R.id.txttituloDialogPutRutina);
        momento.setText(getResources().getString(R.string.msgSelectRutina2));
        remover = (TextView) view.findViewById(R.id.bttxtDeleteDialogPutRutina);
        remover.setVisibility(View.GONE);
        data = new RutinasListViewAdapter(getContext(),R.layout.row_titulo_detalle, SQLcommands.selectRutinas(getContext()));
        lista.setAdapter(data);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Rutina rutina = (Rutina)data.getItem(i);
                iRutinaHoy.back(rutina);
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iRutinaHoy =  (fr_RutinaHoy) getTargetFragment();
    }
}
