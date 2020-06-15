package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilesetPickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.OpenTilePickerFragmentForDrawableInterface;
import co.uk.genesisengineers.core.content.Context;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.activity.Activity;
import co.uk.genesisengineers.core.ui.activity.Fragment;
import co.uk.genesisengineers.core.ui.view.recyclerLayoutManager.LinearLayoutManager;
import co.uk.genesisengineers.core.ui.view.RecyclerView;
import co.uk.genesisengineers.core.ui.view.View;
import co.uk.genesisengineers.core.ui.view.ViewGroup;

import java.util.List;

public class TilesetPickerFragment extends Fragment implements TilesetPickerView, TilesetPickerAdapter.OnItemSelectedListener{

    private TilesetPickerPresenter presenter;
    private TilesetPickerAdapter adapter;
    private RecyclerView recyclerView;

    private OpenTilePickerFragmentForDrawableInterface openTilePickerFragmentForDrawableInterface;

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        presenter = new TilesetPickerPresenter();
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
        adapter = new TilesetPickerAdapter(getActivity());
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
            throw new RuntimeException("parent activity of TilesetPickerFragment most implement OpenTilePickerFragmentForDrawableInterface");
        }

        openTilePickerFragmentForDrawableInterface = (OpenTilePickerFragmentForDrawableInterface) activity;
    }

    @Override
    public void setItems(List<TilesetPickerPresenter.ListItem> items) {
        adapter.setItems(items);
    }

    @Override
    public void OnItemSelected(TilesetPickerPresenter.ListItem item) {
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
