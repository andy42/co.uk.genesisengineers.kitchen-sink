package co.uk.genesisengineers.system;

import entity.Entity;
import entity.EntityHandler;
import entity.component.ComponentBase;
import co.uk.genesisengineers.entityComponent.MapSquare;
import co.uk.genesisengineers.entityComponent.Position;
import system.SystemBase;
import util.Vector2Df;
import visualisation.Visualisation;

import java.util.ArrayList;

public class MapRenderSystem extends SystemBase {

    private ArrayList<Entity> entityList = new ArrayList<>();

    public MapRenderSystem () {
        this.type = SystemBase.Type.MAP_RENDER;
    }

    @Override
    public void init (EntityHandler entityHandler) {
        for (Entity entity : entityHandler) {
            if (entity.hasComponent(ComponentBase.Type.POSITION) && entity.hasComponent(ComponentBase.Type.MAP_SQUARE)) {
                entityList.add(entity);
            }
        }
    }

    private void updateMap (Visualisation visualisation, Entity entity) {
        Position position = (Position) entity.getComponent(ComponentBase.Type.POSITION);
        MapSquare mapSquare = (MapSquare) entity.getComponent(ComponentBase.Type.MAP_SQUARE);

        Vector2Df mapPosition = position.getCoordinates().add(mapSquare.getHalfTileDimensions());
        Vector2Df tilePosition;


        MapSquare.MapTile mapTile;
        for (int x = 0; x < mapSquare.getBoardDimensions().x; x++) {
            for (int y = 0; y < mapSquare.getBoardDimensions().y; y++) {
                mapTile = mapSquare.getMapTile(x, y);
                if (mapTile == null)
                    continue;
                if(mapTile.drawableArray == null)
                    continue;

                tilePosition = mapSquare.getTileDimensions().multiply(new Vector2Df(x, y)).add(mapPosition);
                mapTile.drawableArray.draw(tilePosition, mapSquare.getTileDimensions(), 0, mapTile.drawableIndex);
            }
        }
    }

    @Override
    public void update () {
        Visualisation visualisation = Visualisation.getInstance();
        visualisation.useTextureProgram();
        for (Entity entity : entityList) {
            updateMap(visualisation, entity);
        }
    }
}
