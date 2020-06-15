package co.uk.genesisengineers.kitchenSink.entityInspector;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.core.ui.activity.Activity;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.view.TextView;

public class EntityInspectorActivity  extends Activity {

    private TextView nameTextView;

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.activity_entity_inspector_xml, null);

        nameTextView = (TextView)this.view.findViewById(R.id.name);
    }
}
