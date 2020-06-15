package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilesetPickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.core.content.Context;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.view.RecyclerView;
import co.uk.genesisengineers.core.ui.view.TextView;
import co.uk.genesisengineers.core.ui.view.View;
import co.uk.genesisengineers.core.ui.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TilesetPickerAdapter extends RecyclerView.Adapter<TilesetPickerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemSelectedListener listener;

    private List<TilesetPickerPresenter.ListItem> items = new ArrayList<>();

    public TilesetPickerAdapter(Context context){
        this.context = context;
        layoutInflater = new LayoutInflater();
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(context, R.layouts.item_title_xml, parent, false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<TilesetPickerPresenter.ListItem> items){
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleView;
        private TilesetPickerPresenter.ListItem item;
        public ViewHolder(View view){
            super(view);
            titleView = (TextView)view.findViewById(R.id.title);
            view.setOnClickListener((View button) ->
                listener.OnItemSelected(item)
            );
        }

        public void bind(TilesetPickerPresenter.ListItem item){
            this.item = item;
            titleView.setText(item.name);
        }
    }

    public interface OnItemSelectedListener{
        void OnItemSelected(TilesetPickerPresenter.ListItem item);
    }
}
