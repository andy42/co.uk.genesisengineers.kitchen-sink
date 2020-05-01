package co.uk.genesisengineers.kitchenSink.system;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.kitchenSink.entityComponent.MapSquare;
import co.uk.genesisengineers.kitchenSink.entityComponent.Position;
import co.uk.genesisengineers.kitchenSink.events.MapTileChangeEvent;
import drawable.Drawable;
import drawable.DrawableArray;
import drawable.DrawableManager;
import entity.Entity;
import entity.EntityHandler;
import entity.component.ComponentBase;
import events.Event;
import input.MotionEvent;
import system.EventListener;
import system.MotionEventListener;
import system.SystemBase;
import util.Logger;
import util.Vector2Df;

import java.util.ArrayList;

public class MapEditorSystem extends SystemBase implements EventListener, MotionEventListener {

    private DrawableArray drawableArray= null;
    private int drawableArrayIndex = -1;

    private ArrayList<Entity> entityList = new ArrayList<>();

    @Override
    public void init(EntityHandler entityHandler) {
        for (Entity entity : entityHandler) {
            if (entity.hasComponent(ComponentBase.Type.POSITION) && entity.hasComponent(ComponentBase.Type.MAP_SQUARE)) {
                entityList.add(entity);
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public boolean dispatchEvent(Event event) {
        if(event.getType() == R.id.MapTileChange && event instanceof  MapTileChangeEvent){
            MapTileChangeEvent mapTileChangeEvent = (MapTileChangeEvent) event;

            int drawableArrayId = mapTileChangeEvent.getDrawableArrayId();
            Drawable drawable =DrawableManager.getInstance().getDrawable(drawableArrayId);

            if(drawable instanceof DrawableArray){
                this.drawableArray = (DrawableArray)drawable;
                drawableArrayIndex = mapTileChangeEvent.getDrawableArrayIndex();
            }

        }
        return false;
    }

    private Vector2Df transformToMap(Vector2Df worldPosition, Entity entity){
        Position position = (Position) entity.getComponent(ComponentBase.Type.POSITION);
        MapSquare mapSquare = (MapSquare) entity.getComponent(ComponentBase.Type.MAP_SQUARE);

        Vector2Df relativeMapPosition = worldPosition.sub(position.getCoordinates());

        if(mapSquare.getBoardDimensions().x == 0 || mapSquare.getBoardDimensions().y == 0){
            String errorMessage = "MapEditorSystem can not divide by 0  BoardDimensions x|y == 0, entity.id:"+entity.getId();
            Logger.exception(new Exception(errorMessage), errorMessage);
            return new Vector2Df(-1, -1);
        }

        return relativeMapPosition.divide(mapSquare.getTileDimensions()).floor();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        MapSquare mapSquare;

        if(motionEvent.getAction() != MotionEvent.ACTION_UP) return false;

        for (Entity entity : entityList) {
            mapSquare = (MapSquare) entity.getComponent(ComponentBase.Type.MAP_SQUARE);

            if(drawableArray == null || drawableArrayIndex < 0){
                continue;
            }

            Vector2Df mapCoordinates = transformToMap(motionEvent.getPosition(), entity);
            if( mapCoordinates.x >= 0 &&
                mapCoordinates.y >= 0 &&
                mapCoordinates.x < mapSquare.getBoardDimensions().x &&
                mapCoordinates.y < mapSquare.getBoardDimensions().y) {

                    mapSquare.setTileTexture(mapCoordinates, drawableArray, drawableArrayIndex);
            }
        }
        return false;
    }
}
