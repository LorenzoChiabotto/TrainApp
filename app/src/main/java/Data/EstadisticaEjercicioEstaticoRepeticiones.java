package Data;

/**
 * Created by loren on 19/04/2018.
 */

public class EstadisticaEjercicioEstaticoRepeticiones extends EstadisticaEjercicioEstatico {

    EstaticoRepeticiones ejercicio;

    public EstadisticaEjercicioEstaticoRepeticiones (int id, float calorias, long tiempo, EstaticoRepeticiones e, int seriesCompletadas){
        super(id,calorias,tiempo, false, seriesCompletadas);
        setEjercicio(e);
        if(seriesCompletadas == e.getNroSeries()){
            super.setCompletado(true);
        }

    }
    EstadisticaEjercicioEstaticoRepeticiones (EstaticoRepeticiones e){
        super();
        setEjercicio(e);
    }

    public EstaticoRepeticiones getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(EstaticoRepeticiones ejercicio) {
        this.ejercicio = ejercicio;
    }

}
