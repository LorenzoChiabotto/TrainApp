package Data;

/**
 * Created by loren on 13/04/2018.
 */

public class EjercicioEstatico extends Ejercicio{

    int nroSeries;

    String tipoEjercicio;

    public EjercicioEstatico(int id, int nroSeries, String tipoEjercicio) {
        super(id);
        setNroSeries(nroSeries);
        setTipoEjercicio(tipoEjercicio);
    }

    public EjercicioEstatico(int nroSeries, String tipoEjercicio) {
        setNroSeries(nroSeries);
        setTipoEjercicio(tipoEjercicio);
    }

    public int getNroSeries() {
        return nroSeries;
    }

    public void setNroSeries(int nroSeries) {
        this.nroSeries = nroSeries;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }
}