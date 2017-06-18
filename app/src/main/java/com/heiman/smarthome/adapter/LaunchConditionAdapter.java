package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * @Author : 张泽晋
 * @Time : 2017/6/10 16:13
 * @Description :
 * @Modify record :
 */

public class LaunchConditionAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> results;
    private View.OnClickListener onClickListener;

    public List<String> getResults() {
        return results;
    }

    public LaunchConditionAdapter(Context context, List<String> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_condition, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new LaunchConditionAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LaunchConditionAdapter.ItemViewHolder) {
            bind((LaunchConditionAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(LaunchConditionAdapter.ItemViewHolder holder, int position) {
        String condition = results.get(position);
        holder.txtCondition.setText(condition);
        holder.txtCondition.setTag(condition);

        if (onClickListener != null) {
            holder.txtCondition.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCondition;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtCondition = (TextView) itemView.findViewById(R.id.txt_launch_condition);
        }
    }
}
