package co.uk.genesisengineers.kitchenSink.entityComponent;

import co.uk.genesisengineers.core.content.entityPrototypeFactory.ComponentAttributes;
import co.uk.genesisengineers.core.drawable.DrawableArray;
import co.uk.genesisengineers.core.drawable.DrawableManager;
import co.uk.genesisengineers.core.entity.component.ComponentBase;
import co.uk.genesisengineers.core.util.Vector2Df;

import java.util.ArrayList;

public class MapSquare extends ComponentBase {

    private Vector2Df boardDimensions = new Vector2Df(1, 1);
    private Vector2Df tileDimensions = new Vector2Df(1, 1);
    private DrawableArray drawableArray;

    private ArrayList<MapLayer> layerArray = new ArrayList<>();

    public MapSquare () {
        this.type = Type.MAP_SQUARE;
    }

    public Vector2Df getBoardDimensions () {
        return this.boardDimensions;
    }

    public Vector2Df getTileDimensions () {
        return this.tileDimensions;
    }

    public Vector2Df getHalfTileDimensions () {
        return this.tileDimensions.multiply(new Vector2Df(0.5f, 0.5f));
    }

    public void setLayerVisibility(int layerIndex, boolean visable){
        if(layerIndex >= 0 && layerIndex < layerArray.size() ){
            layerArray.get(layerIndex).setVisable(visable);
        }
    }

    public boolean isMapLayerVisable(int layerIndex){
        if(layerIndex >= 0 && layerIndex < layerArray.size() ){
            return layerArray.get(layerIndex).isVisable();
        }
        return false;
    }

    public MapSquare (DrawableArray drawableArray, Vector2Df boardDimensions, Vector2Df tileDimensions) {
        this();

        this.boardDimensions = boardDimensions;
        this.tileDimensions = tileDimensions;
        this.drawableArray = drawableArray;

        init();
    }

    public MapSquare (int drawableArrayId, Vector2Df boardDimensions, Vector2Df tileDimensions) {
        this();
        DrawableArray drawableArray = (DrawableArray)DrawableManager.getInstance().getDrawable(drawableArrayId);

        this.boardDimensions = boardDimensions;
        this.tileDimensions = tileDimensions;
        this.drawableArray = drawableArray;
        init();
    }

    public MapSquare (ComponentAttributes componentAttributes) {
        this(
                componentAttributes.getIntValue("drawableArrayId", -1),
                componentAttributes.getVector2Df("boardDimensions", new Vector2Df(1,1)),
                componentAttributes.getVector2Df("tileDimensions", new Vector2Df(1,1))
        );
    }

    public void init(){
        MapLayer mapLayer = new MapLayer();
        mapLayer.setName("floor");
        mapLayer.init();
        for(int i=0; i < mapLayer.getTileArraySize(); i++){
            mapLayer.getMapTile(i).set(drawableArray, 0);
        }
        layerArray.add(mapLayer);

        mapLayer = new MapLayer();
        mapLayer.setName("walls");
        mapLayer.init();
        for(int i=0; i < mapLayer.getTileArraySize(); i++){
            mapLayer.getMapTile(i).set(null, 0);
        }
        layerArray.add(mapLayer);
    }

    @Override
    public ComponentBase clone() {
        return new MapSquare(drawableArray, boardDimensions.copy(),tileDimensions.copy());
    }

    public int getLayerCount(){
        return layerArray.size();
    }

    public MapLayer getLayer(int index){
        return layerArray.get(index);
    }

    public void setTileTexture (Vector2Df position, int layerIndex, DrawableArray drawableArray, int drawableIndex) {
        MapLayer mapLayer = layerArray.get(layerIndex);
        if(mapLayer == null) {
            return;
        }

        MapTile tile = mapLayer.getMapTile(position);
        if (tile == null) {
            return;
        }
        tile.drawableArray = drawableArray;
        tile.drawableIndex = drawableIndex;
    }

    public void setAllTileTextures ( int layerIndex, DrawableArray drawableArray, int drawableIndex) {
        MapLayer mapLayer = layerArray.get(layerIndex);
        if(mapLayer == null) {
            return;
        }
        mapLayer.setAllTileTextures(drawableArray, drawableIndex);
    }

    public MapTile getMapTile (int x, int y, int layerIndex) {
        MapLayer mapLayer = layerArray.get(layerIndex);
        if(mapLayer == null) {
            return null;
        }
        return mapLayer.getMapTile(x + y * (int) boardDimensions.x);
    }

    public MapTile getMapTile (Vector2Df position, int layerIndex) {
        return getMapTile((int)position.x, (int)position.y, layerIndex);
    }

    public static class MapTile {

        public DrawableArray drawableArray;
        public int drawableIndex = 0;

        public MapTile (DrawableArray drawableArray, int drawableIndex) {
            this.drawableArray = drawableArray;
            this.drawableIndex = drawableIndex;
        }

        public void set(DrawableArray drawableArray, int drawableIndex){
            this.drawableArray = drawableArray;
            this.drawableIndex = drawableIndex;
        }
    }

    public class MapLayer  {
        private boolean visable = true;
        private String name = "";
        private ArrayList<MapSquare.MapTile> mapArray = new ArrayList<>();

        public void init(){
            int tileCount = (int) (boardDimensions.x * boardDimensions.y);
            for (int i = 0; i < tileCount; i++) {
                mapArray.add(new MapTile(null, 0));
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MapTile getMapTile (int index) {
            return mapArray.get(index);
        }

        public MapTile getMapTile (int x, int y) {
            return mapArray.get(x + y * (int) boardDimensions.x);
        }

        public MapTile getMapTile (Vector2Df position) {
            return getMapTile((int)position.x, (int)position.y);
        }

        public int getTileArraySize(){
            return mapArray.size();
        }

        public void setTileTexture (Vector2Df position, DrawableArray drawableArray, int drawableIndex) {
            MapTile tile = getMapTile(position);
            if (tile == null) {
                return;
            }
            tile.drawableArray = drawableArray;
            tile.drawableIndex = drawableIndex;
        }

        public void setAllTileTextures(DrawableArray drawableArray, int drawableIndex){
            for (int i = 0; i < mapArray.size(); i++) {
                mapArray.get(i).set(drawableArray, drawableIndex);
            }
        }

        public boolean isVisable() {
            return visable;
        }

        public void setVisable(boolean visable) {
            this.visable = visable;
        }
    }
}
