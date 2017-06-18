/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.mode.link.plBean.linkBean.CheckListBean;
import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/13 14:04
 * @Description : 
 * @Modify record :
 */

public class CheckListAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CheckListBean> results;
    private View.OnClickListener onClickListener;

    public List<CheckListBean> getResults() {
        return results;
    }

    public CheckListAdapter(Context context, List<CheckListBean> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_condition, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new CheckListAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CheckListAdapter.ItemViewHolder) {
            bind((CheckListAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(CheckListAdapter.ItemViewHolder holder, int position) {
        CheckListBean checkListBean = results.get(position);
        holder.txtConditionName.setText(checkListBean.getDeviceId() + "");
        holder.txtConditionState.setText(checkListBean.getCheck_ep() + "");
        holder.txtConditionName.setTag(checkListBean);
        holder.imgConditionIcon.setTag(checkListBean);
        holder.txtConditionState.setTag(checkListBean);
        holder.txtDelete.setTag(checkListBean);

        if (onClickListener != null) {
            holder.txtConditionName.setOnClickListener(onClickListener);
            holder.imgConditionIcon.setOnClickListener(onClickListener);
            holder.txtConditionState.setOnClickListener(onClickListener);
            holder.txtDelete.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtConditionName;
        public ImageView imgConditionIcon;
        public TextView txtConditionState;
        public TextView txtDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtConditionName = (TextView) itemView.findViewById(R.id.txt_condition_name);
            imgConditionIcon = (ImageView) itemView.findViewById(R.id.img_icon_condition);
            txtConditionState = (TextView) itemView.findViewById(R.id.txt_condition_state);
            txtDelete = (TextView) itemView.findViewById(R.id.txt_delete_condition);
        }
    }

}
