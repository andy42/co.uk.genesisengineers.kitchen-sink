package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilesetPickerFragment;

import java.util.List;

public interface TilesetPickerView {
    void setItems(List<TilesetPickerPresenter.ListItem> items);
    void openTilePickerFragmentForDrawable(int drawableId, String name);
}
