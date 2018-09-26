package Data;

import java.io.Serializable;

/**
 * Created by loren on 13/04/2018.
 */

public class Ejercicio implements Serializable {

    int id;

    Ejercicio(){};

    Ejercicio(int id) {
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


