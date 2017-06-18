/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.mode.link.plBean.linkBean.ExecBean;
import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/13 14:49
 * @Description : 
 * @Modify record :
 */

public class LinkExecAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ExecBean> results;
    private View.OnClickListener onClickListener;

    public List<ExecBean> getResults() {
        return results;
    }

    public LinkExecAdapter(Context context, List<ExecBean> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_exec, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new LinkExecAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LinkExecAdapter.ItemViewHolder) {
            bind((LinkExecAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(LinkExecAdapter.ItemViewHolder holder, int position) {
        ExecBean execBean = results.get(position);
        holder.txtExecDeviceName.setText(execBean.getDeviceId() + "");
        holder.txtExecDeviceAction.setText(execBean.getExec_ep() + "");
        holder.txtExecDeviceName.setTag(execBean);
        holder.imgExecDeviceIcon.setTag(execBean);
        holder.txtExecDeviceAction.setTag(execBean);
        holder.txtDelete.setTag(execBean);

        if (onClickListener != null) {
            holder.txtExecDeviceName.setOnClickListener(onClickListener);
            holder.imgExecDeviceIcon.setOnClickListener(onClickListener);
            holder.txtExecDeviceAction.setOnClickListener(onClickListener);
            holder.txtDelete.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtExecDeviceName;
        public ImageView imgExecDeviceIcon;
        public TextView txtExecDeviceAction;
        public TextView txtDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtExecDeviceName = (TextView) itemView.findViewById(R.id.txt_exec_device_name);
            imgExecDeviceIcon = (ImageView) itemView.findViewById(R.id.img_icon_exec_device);
            txtExecDeviceAction = (TextView) itemView.findViewById(R.id.txt_exec_device_action);
            txtDelete = (TextView) itemView.findViewById(R.id.txt_delete_exec_device);
        }
    }
}
