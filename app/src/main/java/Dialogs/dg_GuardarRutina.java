package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lorenzoch.trainapp.INewRutina;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.ac_NuevaRutina;

import SQLite.SQLcommands;

/**
 * Created by loren on 17/04/2018.
 */

public class dg_GuardarRutina extends DialogFragment {
    private static final String TAG = "dg_GuardarRutina";

    EditText nombre;
    TextView btConfirmar, btCancelar;

    INewRutina iNewRutina;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dg_confirm_rutina, container,false);

        nombre = (EditText) view.findViewById(R.id.txtNombreRutinaDialogConfirmRutina);
        btCancelar = (TextView) view.findViewById(R.id.bttxtCancelDialogConfirmRutina);
        btConfirmar = (TextView) view.findViewById(R.id.bttxtOkDialogConfirmRutina);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(nombre.getText().toString().equals(""))){
                    //Logica.altaRutina(getContext(),nombre.getText().toString());

                    iNewRutina.guardarRutina(nombre.getText().toString());
                    dismiss();
                    getActivity().finish();
                } else{
                    Toast.makeText(getContext(),getResources().getString(R.string.msgCompletarCampos),Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        iNewRutina = (ac_NuevaRutina) getActivity();
    }
}
