package Data;

/**
 * Created by loren on 13/04/2018.
 */

public class DinamicoDistancia extends EjercicioDinamico{

    float distKm;

    public DinamicoDistancia(float distKm) {
        super();
        setDistKm(distKm);
    }

    public DinamicoDistancia(int id, float distKm) {
        super(id);
        setDistKm(distKm);
    }

    public float getDistKm() {
        return distKm;
    }

    public void setDistKm(float distKm) {
        this.distKm = distKm;
    }
}

