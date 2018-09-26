package Data;

/**
 * Created by loren on 13/04/2018.
 */

public class EstaticoTiempo extends EjercicioEstatico{

    long tiempo;
    long tiempoEntreSeries;

    public EstaticoTiempo(int id, int nroSeries, String tipoEjercicio, long tiempo, long tiempoEntreSeries) {
        super(id, nroSeries, tipoEjercicio);
        setTiempo(tiempo);
        setTiempoEntreSeries(tiempoEntreSeries);
    }

    public EstaticoTiempo(int nroSeries, String tipoEjercicio, long tiempo, long tiempoEntreSeries) {
        super(nroSeries, tipoEjercicio);
        setTiempo(tiempo);
        setTiempoEntreSeries(tiempoEntreSeries);
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public long getTiempoEntreSeries() {
        return tiempoEntreSeries;
    }

    public void setTiempoEntreSeries(long tiempoEntreSeries) {
        this.tiempoEntreSeries = tiempoEntreSeries;
    }
}
