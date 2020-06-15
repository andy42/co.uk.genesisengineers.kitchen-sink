package co.uk.genesisengineers.kitchenSink.activites.mapEditor;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.mapLayerFragment.MapLayerFragment;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment.TilePickerFragment;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.tileSetPickerFragment.TileSetPickerFragment;
import entity.Entity;
import ui.LayoutInflater;
import ui.activity.Activity;
import ui.view.TextView;
import ui.view.View;
import util.Logger;

public class MapEditorActivity extends Activity implements OpenTilePickerFragmentForDrawableInterface, TilePickerFragment.TilePickerTitleInterface, TileSetPickerFragment.TileSetPickerInterface{

    private Entity mapEntity;
    private View backButton;
    private TextView navbarTitle;

    public MapEditorActivity (Entity mapEntity) {
        super();
        this.open = true;
        this.mapEntity = mapEntity;
    }

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.activity_map_editor_xml, null);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        this.setDragbar(R.id.dragBar);

        this.backButton = findViewById(R.id.backButton);
        this.navbarTitle = (TextView)findViewById(R.id.navbarTitle);

        fragmentManager.replace( R.id.layerContainer, new MapLayerFragment(mapEntity),  true);
        Logger.info("test");
        //fragmentManager.replace( R.id.tilePickerContainer, new TilePickerFragment(),  true);
        fragmentManager.replace( R.id.tilePickerContainer, new TileSetPickerFragment(),  true);
        backButton.setVisibility(View.GONE);

        findViewById(R.id.backButton).setOnClickListener((View button) -> {
            fragmentManager.popBackStack(R.id.tilePickerContainer);
            backButton.setVisibility(View.GONE);
        });
    }

    @Override
    public void onOpenTilePickerFragmentForDrawable(int drawableId, String name) {
        fragmentManager.replace( R.id.tilePickerContainer, new TilePickerFragment(drawableId, name),  true);
        backButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTilePickerTitle(String title) {
        navbarTitle.setText(title);
    }

    @Override
    public void setTileSetPickerTitle(String title) {
        navbarTitle.setText(title);
    }
}
