package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * @Author : 张泽晋
 * @Time : 2017/6/10 20:13
 * @Description :
 * @Modify record :
 */

public class DeviceAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<XlinkDevice> results;
    private View.OnClickListener onClickListener;

    public List<XlinkDevice> getResults() {
        return results;
    }

    public DeviceAdapter(Context context, List<XlinkDevice> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DeviceAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DeviceAdapter.ItemViewHolder) {
            bind((DeviceAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(DeviceAdapter.ItemViewHolder holder, int position) {
        XlinkDevice device = results.get(position);
        holder.txtDeviceName.setText(device.getDeviceName());
        holder.txtDeviceName.setTag(device);
        holder.imgDeviceIcon.setTag(device);
        holder.imgRightIcon.setTag(device);
        int iconRes = SmartHomeUtils.typeToIcon(false, device.getDeviceType());
        if (iconRes != 0) {
            holder.imgDeviceIcon.setImageResource(iconRes);
        }

        if (onClickListener != null) {
            holder.imgDeviceIcon.setOnClickListener(onClickListener);
            holder.txtDeviceName.setOnClickListener(onClickListener);
            holder.imgRightIcon.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgDeviceIcon;
        public TextView txtDeviceName;
        public ImageView imgRightIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_device_name);
            imgDeviceIcon = (ImageView) itemView.findViewById(R.id.img_icon_device);
            imgRightIcon = (ImageView) itemView.findViewById(R.id.img_icon_right);
        }
    }

}
