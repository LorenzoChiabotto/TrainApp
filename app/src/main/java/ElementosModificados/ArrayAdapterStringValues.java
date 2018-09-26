package ElementosModificados;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lorenzoch.trainapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 18/04/2018.
 */

public class ArrayAdapterStringValues extends ArrayAdapter {

    public ArrayAdapterStringValues(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ArrayAdapterStringValues(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }

    public View getView(int posicion, View view, ViewGroup parent) {
        // Declare Variables
        TextView txtTituloRow;
        LayoutInflater inflater;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.row_simple, parent, false);

        txtTituloRow = itemView.findViewById(R.id.txtTituloRowSimple);


        txtTituloRow.setText(getContext().getResources().getString((int)getItem(posicion)));



        return itemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getView(position,convertView,parent);
    }
}
