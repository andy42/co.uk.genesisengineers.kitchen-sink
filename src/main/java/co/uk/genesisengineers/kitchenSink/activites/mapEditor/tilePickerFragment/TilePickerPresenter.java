package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment;

import co.uk.genesisengineers.core.events.EventManager;
import co.uk.genesisengineers.kitchenSink.events.MapTileChangeEvent;
import co.uk.genesisengineers.core.drawable.DrawableArray;
import co.uk.genesisengineers.core.drawable.DrawableManager;

import java.util.ArrayList;
import java.util.List;

public class TilePickerPresenter {

    private TilePickerView view;
    private int drawableArrayId;

    public TilePickerPresenter(int drawableArrayId){
        this.drawableArrayId = drawableArrayId;
    }

    public void onCreate(DrawableManager drawableManager){
        List<TilePickerAdapter.Item> items = new ArrayList<>();
        DrawableArray drawableArray = (DrawableArray)drawableManager.getDrawable(drawableArrayId);
        for(int i=0; i< drawableArray.size(); i++){
            items.add(new TilePickerAdapter.Item(drawableArrayId, i));
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
