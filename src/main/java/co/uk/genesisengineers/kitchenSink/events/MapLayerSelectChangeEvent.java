package co.uk.genesisengineers.kitchenSink.events;

import co.uk.genesisengineers.kitchenSink.R;
import events.Event;

public class MapLayerSelectChangeEvent implements Event {
    public int layerIndex;

    public MapLayerSelectChangeEvent(int layerIndex){
        this.layerIndex = layerIndex;
    }

    @Override
    public int getType() {
        return R.id.MapLayerChange;
    }
}
