package Data;

/**
 * Created by loren on 20/6/2018.
 */

public class EstadisticaEjercicioEstatico extends EstadisticaEjercicio {


    int seriesCompletadas = 0;

    EstadisticaEjercicioEstatico(int id, float calorias, long tiempo, boolean completado, int seriesCompletadas){
        super(id,calorias,tiempo, false);
        this.id = id;
        setCalorias(calorias);
        setTiempo(tiempo);
        setCompletado(completado);
        this.seriesCompletadas = seriesCompletadas;
    }
    EstadisticaEjercicioEstatico(){
        super();
        setCompletado(false);
    }



    public int getSeriesCompletadas() {
        return seriesCompletadas;
    }

    public void setSeriesCompletadas(int seriesCompletadas) {
        this.seriesCompletadas = seriesCompletadas;
    }

    public EjercicioEstatico getEjercicio(){
        return null;
    }


    public void sumSerie() {
        this.seriesCompletadas++;
    }
}
