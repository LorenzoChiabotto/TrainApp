package Data;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by loren on 29/05/2018.
 */

public class LatLongStr implements Serializable{
    String latitude;
    String longitud;

    public LatLongStr(LatLng latLng) {
        this.latitude = String.valueOf(latLng.latitude);
        this.longitud = String.valueOf(latLng.longitude);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
