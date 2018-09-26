package Data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by loren on 19/04/2018.
 */

public class EstadisticaEjercicioDinamico extends EstadisticaEjercicio{

    EjercicioDinamico ejercicio;
    ArrayList<LatLongStr> recorrido = new ArrayList<>();
    float velocidadMedia;
    float distancia;
    long descanso;

    EstadisticaEjercicioDinamico(EjercicioDinamico e){
        super();
        setEjercicio(e);
    }
    public EstadisticaEjercicioDinamico(int id, float calorias, long tiempo, long descanso, EjercicioDinamico e, float vMedia, float distancia, boolean completado){
        super(id,calorias,tiempo, completado);
        setDescanso(descanso);
        setEjercicio(e);
        setVelocidadMedia(vMedia);
        setDistancia(distancia);
    }

    public EjercicioDinamico getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(EjercicioDinamico ejercicio) {
        this.ejercicio = ejercicio;
    }

    public ArrayList<LatLongStr> getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(ArrayList<LatLng> recorrido) {
        if(recorrido!= null){
            for (LatLng e:recorrido) {
                this.recorrido.add(new LatLongStr(e));
            }

        }
    }

    public float getVelocidadMedia() {
        return velocidadMedia;
    }

    public void setVelocidadMedia(float velocidadMedia) {
        this.velocidadMedia = velocidadMedia;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    @Override
    public long getTiempo() {
        return tiempo;
    }

    @Override
    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public long getDescanso() {
        return descanso;
    }

    public void setDescanso(long descanso) {
        this.descanso = descanso;
    }
}
