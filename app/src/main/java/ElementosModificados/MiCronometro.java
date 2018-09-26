package ElementosModificados;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

/**
 * Created by loren on 20/04/2018.
 */

public class MiCronometro extends Chronometer {
    long tiempoPausado = 0, tiempoPausadoTotal=0;

    public MiCronometro(Context context) {
        super(context);
    }

    public MiCronometro(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MiCronometro(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void pause(){
        tiempoPausado =(SystemClock.elapsedRealtime());
        this.stop();

    }

    public void go(){
        this.setBase(SystemClock.elapsedRealtime() - (tiempoPausado-this.getBase()));
        this.start();

        tiempoPausadoTotal = tiempoPausadoTotal + (SystemClock.elapsedRealtime()-tiempoPausado);
        tiempoPausado = 0;
    }

    public long getTiempoPausado() {
        return tiempoPausadoTotal;
    }
}
