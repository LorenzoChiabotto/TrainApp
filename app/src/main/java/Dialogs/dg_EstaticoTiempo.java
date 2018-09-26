package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lorenzoch.trainapp.INewRutina;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.fr_NuevaRutina;

import Data.EstaticoTiempo;

/**
 * Created by loren on 16/04/2018.
 */

public class dg_EstaticoTiempo extends DialogFragment {

    private static final String TAG = "dg_EstaticoTiempo";

    //Vars
    public INewRutina iNewRutina;

    //widgets
    Spinner spinner;
    EditText series, Mt, St, Md, Sd;
    TextView cancelar, aceptar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_estatico_tiempo, container,false);

        cancelar = (TextView) view.findViewById(R.id.bttxtCancelDialogEstaticoTiempo);
        aceptar = (TextView) view.findViewById(R.id.bttxtOkDialogEstaticoTiempo);


        series = (EditText) view.findViewById(R.id.txtSeriesDialogEstaticoTiempo);

        Mt = (EditText)view.findViewById(R.id.txtMTDialogEstaticoTiempo);
        St = (EditText)view.findViewById(R.id.txtSTDialogEstaticoTiempo);

        Md = (EditText)view.findViewById(R.id.txtMDDialogEstaticoTiempo);
        Sd = (EditText)view.findViewById(R.id.txtSDDialogEstaticoTiempo);

        spinner = (Spinner) view.findViewById(R.id.spEjercicioEstaticoTiempo);
        ArrayAdapter<String> data=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.arrayEjercicios));
        spinner.setAdapter(data);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Closing Dialog");
                getDialog().dismiss();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((series.getText().toString().equals("") || Mt.getText().toString().equals("") || St.getText().toString().equals("")
                        || Md.getText().toString().equals("") || Sd.getText().toString().equals(""))){
                    Toast.makeText(getContext(),getResources().getString(R.string.msgCompletarCampos),Toast.LENGTH_SHORT).show();
                }
                else {
                    EstaticoTiempo et = Logica.createEjercicio(Integer.valueOf(series.getText().toString()),
                            spinner.getSelectedItem().toString(),
                            Integer.valueOf(Mt.getText().toString()),
                            Integer.valueOf(St.getText().toString()),
                            Integer.valueOf(Md.getText().toString()),
                            Integer.valueOf(Sd.getText().toString()));

                    iNewRutina.doFragmentTransaction(new fr_NuevaRutina(),getString(R.string.fr_nuevarutina),false,et);
                    getDialog().dismiss();
                }

            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        iNewRutina = (INewRutina) getActivity();
    }
}
