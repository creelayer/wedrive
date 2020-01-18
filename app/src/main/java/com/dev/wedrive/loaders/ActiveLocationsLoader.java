package com.dev.wedrive.loaders;

import androidx.core.util.Consumer;

import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.TypeInterface;
import com.dev.wedrive.service.LocationService;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ActiveLocationsLoader implements LoaderInterface {

    @Getter
    protected LocationCollection locationCollection;
    protected LocationService locationService;

    @Setter
    @Accessors(chain = true)
    private ApiLocation activeLocation;

    public ActiveLocationsLoader() {
        this.locationCollection = new LocationCollection();
        this.locationService = new LocationService();
    }

    public void load(final Consumer<LocationCollection> func) {
        run(func);
    }

    public void run(final Consumer<LocationCollection> func) {
        locationService.getActiveLocations((locations) -> {
            func.accept(locationCollection.put(locations));
            highlight();
        });

    }

    public ActiveLocationsLoader highlight() {
        for (Map.Entry<String, LocationAdapter> entry : locationCollection.entrySet()) {
            LocationAdapter adapter = entry.getValue();

            if (activeLocation == null) {
                adapter.getMarker().setAlpha(1);
                continue;
            }

            if (adapter.getUuid().equals(activeLocation.uuid)) {
                adapter.getMarker().setAlpha(1);
                continue;
            }

            if (adapter.getLocation().type.equals(TypeInterface.TYPE_DRIVER_LOCATION) && adapter.getLocation().routeUuid.equals(activeLocation.routeUuid)) {
                adapter.getMarker().setAlpha(1);
                continue;
            }

            adapter.getMarker().setAlpha((float) 0.4);
        }

        return this;
    }

}
