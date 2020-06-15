package co.uk.genesisengineers.kitchenSink.entityComponent;

import co.uk.genesisengineers.core.content.entityPrototypeFactory.ComponentAttributes;
import co.uk.genesisengineers.core.entity.component.ComponentBase;

public class KeyboardController extends ComponentBase {
    public KeyboardController () {
        this.type = Type.KEYBOARD_CONTROLLER;
    }

    public KeyboardController (ComponentAttributes componentAttributes) {
        this();
    }

    @Override
    public ComponentBase clone() {
        return new KeyboardController();
    }
}
