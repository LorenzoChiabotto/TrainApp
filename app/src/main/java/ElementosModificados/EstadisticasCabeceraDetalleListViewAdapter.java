package ElementosModificados;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lorenzoch.trainapp.R;

import java.util.List;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.Ejercicio;
import Data.EjercicioDinamico;
import Data.EjercicioEstatico;
import Data.Estadistica;
import Data.EstadisticaEjercicio;
import Data.EstaticoRepeticiones;
import Data.EstaticoTiempo;

/**
 * Created by loren on 24/04/2018.
 */

public class EstadisticasCabeceraDetalleListViewAdapter extends ArrayAdapter {

    Context context;

    public EstadisticasCabeceraDetalleListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public EstadisticasCabeceraDetalleListViewAdapter(Context context, int resource, List items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        // Declare Variables
        TextView txtTitle;
        TextView txtCabecera;
        LayoutInflater inflater;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.row_cabecera_titulo, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = itemView.findViewById(R.id.txtTituloRowCabeceraTitulo);
        txtCabecera = itemView.findViewById(R.id.txtCabeceraRowCabeceraTitulo);

        Estadistica e = (Estadistica)getItem(posicion);

        txtCabecera.setText(e.getFecha() + " - " + e.getHora());

        if (((Estadistica) getItem(posicion)).isCompleted()){
            txtTitle.setTextColor(Color.GREEN);
        }
        else{
            txtTitle.setTextColor(Color.RED);
        }
        txtTitle.setText(e.getRut().getNombre());

        return itemView;
    }
}
