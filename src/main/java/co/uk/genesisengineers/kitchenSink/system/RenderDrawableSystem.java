package co.uk.genesisengineers.kitchenSink.system;

import co.uk.genesisengineers.core.drawable.DrawableManager;
import co.uk.genesisengineers.core.entity.Entity;
import co.uk.genesisengineers.core.entity.EntityHandler;
import co.uk.genesisengineers.core.entity.component.ComponentBase;
import co.uk.genesisengineers.kitchenSink.entityComponent.BasicDrawable;
import co.uk.genesisengineers.kitchenSink.entityComponent.Position;
import co.uk.genesisengineers.core.system.SystemBase;
import co.uk.genesisengineers.core.visualisation.Visualisation;

import java.util.ArrayList;

public class RenderDrawableSystem extends SystemBase {
    private ArrayList<Entity> entityList = new ArrayList<Entity>();
    private DrawableManager drawableManager;

    public RenderDrawableSystem (DrawableManager drawableManager) {
        this.type = SystemBase.Type.RENDER_DRAWABLE;
        this.drawableManager= drawableManager;
    }

    @Override
    public void init (EntityHandler entityHandler) {
        for (Entity entity : entityHandler) {
            if (entity.hasComponent(ComponentBase.Type.POSITION) && entity.hasComponent(ComponentBase.Type.BASIC_DRAWABLE)) {
                entityList.add(entity);
            }
        }
    }

    @Override
    public void update () {
        Visualisation visualisation = Visualisation.getInstance();
        visualisation.useTextureProgram();


        Position position = null;
        BasicDrawable basicDrawable = null;
        for (Entity entity : entityList) {
            position = (Position) entity.getComponent(ComponentBase.Type.POSITION);
            basicDrawable = (BasicDrawable) entity.getComponent(ComponentBase.Type.BASIC_DRAWABLE);

            drawableManager.draw(basicDrawable.getDrawableId(), position.getCoordinates(), basicDrawable.getDimensions(), basicDrawable.getRotation());
        }
    }

}
