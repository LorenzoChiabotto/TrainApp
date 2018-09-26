package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lorenzoch.trainapp.INewRutina;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.fr_NuevaRutina;

import Data.EjercicioDinamico;

/**
 * Created by loren on 16/04/2018.
 */

public class dg_DinamicoLibre extends DialogFragment {
    private static final String TAG = "dg_DinamicoLibre";
    //Vars
    public INewRutina iNewRutina;

    //widgets
    TextView cancelar, aceptar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_dinamico_libre, container,false);

        cancelar = (TextView) view.findViewById(R.id.bttxtCancelDialogDinamicoLibre);
        aceptar = (TextView) view.findViewById(R.id.bttxtOkDialogDinamicoLibre);


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

                EjercicioDinamico dl = Logica.createEjercicio();
                iNewRutina.doFragmentTransaction(new fr_NuevaRutina(),getString(R.string.fr_nuevarutina),false,dl);
                getDialog().dismiss();
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