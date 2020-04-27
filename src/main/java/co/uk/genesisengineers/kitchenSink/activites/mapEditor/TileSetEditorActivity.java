package co.uk.genesisengineers.kitchenSink.activites.mapEditor;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment.TilePickerFragment;
import ui.activity.Activity;
import ui.LayoutInflater;

public class TileSetEditorActivity extends Activity {

    public TileSetEditorActivity () {
        super();
        this.open = true;
    }

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.activity_tileset_editor_xml, null);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        fragmentManager.replace( R.id.container, new TilePickerFragment(),  true);
    }
}
