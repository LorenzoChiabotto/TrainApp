package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by loren on 13/04/2018.
 */

public class Rutina implements Serializable {

    int id;
    ArrayList<Ejercicio> rutina;
    String nombre;


    public Rutina(int id, ArrayList<Ejercicio> rutina, String nombre) {
        setId(id);
        setRutina(rutina);
        setNombre(nombre);
    }
    public Rutina(ArrayList<Ejercicio> rutina, String nombre) {
        setRutina(rutina);
        setNombre(nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Ejercicio> getRutina() {
        return rutina;
    }

    public void setRutina(ArrayList<Ejercicio> rutina) {
        this.rutina = rutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
