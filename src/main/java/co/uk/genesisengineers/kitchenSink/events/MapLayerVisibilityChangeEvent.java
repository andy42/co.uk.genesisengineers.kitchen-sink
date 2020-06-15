package co.uk.genesisengineers.kitchenSink.events;

import co.uk.genesisengineers.kitchenSink.R;
import events.Event;

public class MapLayerVisibilityChangeEvent implements Event {

    private int layerIndex;
    private boolean visibility;

    public MapLayerVisibilityChangeEvent(int layerIndex, boolean visibility){
        this.layerIndex = layerIndex;
        this.visibility = visibility;
    }

    public int getLayerIndex() {
        return layerIndex;
    }

    public boolean isVisibility() {
        return visibility;
    }

    @Override
    public int getType() {
        return R.id.MapLayerVisibilityChange;
    }
}
