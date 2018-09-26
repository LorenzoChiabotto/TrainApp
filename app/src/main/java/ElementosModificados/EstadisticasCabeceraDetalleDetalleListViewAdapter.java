package ElementosModificados;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;

import java.util.List;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.EjercicioDinamico;
import Data.EjercicioEstatico;
import Data.Estadistica;
import Data.EstadisticaEjercicio;
import Data.EstadisticaEjercicioDinamico;
import Data.EstadisticaEjercicioEstatico;
import Data.EstadisticaEjercicioEstaticoRepeticiones;
import Data.EstadisticaEjercicioEstaticoTiempo;
import Data.EstaticoTiempo;

/**
 * Created by loren on 25/04/2018.
 */

public class EstadisticasCabeceraDetalleDetalleListViewAdapter extends ArrayAdapter {

    Context context;

    public EstadisticasCabeceraDetalleDetalleListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public EstadisticasCabeceraDetalleDetalleListViewAdapter(Context context, int resource, List items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        // Declare Variables
        TextView txtDetalle1, txtDetalle2;
        TextView txtCabecera;
        LayoutInflater inflater;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.row_titulo_detalle_detalle, parent, false);

        // Locate the TextViews in listview_item.xml
        txtDetalle1 = itemView.findViewById(R.id.txtDetalle1RowTituloDetalleDetalle);
        txtDetalle2 = itemView.findViewById(R.id.txtDetalle2RowTituloDetalleDetalle);
        txtCabecera = itemView.findViewById(R.id.txtTituloRowTituloDetalleDetalle);

        EstadisticaEjercicio e = (EstadisticaEjercicio)getItem(posicion);

        if(EstadisticaEjercicioDinamico.class.isAssignableFrom(e.getClass())){
            if (DinamicoTiempo.class.isAssignableFrom(e.getEjercicio().getClass())){
                txtCabecera.setText(context.getResources().getString(R.string.dinamicoTiempo));
            }
            else{
                if (DinamicoDistancia.class.isAssignableFrom(e.getEjercicio().getClass())){
                    txtCabecera.setText(context.getResources().getString(R.string.dinamicoDistancia));
                }
                else{
                    txtCabecera.setText(context.getResources().getString(R.string.dinamicoLibre));
                }
            }
            txtDetalle1.setText((String.format("%.2f km",((EstadisticaEjercicioDinamico)e).getDistancia())) + " -- " + (Logica.formatTime(e.getTiempo())));

        }else {
            if(EstadisticaEjercicioEstaticoTiempo.class.isAssignableFrom(e.getClass())){
                txtCabecera.setText(((EjercicioEstatico)e.getEjercicio()).getTipoEjercicio() + " - " + context.getResources().getString(R.string.estaticoTiempo));
                txtDetalle1.setText(context.getResources().getString(R.string.tiempo)+": "+Logica.formatTime(((EstadisticaEjercicioEstaticoTiempo)e).getEjercicio().getTiempo())
                                    +" - " + context.getResources().getString(R.string.series)
                                    +": "+((EstadisticaEjercicioEstatico)e).getSeriesCompletadas()+"/" +((EjercicioEstatico)e.getEjercicio()).getNroSeries());
            }else{
                txtCabecera.setText(((EjercicioEstatico)e.getEjercicio()).getTipoEjercicio() + " - " + context.getResources().getString(R.string.estaticoRepeticiones));
                txtDetalle1.setText(context.getResources().getString(R.string.series)
                                    +": "+((EstadisticaEjercicioEstatico)e).getSeriesCompletadas()+"/" +((EjercicioEstatico)e.getEjercicio()).getNroSeries());
            }
        }

        if(e.isCompletado()){
            txtDetalle2.setText(context.getResources().getString(R.string.completo));
            txtDetalle2.setTextColor(Color.GREEN);
        }else{
            txtDetalle2.setText(context.getResources().getString(R.string.incompleto));
            txtDetalle2.setTextColor(Color.RED);
        }

        return itemView;
    }
}
