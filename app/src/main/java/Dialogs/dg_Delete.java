package Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lorenzoch.trainapp.IListaRutina;
import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;
import com.lorenzoch.trainapp.fr_ListaRutinas;

import java.util.ArrayList;

import Data.Rutina;
import ElementosModificados.EjerciciosDetalleListViewAdapter;

/**
 * Created by loren on 23/05/2018.
 */

public class dg_Delete extends DialogFragment {
    private static final String TAG = "dg_Delete";

    //vars
    Rutina rutina;
    IListaRutina iListaRutina;

    //widgets
    ListView lista;
    TextView bteliminar, nombre;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dg_delete, container, false);

        lista = (ListView) view.findViewById(R.id.listDialogDelete);
        bteliminar = (TextView) view.findViewById(R.id.bttxtDeleteDialogDelete);
        nombre = (TextView) view.findViewById(R.id.txttituloDialogDelete);


        Bundle bundle = this.getArguments();
        if(bundle != null){
            rutina = (Rutina) bundle.getSerializable(getResources().getString(R.string.intent_message));
        }

        final EjerciciosDetalleListViewAdapter data = new EjerciciosDetalleListViewAdapter(getContext(),R.layout.row_titulo_detalle,rutina.getRutina());
        lista.setAdapter(data);

        nombre.setText(rutina.getNombre());

        bteliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logica.bajaRutina(getContext(), rutina);
                dismiss();
                iListaRutina.back();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iListaRutina = (fr_ListaRutinas)getTargetFragment();
    }
}
