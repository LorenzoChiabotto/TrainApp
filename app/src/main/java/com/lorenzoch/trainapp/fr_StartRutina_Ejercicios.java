package com.lorenzoch.trainapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import Data.Estadistica;
import Data.EstadisticaEjercicio;
import Data.EstadisticaEjercicioDinamico;
import Data.EstadisticaEjercicioEstaticoRepeticiones;
import Data.EstadisticaEjercicioEstaticoTiempo;
import ElementosModificados.EjerciciosDetalleListViewAdapter;

/**
 * Created by loren on 19/04/2018.
 */

public class fr_StartRutina_Ejercicios extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "fr_StartRutina_Ejercici";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    Estadistica estadistica;
    IStartRutina iStartRutina;
    EjerciciosDetalleListViewAdapter data;

    //widgets
    ListView listaEjercicios;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: running");

        View view = inflater.inflate(R.layout.fr_lista, container, false);

        listaEjercicios = (ListView)view.findViewById(R.id.listLista);

        Bundle bundle = getArguments();
        if (bundle!= null){
            estadistica = (Estadistica) bundle.getSerializable(getResources().getString(R.string.intent_message));
            data = new EjerciciosDetalleListViewAdapter(getContext(),R.layout.row_titulo_detalle, estadistica.getStats());
            listaEjercicios.setAdapter(data);
            bundle=null;
        }

        listaEjercicios.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iStartRutina = (ac_StartRutina)getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(!(((EstadisticaEjercicio)data.getItem(i)).isCompletado())) {
            if (data.getItem(i).getClass() == EstadisticaEjercicioEstaticoTiempo.class) {
                EstadisticaEjercicioEstaticoTiempo ej = (EstadisticaEjercicioEstaticoTiempo) data.getItem(i);
                iStartRutina.inflateFragment(R.string.fr_estaticotiempo, ej, i);

            } else if (data.getItem(i).getClass() == EstadisticaEjercicioEstaticoRepeticiones.class) {
                EstadisticaEjercicioEstaticoRepeticiones ej = (EstadisticaEjercicioEstaticoRepeticiones) data.getItem(i);
                iStartRutina.inflateFragment(R.string.fr_estaticorepeticiones, ej, i);

            } else if (data.getItem(i).getClass() == EstadisticaEjercicioDinamico.class) {
                if(isServicesOk()){
                    EstadisticaEjercicioDinamico ej = (EstadisticaEjercicioDinamico) data.getItem(i);
                    iStartRutina.inflateFragment(R.string.fr_dinamico, ej, i);
                }
            }
        }else {
            Toast.makeText(getActivity(),getResources().getString(R.string.msgejerciciocompletado), Toast.LENGTH_SHORT).show();
        }
    }



    public boolean isServicesOk(){
        Log.d(TAG, "isServicesOk: Checking googles services");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if(available == ConnectionResult.SUCCESS){
            //T0do esta correcto y puede hacer solicitudes de maps
            Log.d(TAG, "isServicesOk: ServicesGoogle is ok and working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Hay error pero se puede resolver
            Log.d(TAG, "isServicesOk: Error ocurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(getContext(), getResources().getString(R.string.errormaprequest), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        iStartRutina.reloadToolBar();
    }
}
