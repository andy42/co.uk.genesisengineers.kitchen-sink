package co.uk.genesisengineers.kitchenSink.activites.mapEditor.mapLayerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.core.content.Context;
import co.uk.genesisengineers.core.entity.Entity;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.activity.Fragment;
import co.uk.genesisengineers.core.ui.view.recyclerLayoutManager.LinearLayoutManager;
import co.uk.genesisengineers.core.ui.view.RecyclerView;
import co.uk.genesisengineers.core.ui.view.View;
import co.uk.genesisengineers.core.ui.view.ViewGroup;

import java.util.List;

public class MapLayerFragment extends Fragment implements MapLayerView, LayerAdapter.OnHideLayerClickListener, LayerAdapter.OnSelectLayerClickListener {

    private Entity mapEntity;
    private RecyclerView recyclerView;
    private LayerAdapter adapter;
    private MapLayerPresenter presenter;

    public MapLayerFragment(Entity mapEntity){
        this.mapEntity = mapEntity;
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        presenter = new MapLayerPresenter(mapEntity);
    }

    @Override
    public View onCreateView(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = new LayoutInflater();
        View view =  layoutInflater.inflate(getActivity(), R.layouts.fragment_map_layer_xml, viewGroup, false);
        return view;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        recyclerView = (RecyclerView)view.findViewById(R.id.layerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager());
        adapter= new LayerAdapter(getActivity());
        adapter.setOnHideLayerClickListener(this);
        adapter.setOnSelectLayerClickListener(this);
        recyclerView.setAdapter(adapter);
        presenter.setView(this);
    }

    @Override
    public void setLayers(List<MapLayerPresenter.Layer> layerList) {
        adapter.setItems(layerList);
    }

    @Override
    public void onHideLayerClick(int index) {
        presenter.onHideLayerClick(index);
    }

    @Override
    public void onSelectLayerClick(int index) {
        presenter.onSelectLayer(index);
    }
}
