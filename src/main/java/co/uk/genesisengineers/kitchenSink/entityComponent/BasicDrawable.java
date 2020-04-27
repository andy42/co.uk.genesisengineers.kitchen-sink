package co.uk.genesisengineers.kitchenSink.entityComponent;

import content.Context;
import content.entityPrototypeFactory.ComponentAttributes;
import entity.component.ComponentBase;
import util.Vector2Df;

public class BasicDrawable extends ComponentBase {

    private Vector2Df dimensions = new Vector2Df();
    private float rotation;
    private int drawableId;

    public BasicDrawable() {
        this.type = Type.BASIC_DRAWABLE;
    }

    public BasicDrawable(Vector2Df dimensions, int drawableId, float rotation){
        this();
        this.dimensions= dimensions;
        this.drawableId = drawableId;
        this.rotation = rotation;
    }

    public BasicDrawable (Context context, ComponentAttributes componentAttributes) {
        this();
        this.dimensions = componentAttributes.getVector2Df("dimensions", new Vector2Df(1,1));
        this.drawableId = context.getResources().getAssetId(componentAttributes.getStringValue("drawableId", ""));
        this.rotation = componentAttributes.getFloat("", 0f);
    }

    @Override
    public ComponentBase clone() {
        return new BasicDrawable(dimensions.copy(), drawableId, rotation);
    }

    public Vector2Df getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector2Df dimensions) {
        this.dimensions = dimensions;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
