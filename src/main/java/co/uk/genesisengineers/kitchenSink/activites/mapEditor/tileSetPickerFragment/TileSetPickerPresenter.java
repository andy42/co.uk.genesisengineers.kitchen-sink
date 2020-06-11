package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tileSetPickerFragment;

import co.uk.genesisengineers.kitchenSink.R;

import java.util.ArrayList;
import java.util.List;

public class TileSetPickerPresenter {

    private List<ListItem> list = new ArrayList<>();

    private  TileSetPickerView view;

    public void setView(TileSetPickerView view){
        this.view = view;
        list.clear();
        list.add(new ListItem(R.drawables.wallTiles_json, "Walls"));
        list.add(new ListItem(R.drawables.floorTiles_json, "Floors"));
        list.add(new ListItem(R.drawables.terrainTiles_json, "Terrain"));
        view.setItems(list);
    }

    public void onItemSelected(TileSetPickerPresenter.ListItem item) {
        view.openTilePickerFragmentForDrawable(item.drawableArrayId, item.name);
    }

    public class ListItem{
        public int drawableArrayId;
        public String name;

        public ListItem(int drawableArrayId, String name){
            this.drawableArrayId = drawableArrayId;
            this.name = name;
        }
    }
}
