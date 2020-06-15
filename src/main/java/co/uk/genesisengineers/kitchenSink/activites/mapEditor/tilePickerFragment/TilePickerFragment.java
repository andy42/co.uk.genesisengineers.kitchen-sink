package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.core.content.Context;
import co.uk.genesisengineers.core.drawable.DrawableManager;
import co.uk.genesisengineers.core.ui.activity.Activity;
import co.uk.genesisengineers.core.ui.activity.Fragment;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.view.recyclerLayoutManager.GridLayoutManager;
import co.uk.genesisengineers.core.ui.view.RecyclerView;
import co.uk.genesisengineers.core.ui.view.View;
import co.uk.genesisengineers.core.ui.view.ViewGroup;

import java.util.List;

public class TilePickerFragment extends Fragment implements TilePickerView, TilePickerAdapter.OnItemSelectedListener{

    private RecyclerView recyclerView;
    private TilePickerAdapter adapter;
    private TilePickerPresenter presenter;
    private int drawableArrayId;
    private String title;
    private TilePickerTitleInterface tilePickerTitleInterface = null;

    public TilePickerFragment(int drawableArrayId, String title){
        this.drawableArrayId = drawableArrayId;
        this.title = title;
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        presenter = new TilePickerPresenter(drawableArrayId);
    }

    @Override
    public View onCreateView(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = new LayoutInflater();
        View view =  layoutInflater.inflate(getActivity(), R.layouts.fragment_tile_picker_xml, viewGroup, false);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof TilePickerTitleInterface){
            ((TilePickerTitleInterface)activity).setTilePickerTitle(title);
        }
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

    public interface TilePickerTitleInterface{
        void setTilePickerTitle(String title);
    }
}
