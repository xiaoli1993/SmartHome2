package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.mode.Scene;
import com.heiman.smarthome.R;
import com.heiman.widget.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2015/11/24.
 */
public class MainSceneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private List<Scene> results;

    //get & set
    public List<Scene> getResults() {
        return results;
    }

    public MainSceneAdapter(Context context, List<Scene> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(results, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_scene, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            bind((ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    /////////////////////////////

    private void bind(ItemViewHolder holder, int position) {

        holder.textView.setText(results.get(position).getPL().getOID().getName());

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_txt);
            imageView = (ImageView) itemView.findViewById(R.id.image_icon);
        }
    }
}
