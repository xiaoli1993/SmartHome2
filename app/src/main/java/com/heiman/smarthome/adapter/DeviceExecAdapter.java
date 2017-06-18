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

import com.heiman.baselibrary.mode.scene.pLBean.sceneBean.ExecListBean;
import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * @Author : 张泽晋
 * @Time : 2017/6/9 17:03
 * @Description :
 * @Modify record :
 */

public class DeviceExecAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ExecListBean> results;
    private View.OnClickListener onClickListener;
    private boolean showDelBtn;

    public List<ExecListBean> getResults() {
        return results;
    }

    public void setShowDelBtn(boolean show) {
        showDelBtn = show;
        notifyDataSetChanged();
    }

    public boolean getShowDelBtn() {
        return showDelBtn;
    }

    public DeviceExecAdapter(Context context, List<ExecListBean> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene_devices_action, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DeviceExecAdapter.ItemViewHolder) {
            bind((DeviceExecAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(DeviceExecAdapter.ItemViewHolder holder, int position) {
        ExecListBean execListBean = results.get(position);
        holder.txtDeviceName.setText(execListBean.getDeviceId() + "");
        holder.txtDeviceAction.setText(execListBean.getExec_ep() + "");
        holder.txtDeviceName.setTag(execListBean);
        holder.imgDeviceIcon.setTag(execListBean);
        holder.txtDeviceAction.setTag(execListBean);
        holder.btnDel.setTag(execListBean);

        if (showDelBtn) {
            holder.btnDel.setVisibility(View.VISIBLE);
            holder.viewLeft.setVisibility(View.GONE);
        } else {
            holder.btnDel.setVisibility(View.GONE);
            holder.viewLeft.setVisibility(View.VISIBLE);
        }

        if (onClickListener != null) {
            holder.txtDeviceName.setOnClickListener(onClickListener);
            holder.imgDeviceIcon.setOnClickListener(onClickListener);
            holder.txtDeviceAction.setOnClickListener(onClickListener);
            holder.btnDel.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDeviceName;
        public ImageView imgDeviceIcon;
        public TextView txtDeviceAction;
        public ImageButton btnDel;
        public View viewLeft;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_device_name);
            imgDeviceIcon = (ImageView) itemView.findViewById(R.id.img_icon_device);
            txtDeviceAction = (TextView) itemView.findViewById(R.id.txt_device_action);
            btnDel = (ImageButton) itemView.findViewById(R.id.btn_del);
            viewLeft = itemView.findViewById(R.id.view_left);
        }
    }

}
