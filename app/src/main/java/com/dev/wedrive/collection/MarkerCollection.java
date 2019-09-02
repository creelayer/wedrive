package com.dev.wedrive.collection;

import com.dev.wedrive.entity.ApiLocationInterface;
import com.dev.wedrive.entity.MMarker;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.Getter;


public class MarkerCollection {

    @Getter
    private Map<String, MMarker> markers;

    public MarkerCollection() {
        markers = new HashMap<String, MMarker>();
    }

    /**
     * @param marker
     * @return
     */
    public MMarker add(MMarker marker) {
        markers.put(marker.getUuid(), marker);
        return marker;
    }

    /**
     * @param uuid
     * @return
     */
    public MMarker get(String uuid) {
        return markers.get(uuid);
    }

    /**
     * @param marker
     * @return
     */
    public MMarker get(Marker marker) {
        return markers.get(marker.getTag().toString());
    }

    /**
     * @param location
     * @return
     */
    public MMarker get(ApiLocationInterface location) {
        return markers.get(location.getUuid());
    }


    /**
     *
     */
    public void clear() {
        for (Iterator<Map.Entry<String, MMarker>> it = markers.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, MMarker> entry = it.next();
            entry.getValue().getMarker().remove();
            it.remove();
        }
    }

    /**
     * @param marker
     */
    public void remove(MMarker marker) {
        markers.remove(marker.getUuid());
    }

    /**
     *
     * @param group
     */
    public void remove(String group) {
        for (Iterator<Map.Entry<String, MMarker>> it = markers.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, MMarker> entry = it.next();
            if (entry.getValue().getGroup() == group) {
                entry.getValue().getMarker().remove();
                it.remove();
            }
        }
    }

}
