package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lorenzoch.trainapp.INewRutina;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.fr_NuevaRutina;

import Data.DinamicoDistancia;

/**
 * Created by loren on 16/04/2018.
 */

public class dg_DinamicoDistancia extends DialogFragment{
    private static final String TAG = "dg_DinamicoDistancia";

    //Vars
    public INewRutina iNewRutina;

    //widgets
    TextView cancelar, aceptar;
    EditText distancia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_dinamico_distancia, container,false);

        cancelar = (TextView) view.findViewById(R.id.bttxtCancelDialogDinamicoDistancia);
        aceptar = (TextView) view.findViewById(R.id.bttxtOkDialogDinamicoDistancia);

        distancia = (EditText) view.findViewById(R.id.txtDistanciaDialogDinamicoDistancia);

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

                if(!(distancia.getText().toString().equals(""))){
                    DinamicoDistancia dn = Logica.createEjercicio(Float.valueOf(distancia.getText().toString()));

                    iNewRutina.doFragmentTransaction(new fr_NuevaRutina(),getString(R.string.fr_nuevarutina),false,dn);
                    getDialog().dismiss();
                }
                else{
                    Toast.makeText(getContext(),getResources().getString(R.string.msgCompletarCampos),Toast.LENGTH_SHORT).show();
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
