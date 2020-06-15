package co.uk.genesisengineers.kitchenSink.events;


import co.uk.genesisengineers.kitchenSink.R;
import events.Event;

public class MapTileChangeEvent implements Event {
    private int drawableArrayId;
    private int drawableArrayIndex;

    public MapTileChangeEvent(int drawableArrayId, int drawableArrayIndex){
        this.drawableArrayId= drawableArrayId;
        this.drawableArrayIndex = drawableArrayIndex;
    }

    @Override
    public int getType() {
        return R.id.MapTileChange;
    }

    public int getDrawableArrayId() {
        return drawableArrayId;
    }

    public int getDrawableArrayIndex() {
        return drawableArrayIndex;
    }
}
