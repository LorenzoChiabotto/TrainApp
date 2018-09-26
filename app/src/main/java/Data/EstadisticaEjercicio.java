package Data;

import java.io.Serializable;

/**
 * Created by loren on 19/04/2018.
 */

public class EstadisticaEjercicio implements Serializable{

    int id;

    float calorias;
    long tiempo;
    boolean completado;


    EstadisticaEjercicio(int id, float calorias, long tiempo, boolean completado){
        this.id = id;
        setCalorias(calorias);
        setTiempo(tiempo);
        setCompletado(completado);
    }
    EstadisticaEjercicio(){
        setCompletado(false);
    }


    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public Ejercicio getEjercicio(){return null;};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
