package co.uk.genesisengineers.kitchenSink.activites.mapEditor.mapLayerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import co.uk.genesisengineers.core.content.Context;
import co.uk.genesisengineers.core.drawable.Drawable;
import co.uk.genesisengineers.core.drawable.DrawableManager;
import co.uk.genesisengineers.core.ui.LayoutInflater;
import co.uk.genesisengineers.core.ui.view.RecyclerView;
import co.uk.genesisengineers.core.ui.view.TextView;
import co.uk.genesisengineers.core.ui.view.View;
import co.uk.genesisengineers.core.ui.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater = new LayoutInflater();
    private Drawable iconHide;
    private Drawable iconShow;
    private Drawable backgroundSelected;
    private Drawable background;
    private OnHideLayerClickListener hideLayerClickListener;
    private OnSelectLayerClickListener selectLayerClickListener;

    private List<MapLayerPresenter.Layer> layerList = new ArrayList<>();

    public LayerAdapter(Context context){
        this.context = context;
        iconHide = DrawableManager.getInstance().getDrawable(R.textures.icon_hide_png);
        iconShow =DrawableManager.getInstance().getDrawable(R.textures.icon_hide_off_png);

        background = DrawableManager.getInstance().getDrawable(R.color.white);
        backgroundSelected = DrawableManager.getInstance().getDrawable(R.color.red);
    }

    public void setOnHideLayerClickListener(OnHideLayerClickListener listener){
        this.hideLayerClickListener = listener;
    }
    public void setOnSelectLayerClickListener(OnSelectLayerClickListener listener){
        this.selectLayerClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(context, R.layouts.item_map_layer_xml, parent, false));
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        holder.bind(position, layerList.get(position));
    }

    @Override
    public int getItemCount() {
        return layerList.size();
    }

    public void setItems(List<MapLayerPresenter.Layer> layerList){
        this.layerList = layerList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View container;
        private TextView nameView;
        private View hideView;
        private View selectView;
        private int index = -1;

        public ViewHolder(View view){
            super(view);
            container = view.findViewById(R.id.container);
            nameView = (TextView) view.findViewById(R.id.nameView);
            hideView = view.findViewById(R.id.hideView);
            hideView.setOnClickListener((View button) -> {
                hideLayerClickListener.onHideLayerClick(index);
            });
            selectView = view.findViewById(R.id.selectView);
            selectView.setOnClickListener((View button) -> {
                selectLayerClickListener.onSelectLayerClick(index);
            });
        }
        public void bind(int index, MapLayerPresenter.Layer layer){
            this.index = index;
            nameView.setText(layer.getName());
            hideView.setBackground( (layer.isVisable()) ? iconShow : iconHide);
            container.setBackground( (layer.isSelected()) ? backgroundSelected : background);
        }
    }

    public interface OnHideLayerClickListener{
        void onHideLayerClick(int index);
    }
    public interface OnSelectLayerClickListener{
        void onSelectLayerClick(int index);
    }
}
