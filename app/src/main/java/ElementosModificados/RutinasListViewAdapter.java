package ElementosModificados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lorenzoch.trainapp.R;

import java.util.List;

import Data.Rutina;

/**
 * Created by loren on 17/04/2018.
 */

public class RutinasListViewAdapter  extends ArrayAdapter {

    Context context;

    public RutinasListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public RutinasListViewAdapter(Context context, int resource, List items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        // Declare Variables
        TextView txtTitle;
        TextView txtDesc;
        LayoutInflater inflater;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.row_titulo_detalle, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = itemView.findViewById(R.id.txtTituloRowTituloDetalle);
        txtDesc = itemView.findViewById(R.id.txtDetalleRowTituloDetalle);


        txtTitle.setText(((Rutina)getItem(posicion)).getNombre());
        txtDesc.setText(context.getResources().getString(R.string.cantidadEjercicios)+": "+((Rutina)getItem(posicion)).getRutina().size());


        return itemView;
    }
}
