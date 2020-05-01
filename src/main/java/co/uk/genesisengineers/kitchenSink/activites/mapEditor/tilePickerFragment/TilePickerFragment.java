package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import content.Context;
import drawable.DrawableManager;
import ui.activity.Fragment;
import ui.LayoutInflater;
import ui.view.GridLayoutManager;
import ui.view.RecyclerView;
import ui.view.View;
import ui.view.ViewGroup;

import java.util.List;

public class TilePickerFragment extends Fragment implements TilePickerView, TilePickerAdapter.OnItemSelectedListener{

    private RecyclerView recyclerView;
    private TilePickerAdapter adapter;
    private TilePickerPresenter presenter;

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        presenter = new TilePickerPresenter();
    }

    @Override
    public View onCreateView(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = new LayoutInflater();
        View view =  layoutInflater.inflate(getActivity(), R.layouts.fragment_tile_picker_xml, viewGroup);
        return view;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        recyclerView = (RecyclerView)view;

        recyclerView.setLayoutManager(new GridLayoutManager(GridLayoutManager.VERTICAL, 8));
        adapter = new TilePickerAdapter(getActivity());
        adapter.setOnItemSelectedListener(this);
        recyclerView.setAdapter(adapter);


        presenter.setView(this);
        presenter.onCreate(DrawableManager.getInstance());
    }

    @Override
    public void setItems(List<TilePickerAdapter.Item> items) {
        adapter.setItems(items);
    }

    @Override
    public void OnItemSelected(TilePickerAdapter.Item item) {
        if(presenter != null) {
            presenter.onItemSelcted(item);
        }
    }
}
