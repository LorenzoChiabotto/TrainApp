package com.lorenzoch.trainapp;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import Data.DinamicoDistancia;
import Data.DinamicoTiempo;
import Data.EjercicioDinamico;
import Data.EstadisticaEjercicioDinamico;
import ElementosModificados.MiCronometro;

/**
 * Created by loren on 20/04/2018.
 */

public class fr_EjercicioDinamico extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = "fr_EjercicioDinamico";
    private IStartRutina iStartRutina;

    private boolean mLocationPermissionsGranted = true;
    private static final int REQUEST_CHECK_SETTINGS_CODE = 0321;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private boolean mRequestingLocationUpdates;
    GoogleMap mMap;
    boolean paused;
    EstadisticaEjercicioDinamico stats;
    EjercicioDinamico ejercicio;

    MiCronometro tiempo;
    TextView txtPausa, txtInterrupt;
    FloatingActionButton btStart;
    TextView txtVmedia, txtDist;
    LinearLayout contain;

    long tiempomedio;
    float distancia, distanciaPausado, velocidad;
    boolean primerVuelta=true, fin=false;
    ArrayList<LatLng> recorrido;
    Location lastlocation;
    float[] distancebetween = new float[2];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_ejercicio_dinamico, container, false);


        Bundle bundle = this.getArguments();

        if(bundle != null){
            stats = (EstadisticaEjercicioDinamico) bundle.getSerializable(getResources().getString(R.string.intent_message));
            ejercicio = stats.getEjercicio();
        }

        txtVmedia = (TextView) view.findViewById(R.id.txtVelFragmentEjercicioDinamico);
        txtDist = (TextView) view.findViewById(R.id.txtDistFragmentEjercicioDinamico);

        txtPausa = (TextView) view.findViewById(R.id.txtPausaFragmentEjercicioDinamico);
        txtInterrupt = (TextView) view.findViewById(R.id.txtFinFragmentEjercicioDinamico);
        btStart = (FloatingActionButton) view.findViewById(R.id.btstartFragmentEjercicioDinamico);
        tiempo = view.findViewById(R.id.chronFragmentEjercicioDinamico);
        contain = (LinearLayout)view.findViewById(R.id.layoutContainer);
        contain.setVisibility(View.INVISIBLE);

        txtPausa.setVisibility(View.GONE);
        txtInterrupt.setVisibility(View.GONE);

        txtInterrupt.setOnClickListener(this);
        txtPausa.setOnClickListener(this);
        btStart.setOnClickListener(this);

        mLocationCallback = new LocationCallback();


        if((DinamicoTiempo.class).isAssignableFrom(ejercicio.getClass())) {
            tiempo.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (((DinamicoTiempo) ejercicio).getTiempo() <= SystemClock.elapsedRealtime() - tiempo.getBase()) {
                        fin = true;
                    }
                }
            });
        }

        initMap();

        setLocationCallback();


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.btstartFragmentEjercicioDinamico):{

                contain.setVisibility(View.VISIBLE);

                tiempo.setBase(SystemClock.elapsedRealtime());
                tiempomedio = 0;
                tiempo.start();

                mRequestingLocationUpdates = true;
                createLocationRequest();

                btStart.setVisibility(View.GONE);

                txtPausa.setVisibility(View.VISIBLE);
                txtInterrupt.setVisibility(View.VISIBLE);

                paused = false;
                break;
            }
            case(R.id.txtPausaFragmentEjercicioDinamico):{
                if(!paused){
                    paused = true;

                    tiempo.pause();

                    txtPausa.setText("REANUDAR");
                    txtPausa.setBackgroundColor(0xFF337A00);
                }
                else {
                    txtPausa.setText("PAUSA");
                    txtPausa.setBackgroundColor(0xB3DB9300);

                    tiempo.go();

                    paused = false;
                }
                break;
            }
            case(R.id.txtFinFragmentEjercicioDinamico):{
                finEjercicio();
                break;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map ready");
        mMap = googleMap;

        getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        btStart.setVisibility(View.VISIBLE);
    }
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapaFragmentEjercicioDinamico);
        mapFragment.getMapAsync(this);
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting device current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        try {
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: found Location");
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 20);
                    }
                    else{
                        Log.d(TAG, "onComplete: current location is null");
                        Toast.makeText(getActivity(), getResources().getString(R.string.errorgetlastrequestion), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }


    ////////////////////////////////////////////////////
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3500);
        mLocationRequest.setFastestInterval(2500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);


        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d(TAG, "TASK succes - can start locate requests");
                startLocationUpdates();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(TAG, "TASK Failure");
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {

                        Log.d(TAG, "TASK Failure - try");
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS_CODE);
                    } catch (IntentSender.SendIntentException sendEx) {
                        Log.d(TAG, "TASK Failure - catch");
                        // Ignore the error.
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        Log.d(TAG, "START LOCATION UPDATES");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    private void setLocationCallback(){
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult newlocation) {
                if (newlocation == null) {
                    return;
                }

                PolylineOptions options = new PolylineOptions();
                LatLng latLng;
                latLng = new LatLng(newlocation.getLastLocation().getLatitude(), newlocation.getLastLocation().getLongitude());



                if(primerVuelta){
                    primerVuelta = false;
                    lastlocation = newlocation.getLastLocation();
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    recorrido = new ArrayList<>();
                }
                recorrido.add(latLng);

                Log.d(TAG, "lat: " + newlocation.getLastLocation().getLatitude() + "  -  long: " + newlocation.getLastLocation().getLongitude());
                Log.d(TAG, distancia +" km");


                mMap.addPolyline(options.add(new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude()) ,latLng)
                        .width(15)
                        .startCap(new RoundCap())
                        .endCap(new RoundCap())
                        .color(getResources().getColor(R.color.secondaryColor)));
                if(!paused){
                    Location.distanceBetween(lastlocation.getLatitude(),lastlocation.getLongitude(), newlocation.getLastLocation().getLatitude(), newlocation.getLastLocation().getLongitude(),distancebetween);

                    distancia = distancia + distancebetween[0]/1000;
                    txtDist.setText(String.format( "%.2f km", distancia));

                    velocidad = distancia/((SystemClock.elapsedRealtime() - tiempo.getBase())*Float.valueOf("2.77778e-7"));
                    txtVmedia.setText(String.format( "%.2f km/h",velocidad));

                    Log.d(TAG, "tiempo: "+(SystemClock.elapsedRealtime() - tiempo.getBase())+"");
                    Log.d(TAG, "velocidad: "+velocidad);
                    Log.d(TAG, " distancia: "+distancia );

                    if ((DinamicoDistancia.class).isAssignableFrom(ejercicio.getClass())){
                        if(((DinamicoDistancia)ejercicio).getDistKm()< distancia){
                            fin = true;
                        }
                    }

                }else{
                    Location.distanceBetween(lastlocation.getLatitude(),lastlocation.getLongitude(), newlocation.getLastLocation().getLatitude(), newlocation.getLastLocation().getLongitude(),distancebetween);

                    distanciaPausado = distanciaPausado + distancebetween[0]/1000;
                }


                lastlocation = newlocation.getLastLocation();

                if(fin){
                    finEjercicio();
                }
            }
        };
    }

    private void finEjercicio(){
        tiempo.stop();
        mRequestingLocationUpdates = false;
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

        stats.setDescanso(tiempo.getTiempoPausado());
        stats.setTiempo((SystemClock.elapsedRealtime() - tiempo.getBase()));

        stats.setCompletado(true);

        if ((DinamicoDistancia.class).isAssignableFrom(ejercicio.getClass())){
            if(((DinamicoDistancia)ejercicio).getDistKm()> distancia){
                stats.setCompletado(false);
            }
        }else{
            if ((DinamicoTiempo.class).isAssignableFrom(ejercicio.getClass())){
                if(((DinamicoTiempo)ejercicio).getTiempo() > (SystemClock.elapsedRealtime() - tiempo.getBase())){
                    stats.setCompletado(false);
                }
            }
        }
        stats.setRecorrido(recorrido);
        stats.setVelocidadMedia(velocidad);
        stats.setDistancia(distancia);

        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

        iStartRutina.backToLista(stats);
    }

    ////////////////////////////////////////////////////


    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to" + latLng.longitude + " - " + latLng.latitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iStartRutina = (IStartRutina) getActivity();
    }
}
