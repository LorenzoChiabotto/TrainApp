package ElementosModificados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lorenzoch.trainapp.Logica;
import com.lorenzoch.trainapp.R;

import java.util.List;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.Ejercicio;
import Data.EjercicioDinamico;
import Data.EjercicioEstatico;
import Data.EstadisticaEjercicio;
import Data.EstadisticaEjercicioDinamico;
import Data.EstadisticaEjercicioEstatico;
import Data.EstaticoRepeticiones;
import Data.EstaticoTiempo;

/**
 * Created by loren on 17/04/2018.
 */

public class EjerciciosDetalleListViewAdapter  extends ArrayAdapter {

    Context context;

    public EjerciciosDetalleListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public EjerciciosDetalleListViewAdapter(Context context, int resource, List items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        // Declare Variables
        TextView txtTitle;
        TextView txtDesc;

        TextView txtXX;
        TextView txBarra;
        TextView txtYY;

        LinearLayout backgroud;
        LayoutInflater inflater;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.row_titulo_detalle, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = itemView.findViewById(R.id.txtTituloRowTituloDetalle);
        txtDesc = itemView.findViewById(R.id.txtDetalleRowTituloDetalle);
        backgroud = itemView.findViewById(R.id.backTituloRowDetalle);

        txtXX = itemView.findViewById(R.id.txtXXRowTituloDetalle);
        txBarra = itemView.findViewById(R.id.txtBarraRowTituloDetalle);
        txtYY = itemView.findViewById(R.id.txtYYRowTituloDetalle);

        Ejercicio ejercicio;

        if(EstadisticaEjercicio.class.isAssignableFrom(getItem(posicion).getClass())){
            ejercicio = ((EstadisticaEjercicio)getItem(posicion)).getEjercicio();
            if(((EstadisticaEjercicio) getItem(posicion)).isCompletado()){
                backgroud.setBackgroundColor(context.getResources().getColor(R.color.primaryColor));
            }
            if (EstadisticaEjercicioEstatico.class.isAssignableFrom(getItem(posicion).getClass())){
                txtXX.setVisibility(View.VISIBLE); txtXX.setText(((EstadisticaEjercicioEstatico)getItem(posicion)).getSeriesCompletadas()+"");
                txBarra.setVisibility(View.VISIBLE);
                txtYY.setVisibility(View.VISIBLE); txtYY.setText(((EstadisticaEjercicioEstatico)getItem(posicion)).getEjercicio().getNroSeries()+"");
            }
        }else {
            ejercicio = (Ejercicio)getItem(posicion);
        }

            if((EjercicioDinamico.class).isAssignableFrom(ejercicio.getClass())){
                if(((EjercicioDinamico)ejercicio).getClass() == DinamicoDistancia.class){
                    txtTitle.setText(context.getResources().getString(R.string.dinamicoDistancia));
                    txtDesc.setText(context.getResources().getString(R.string.distancia) +
                            ": "+String.valueOf(((DinamicoDistancia)ejercicio).getDistKm())+"km");
                }else{
                    if(((EjercicioDinamico)ejercicio).getClass() == DinamicoTiempo.class){
                        txtTitle.setText(context.getResources().getString(R.string.dinamicoTiempo));
                        txtDesc.setText(context.getResources().getString(R.string.tiempo)+ ": "+(Logica.formatTime(((DinamicoTiempo) ejercicio).getTiempo())));
                    }
                    else {
                        txtTitle.setText(context.getResources().getString(R.string.dinamicoLibre));
                        txtDesc.setVisibility(View.INVISIBLE);
                    }
                }
            }else{
                if(((EjercicioEstatico)ejercicio).getClass() == EstaticoRepeticiones.class){
                    txtTitle.setText(((EjercicioEstatico)ejercicio).getTipoEjercicio());
                    txtDesc.setText(context.getResources().getString(R.string.repeticiones)+": "+((EstaticoRepeticiones)ejercicio).getNroRepeticiones() + "  --  " +
                            context.getResources().getString(R.string.series)+": "+ ((EstaticoRepeticiones)ejercicio).getNroSeries());
                }else{
                    txtTitle.setText(((EjercicioEstatico)ejercicio).getTipoEjercicio());
                    txtDesc.setText(context.getResources().getString(R.string.tiempo)+": "+Logica.formatTime(((EstaticoTiempo)ejercicio).getTiempo()) + "  --  " +
                            context.getResources().getString(R.string.series)+": "+((EstaticoTiempo)ejercicio).getNroSeries() +  " -- "+"\n"+
                            context.getResources().getString(R.string.descanso)+": "+Logica.formatTime(((EstaticoTiempo)ejercicio).getTiempoEntreSeries()));
                }
            }

        return itemView;
    }
}
