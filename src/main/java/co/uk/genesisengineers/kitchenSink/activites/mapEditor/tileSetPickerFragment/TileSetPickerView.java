package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tileSetPickerFragment;

import java.util.List;

public interface TileSetPickerView {
    void setItems(List<TileSetPickerPresenter.ListItem> items);
    void openTilePickerFragmentForDrawable(int drawableId, String name);
}
