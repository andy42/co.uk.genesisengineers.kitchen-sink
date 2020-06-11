package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tileSetPickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import content.Context;
import ui.LayoutInflater;
import ui.view.RecyclerView;
import ui.view.TextView;
import ui.view.View;
import ui.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TileSetPickerAdapter extends RecyclerView.Adapter<TileSetPickerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemSelectedListener listener;

    private List<TileSetPickerPresenter.ListItem> items = new ArrayList<>();

    public TileSetPickerAdapter(Context context){
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

    public void setItems(List<TileSetPickerPresenter.ListItem> items){
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleView;
        private TileSetPickerPresenter.ListItem item;
        public ViewHolder(View view){
            super(view);
            titleView = (TextView)view.findViewById(R.id.title);
            view.setOnClickListener((View button) ->
                listener.OnItemSelected(item)
            );
        }

        public void bind(TileSetPickerPresenter.ListItem item){
            this.item = item;
            titleView.setText(item.name);
        }
    }

    public interface OnItemSelectedListener{
        void OnItemSelected(TileSetPickerPresenter.ListItem item);
    }
}
