package co.uk.genesisengineers.kitchenSink.activites.recyclerViewTest;

import co.uk.genesisengineers.kitchenSink.R;
import ui.LayoutInflater;
import ui.activity.Activity;
import ui.view.*;
import util.Logger;
import util.Vector2Df;

public class RecyclerViewActivity  extends Activity implements RecyclerView.OnItemClickListener {

    private RecyclerView recyclerView = null;
    private RecyclerViewActivityAdapter adapter;

    public RecyclerViewActivity () {
        super();
        this.open = true;
    }

    @Override
    public void onCreate () {
        LayoutInflater layoutInflater = new LayoutInflater();
        this.view = layoutInflater.inflate(this, R.layouts.list_layout_xml,null);
        this.recyclerView = (RecyclerView)this.view;

        this.adapter = new RecyclerViewActivityAdapter(this);
        this.recyclerView.setLayoutManager(new GridLayoutManager(LinearLayoutManager.VERTICAL, 2));
        this.recyclerView.setAdapter(this.adapter);

        this.recyclerView.setOnItemClickListener(this);

        this.position = new Vector2Df(0,0);
    }

    @Override
    public void onItemClick (int index, View view) {
        Logger.info("onItemClick index = "+index);
    }
}
