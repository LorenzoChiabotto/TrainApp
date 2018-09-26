package com.lorenzoch.trainapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import Data.EstadisticaEjercicioDinamico;

/**
 * Created by loren on 30/04/2018.
 */

public class fr_DetalleEstadisticaDinamico extends Fragment implements OnMapReadyCallback{
    private static final String TAG = "fr_DetalleEstadisticaDi";
    //vars
    EstadisticaEjercicioDinamico estadistica;
    //widgets
    TextView txtVel, txtTiempo, txtDist, txtPause;
    GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_stat_dinamico_detalle, container, false);

        txtVel = (TextView)view.findViewById(R.id.txtVelFragmentStatDinamicoDetalle);
        txtTiempo = (TextView)view.findViewById(R.id.txtTiempoFragmentStatDinamicoDetalle);
        txtDist = (TextView)view.findViewById(R.id.txtDistanciaFragmentStatDinamicoDetalle);
        txtPause = (TextView)view.findViewById(R.id.txtTiempoPausaFragmentStatDinamicoDetalle);


        Bundle bundle = getArguments();

        if (bundle!= null){
            estadistica = (EstadisticaEjercicioDinamico) bundle.getSerializable(getResources().getString(R.string.intent_message));
            txtVel.setText(String.format("%.2f km/h",estadistica.getVelocidadMedia()));
            txtTiempo.setText(Logica.formatTime(estadistica.getTiempo()));
            txtDist.setText(String.format("%.2f km",estadistica.getDistancia()));
            txtPause.setText(Logica.formatTime(estadistica.getDescanso()));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapaFragmentStatDinamicoDetalle);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int i;
        if(mMap!=null && estadistica.getRecorrido().size() > 0){
            for (i = 0; i < estadistica.getRecorrido().size() - 1; i++) {

                LatLng src = new LatLng(Double.parseDouble(estadistica.getRecorrido().get(i).getLatitude()),
                                        Double.parseDouble(estadistica.getRecorrido().get(i).getLongitud()));
                LatLng dest =new LatLng(Double.parseDouble(estadistica.getRecorrido().get(i+1).getLatitude()),
                                        Double.parseDouble(estadistica.getRecorrido().get(i+1).getLongitud()));
                Log.d(TAG, "onMapReady: "+src.latitude + ": " + src.longitude);

                // mMap is the Map Object
                Polyline line = mMap.addPolyline(
                        new PolylineOptions().add(
                                new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude,dest.longitude)
                        )
                                .width(15)
                                .startCap(new RoundCap())
                                .endCap(new RoundCap())
                                .color(getResources().getColor(R.color.secondaryColor)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(estadistica.getRecorrido().get(0).getLatitude()),
                    Double.parseDouble(estadistica.getRecorrido().get(0).getLongitud())),20));

            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(estadistica.getRecorrido().get(0).getLatitude()),
                    Double.parseDouble(estadistica.getRecorrido().get(0).getLongitud()))));
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(estadistica.getRecorrido().get(i).getLatitude()),
                    Double.parseDouble(estadistica.getRecorrido().get(i).getLongitud()))));
        }
    }
}
