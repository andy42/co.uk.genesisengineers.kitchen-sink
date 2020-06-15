package co.uk.genesisengineers.kitchenSink.activites.mapEditor;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.kitchenSink.events.ValueEvent;
import co.uk.genesisengineers.core.events.EventManager;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.activity.Activity;
import co.uk.genesisengineers.core.ui.view.View;

public class MapEditorToolsActivity extends Activity implements View.OnClickListener{

    public MapEditorToolsActivity(){
        super();
        this.open = true;
    }

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.activity_map_editor_tools_xml, null);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        this.setDragbar(R.id.dragBar);

        setupButton(R.id.paintButton);
        setupButton(R.id.fillButton);
        setupButton(R.id.eraserButton);

        findViewById(R.id.paintButton).focus();

    }

    private void setupButton(int buttonId){
        View button = findViewById(buttonId);
        button.setFocusable(true);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paintButton:
                EventManager.getInstance().addEvent(new ValueEvent(R.id.MapToolChangeEvent, R.id.paint));
                break;
            case R.id.fillButton:
                EventManager.getInstance().addEvent(new ValueEvent(R.id.MapToolChangeEvent, R.id.fill));
                break;
            case R.id.eraserButton:
                EventManager.getInstance().addEvent(new ValueEvent(R.id.MapToolChangeEvent, R.id.eraser));
                break;
        }
    }
}
