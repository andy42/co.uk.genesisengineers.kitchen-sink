package co.uk.genesisengineers.kitchenSink.events;

import co.uk.genesisengineers.core.events.Event;

public class ValueEvent<T> implements Event {
    private int eventType;
    private T value;

    public ValueEvent(int eventType, T value){
        this.eventType = eventType;
        this.value = value;
    }

    @Override
    public int getType() {
        return eventType;
    }

    public T getValue(){
        return value;
    }
}
