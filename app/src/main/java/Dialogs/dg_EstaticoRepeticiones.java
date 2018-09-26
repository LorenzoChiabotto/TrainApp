package Dialogs;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import Data.EstaticoRepeticiones;

/**
 * Created by loren on 16/04/2018.
 */

public class dg_EstaticoRepeticiones extends DialogFragment{
    private static final String TAG = "dg_EstaticoRepeticiones";

    //Vars
    public INewRutina iNewRutina;

    //widgets
    Spinner spinner;
    EditText series, repeticiones;
    TextView cancelar, aceptar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_estatico_repeticiones, container,false);

        cancelar = (TextView) view.findViewById(R.id.bttxtCancelDialogEstaticoRepeticiones);
        aceptar = (TextView) view.findViewById(R.id.bttxtOkDialogEstaticoRepeticiones);


        series = (EditText) view.findViewById(R.id.txtSeriesDialogEstaticoRepeticiones);
        repeticiones = (EditText) view.findViewById(R.id.txtRepeticionesDialogEstaticoRepeticiones);

        spinner = (Spinner) view.findViewById(R.id.spEjercicioEstaticoRepeticiones);
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

                if((series.getText().toString().equals("") || repeticiones.getText().toString().equals(""))){
                    Toast.makeText(getContext(),getResources().getString(R.string.msgCompletarCampos),Toast.LENGTH_SHORT).show();
                }
                else{
                    EstaticoRepeticiones er = Logica.createEjercicio(Integer.valueOf(series.getText().toString()),
                            Integer.valueOf(repeticiones.getText().toString()),
                            spinner.getSelectedItem().toString());

                    iNewRutina.doFragmentTransaction(new fr_NuevaRutina(),getString(R.string.fr_nuevarutina),false,er);
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
