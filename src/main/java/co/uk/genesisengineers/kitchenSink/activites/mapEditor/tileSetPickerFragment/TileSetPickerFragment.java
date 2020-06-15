package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tileSetPickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.OpenTilePickerFragmentForDrawableInterface;
import content.Context;
import ui.LayoutInflater;
import ui.activity.Activity;
import ui.activity.Fragment;
import ui.view.recyclerLayoutManager.LinearLayoutManager;
import ui.view.RecyclerView;
import ui.view.View;
import ui.view.ViewGroup;

import java.util.List;

public class TileSetPickerFragment extends Fragment implements TileSetPickerView, TileSetPickerAdapter.OnItemSelectedListener{

    private TileSetPickerPresenter presenter;
    private TileSetPickerAdapter adapter;
    private RecyclerView recyclerView;

    private OpenTilePickerFragmentForDrawableInterface openTilePickerFragmentForDrawableInterface;

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        presenter = new TileSetPickerPresenter();
    }

    @Override
    public View onCreateView(ViewGroup viewGroup) {
        LayoutInflater inflater = new LayoutInflater();
        return inflater.inflate(getActivity(), R.layouts.fragment_tile_set_picker_xml, viewGroup, false );
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);

        recyclerView = (RecyclerView)view;
        recyclerView.setLayoutManager(new LinearLayoutManager(LinearLayoutManager.VERTICAL));
        adapter = new TileSetPickerAdapter(getActivity());
        adapter.setOnItemSelectedListener(this);
        recyclerView.setAdapter(adapter);

        presenter.setView(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof TileSetPickerInterface){
            ((TileSetPickerInterface)activity).setTileSetPickerTitle("Tile sets");
        }

        if(activity instanceof OpenTilePickerFragmentForDrawableInterface == false){
            throw new RuntimeException("parent activity of TileSetPickerFragment most implement OpenTilePickerFragmentForDrawableInterface");
        }

        openTilePickerFragmentForDrawableInterface = (OpenTilePickerFragmentForDrawableInterface) activity;
    }

    @Override
    public void setItems(List<TileSetPickerPresenter.ListItem> items) {
        adapter.setItems(items);
    }

    @Override
    public void OnItemSelected(TileSetPickerPresenter.ListItem item) {
        presenter.onItemSelected(item);
    }

    @Override
    public void openTilePickerFragmentForDrawable(int drawableId, String name) {
        openTilePickerFragmentForDrawableInterface.onOpenTilePickerFragmentForDrawable(drawableId, name);
    }

    public interface TileSetPickerInterface{
        void setTileSetPickerTitle(String title);
    }
}
