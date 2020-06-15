package co.uk.genesisengineers.kitchenSink.activites;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.activity.Activity;
import co.uk.genesisengineers.core.ui.view.*;
import co.uk.genesisengineers.core.util.Logger;

public class TestActivity extends Activity implements View.OnClickListener{

    protected Button button = null;

    public TestActivity () {
        super();
        this.open = true;
    }

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.basic_layout_xml, null);

        this.getResources();

        this.button = (Button) this.view.findViewById(R.id.button2);
        this.button.setOnClickListener(this);
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void onClick (View v) {
        if(v.getId() == this.button.getId()){
            Logger.info("onClick ");
        }
    }
}
