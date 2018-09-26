package Data;

/**
 * Created by loren on 13/04/2018.
 */

public class EstaticoRepeticiones extends EjercicioEstatico{
    int nroRepeticiones;


    public EstaticoRepeticiones(int id, int nroSeries, String tipoEjercicio, int repeticiones) {
        super(id, nroSeries, tipoEjercicio);
        setNroRepeticiones(repeticiones);
    }

    public EstaticoRepeticiones(int nroSeries, String tipoEjercicio, int repeticiones) {
        super(nroSeries, tipoEjercicio);
        setNroRepeticiones(repeticiones);
    }

    public int getNroRepeticiones() {
        return nroRepeticiones;
    }

    public void setNroRepeticiones(int repeticiones) {
        this.nroRepeticiones = repeticiones;
    }

}
