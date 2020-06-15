package co.uk.genesisengineers.kitchenSink.activites.mapEditor.mapLayerFragment;

import co.uk.genesisengineers.kitchenSink.entityComponent.MapSquare;
import co.uk.genesisengineers.kitchenSink.events.MapLayerSelectChangeEvent;
import co.uk.genesisengineers.kitchenSink.events.MapLayerVisibilityChangeEvent;
import entity.Entity;
import entity.component.ComponentBase;
import events.EventManager;

import java.util.ArrayList;
import java.util.List;

public class MapLayerPresenter {
    private MapLayerView view;
    private List<Layer> layerList = new ArrayList<>();
    private Entity mapEntity;
    private int selectedIndex= 0;

    public MapLayerPresenter(Entity mapEntity){
        this.mapEntity = mapEntity;
        MapSquare mapSquare = (MapSquare)mapEntity.getComponent(ComponentBase.Type.MAP_SQUARE);

        for(int i= 0; i<mapSquare.getLayerCount(); i++){
            MapSquare.MapLayer layer = mapSquare.getLayer(i);
            layerList.add(new Layer(layer.getName(), layer.isVisable()));
        }
        selectedIndex=  mapSquare.getLayerCount() -1;
        selectLayer(selectedIndex);
        EventManager.getInstance().addEvent(new MapLayerSelectChangeEvent(selectedIndex));
    }

    public void setView(MapLayerView view){
        this.view = view;
        view.setLayers(layerList);
    }

    public void onHideLayerClick(int index) {
        Layer layer = layerList.get(index);
        if(layer == null) return;
        layer.visable = !layer.visable;
        view.setLayers(layerList);
        EventManager.getInstance().addEvent(new MapLayerVisibilityChangeEvent(index, layer.visable));
    }

    public void onSelectLayer(int index){
        selectLayer(index);
        view.setLayers(layerList);
        EventManager.getInstance().addEvent(new MapLayerSelectChangeEvent(index));
    }

    private void selectLayer(int index){
        for(int i=0; i< layerList.size(); i++){
            if(i == index){
                layerList.get(i).setSelected(true);
            } else {
                layerList.get(i).setSelected(false);
            }
        }
    }

    public class Layer{
        private String name;
        private boolean visable;
        private boolean selected;

        public Layer(String name, boolean visable){
            this.name = name;
            this.visable = visable;
            this.selected = false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isVisable() {
            return visable;
        }

        public void setVisable(boolean visable) {
            this.visable = visable;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
