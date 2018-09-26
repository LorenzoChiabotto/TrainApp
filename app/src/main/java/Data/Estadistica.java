package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by loren on 19/04/2018.
 */

public class Estadistica implements Serializable{

    int id;
    ArrayList<EstadisticaEjercicio> stats = new ArrayList<EstadisticaEjercicio>();
    long tiempo, descanso;
    float calorias;
    Rutina rut;
    boolean completed;

    String fecha, hora;

    public Estadistica(int id, String fecha, String momento, Rutina rutina, boolean finished){
        setRut(rutina);
        this.id = id;
        setFecha(fecha);
        setHora(momento);
        this.completed = finished;
    }

    public Estadistica(Rutina rutina, String fecha, String momento){
        int i = 0;
        setRut(rutina);
        setFecha(fecha);
        setHora(momento);
    }

    public ArrayList<EstadisticaEjercicio> getStats() {
        return stats;
    }

    public void setStats(ArrayList<EstadisticaEjercicio> stats) {
        this.stats = stats;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public long getDescanso() {
        return descanso;
    }

    public void setDescanso(long descanso) {
        this.descanso = descanso;
    }

    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    public Rutina getRut() {
        return rut;
    }

    public void setRut(Rutina rut) {
        this.rut = rut;
        for (Ejercicio e : rut.getRutina()) {
            if((EjercicioDinamico.class).isAssignableFrom(e.getClass())){
                stats.add(new EstadisticaEjercicioDinamico((EjercicioDinamico) e));
            }else if ((EstaticoRepeticiones.class) == e.getClass()) {
                stats.add(new EstadisticaEjercicioEstaticoRepeticiones((EstaticoRepeticiones) e));
            }else if ((EstaticoTiempo.class) == e.getClass()) {
                stats.add(new EstadisticaEjercicioEstaticoTiempo((EstaticoTiempo) e));
            }
        }
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isFinished(){
        for (EstadisticaEjercicio e: stats) {
            if (!(e.isCompletado())){
                this.completed = false;
                return this.completed;
            }
        }
        this.completed = true;
        return this.completed;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void editEstadistica(int id, EstadisticaEjercicio stat){
        stats.set(id,stat);

    }

    public int getId() {
        return id;
    }
}
