package Data;

/**
 * Created by loren on 13/04/2018.
 */

public class DinamicoTiempo extends EjercicioDinamico{

    long tiempo;

    public DinamicoTiempo(long tiempo) {
        super();
        setTiempo(tiempo);
    }

    public DinamicoTiempo(int id, long tiempo) {
        super(id);
        setTiempo(tiempo);
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
