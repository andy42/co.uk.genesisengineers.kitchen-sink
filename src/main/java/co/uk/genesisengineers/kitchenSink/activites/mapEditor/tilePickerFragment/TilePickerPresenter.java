package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment;

import events.EventManager;
import co.uk.genesisengineers.kitchenSink.events.MapTileChangeEvent;
import co.uk.genesisengineers.kitchenSink.R;
import drawable.DrawableArray;
import drawable.DrawableManager;

import java.util.ArrayList;
import java.util.List;

public class TilePickerPresenter {

    private TilePickerView view;

    public void onCreate(DrawableManager drawableManager){
        List<TilePickerAdapter.Item> items = new ArrayList<>();
        DrawableArray drawableArray = (DrawableArray)drawableManager.getDrawable(R.drawables.wallTiles_json);
        for(int i=0; i< drawableArray.size(); i++){
            items.add(new TilePickerAdapter.Item(R.drawables.wallTiles_json, i));
        }
        view.setItems(items);
    }

    public void onItemSelcted(TilePickerAdapter.Item item){
        EventManager.getInstance().addEvent(new MapTileChangeEvent(item.textureArrayId, item.textureIndex));
    }

    public void setView(TilePickerView view){
        this.view = view;
    }
}
