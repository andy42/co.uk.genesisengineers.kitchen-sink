package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import drawable.Drawable;
import drawable.DrawableArray;
import drawable.DrawableManager;
import ui.activity.Fragment;
import ui.LayoutInflater;
import ui.view.GridLayoutManager;
import ui.view.RecyclerView;
import ui.view.View;
import ui.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TilePickerFragment extends Fragment {

    private RecyclerView recyclerView;
    private TilePickerAdapter adapter;

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

        recyclerView.setLayoutManager(new GridLayoutManager(GridLayoutManager.VERTICAL, 4));
        adapter = new TilePickerAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        List<TilePickerAdapter.Item> items = new ArrayList<>();
        DrawableArray drawableArray = (DrawableArray)DrawableManager.getInstance().getDrawable(R.drawables.tiles_top_left_json);
        for(int i=0; i< drawableArray.size(); i++){
            items.add(new TilePickerAdapter.Item(R.drawables.tiles_json, i));
        }
        adapter.setItems(items);

    }
}
