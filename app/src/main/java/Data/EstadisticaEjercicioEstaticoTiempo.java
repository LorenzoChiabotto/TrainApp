package Data;

/**
 * Created by loren on 19/04/2018.
 */

public class EstadisticaEjercicioEstaticoTiempo extends EstadisticaEjercicioEstatico {

    EstaticoTiempo ejercicio;

    public EstadisticaEjercicioEstaticoTiempo (int id, float calorias, long tiempo, EstaticoTiempo e, int seriesCompletadas){
        super(id,calorias,tiempo, false, seriesCompletadas);
        setEjercicio(e);
        if(seriesCompletadas == e.getNroSeries()){
            super.setCompletado(true);
        }

    }
    EstadisticaEjercicioEstaticoTiempo (EstaticoTiempo e){
        super();
        setEjercicio(e);
    }

    public EstaticoTiempo getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(EstaticoTiempo ejercicio) {
        this.ejercicio = ejercicio;
    }

}