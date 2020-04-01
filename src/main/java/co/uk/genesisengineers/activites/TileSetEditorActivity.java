package co.uk.genesisengineers.activites;

import co.uk.genesisengineers.kitchenSink.R;
import ui.activity.Activity;
import ui.view.Button;
import ui.view.LayoutInflater;

public class TileSetEditorActivity extends Activity {

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.activity_tileset_editor_xml, null);
    }
}
