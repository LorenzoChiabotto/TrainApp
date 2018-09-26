package Data;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Created by loren on 13/04/2018.
 */

public class RutinaDia{

    TreeMap<Integer,Rutina> rutina;

    public RutinaDia(TreeMap<Integer,Rutina> rutina) {
        setRutina(rutina);
    }

    public TreeMap<Integer,Rutina> getRutina() {
        return rutina;
    }

    public void setRutina(TreeMap<Integer,Rutina> rutina) {
        this.rutina = rutina;
    }
}