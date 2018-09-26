package Data;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by loren on 13/04/2018.
 */

public class PlanEntrenamiento {

    TreeMap<Integer,RutinaDia> plan;

    public PlanEntrenamiento(TreeMap<Integer,RutinaDia> plan) {
        setPlan(plan);
    }

    public TreeMap<Integer,RutinaDia> getPlan() {
        return plan;
    }

    public void setPlan(TreeMap<Integer,RutinaDia> plan) {
        this.plan = plan;
    }
}