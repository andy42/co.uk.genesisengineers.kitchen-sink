package co.uk.genesisengineers.kitchenSink.recyclerViewTest;

import co.uk.genesisengineers.kitchenSink.R;
import content.Context;
import ui.view.*;

public class RecyclerViewActivityAdapter extends RecyclerView.Adapter {

    LayoutInflater layoutInflater = new LayoutInflater();
    Context context;

    public RecyclerViewActivityAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder (ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(context, R.layouts.item_view_xml ,null));
    }

    @Override
    public void bindViewHolder (RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount () {
        return 100;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView valueTextView;

        public ViewHolder(View view){
            super(view);
            titleTextView = (TextView)view.findViewById(R.id.title);
            valueTextView = (TextView)view.findViewById(R.id.value);
        }

        public void bind(int position){
            titleTextView.setText("title : "+position);
            valueTextView.setText("");
        }
    }
}
