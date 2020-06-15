package co.uk.genesisengineers.kitchenSink.activites.mapEditor.tilePickerFragment;

import co.uk.genesisengineers.kitchenSink.R;
import content.Context;
import drawable.DrawableManager;
import input.MotionEvent;
import org.lwjgl.system.CallbackI;
import ui.LayoutInflater;
import ui.view.ImageView;
import ui.view.RecyclerView;
import ui.view.View;
import ui.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TilePickerAdapter extends RecyclerView.Adapter<TilePickerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Item> itemList = new ArrayList<>();
    private int selcted = -1;
    private OnItemSelectedListener onItemSelectedListener = null;

    public TilePickerAdapter(Context context){
        this.context = context;
        this.layoutInflater = new LayoutInflater();
    }

    public void setItems(List<Item> itemList){
        this.itemList= itemList;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(context, R.layouts.item_tile_picker_xml, parent));
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void selectedItem(int index){
        if(selcted != -1){
            itemList.get(selcted).selected = false;
        }
        selcted = index;
        Item selectedItem = itemList.get(selcted);
        selectedItem.selected = true;

        if(onItemSelectedListener != null){
            onItemSelectedListener.OnItemSelected(selectedItem);
        }
        notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private ImageView selected;
        private int index = -1;

        public ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            selected = (ImageView)view.findViewById(R.id.selected);
            selected.setVisibility(View.INVISIBLE);
            view.setOnTouchListener((MotionEvent event, View root)->{
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    selectedItem(index);
                }
            });
        }

        public void bind(Item item, int index){
            imageView.setDrawableArray(DrawableManager.getInstance().getDrawable(item.textureArrayId), item.textureIndex);
            selected.setVisibility((item.selected) ? View.VISIBLE : View.INVISIBLE);
            this.index = index;
        }
    }

    public static class Item{
        int textureArrayId;
        int textureIndex;
        boolean selected = false;
        public Item(int textureArrayId, int textureIndex){
            this.textureArrayId = textureArrayId;
            this.textureIndex = textureIndex;
        }
    }

    public interface OnItemSelectedListener{
        void OnItemSelected(Item item);
    }
}
