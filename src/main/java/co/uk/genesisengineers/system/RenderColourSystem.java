package co.uk.genesisengineers.system;

import entity.Entity;
import entity.EntityHandler;
import entity.component.ComponentBase;
import co.uk.genesisengineers.entityComponent.BasicColouredSquare;
import co.uk.genesisengineers.entityComponent.Position;
import system.SystemBase;
import visualisation.Visualisation;

import java.util.ArrayList;

public class RenderColourSystem extends SystemBase {
    private ArrayList<Entity> entityList = new ArrayList<Entity>();

    public RenderColourSystem () {
        this.type = SystemBase.Type.RENDER_COLOUR;
    }

    @Override
    public void init (EntityHandler entityHandler) {
        for (Entity entity : entityHandler) {
            if (entity.hasComponent(ComponentBase.Type.POSITION) && entity.hasComponent(ComponentBase.Type.BASIC_COLOURED_SQUARE)) {
                entityList.add(entity);
            }
        }
    }

    @Override
    public void update () {
        Visualisation visualisation = Visualisation.getInstance();
        visualisation.useColourProgram();

        Position position = null;
        BasicColouredSquare basicSquare = null;

        for (Entity entity : entityList) {
            position = (Position) entity.getComponent(ComponentBase.Type.POSITION);
            basicSquare = (BasicColouredSquare) entity.getComponent(ComponentBase.Type.BASIC_COLOURED_SQUARE);

            visualisation.drawColouredSquare(basicSquare.getRgb(), position.getCoordinates(), basicSquare.getDimensions(), position.getRotation());
        }
    }
}
