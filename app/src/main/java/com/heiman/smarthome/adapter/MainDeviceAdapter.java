package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.listener.OnItemClickListener;
import com.heiman.smarthome.modle.ListMain;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/9 14:26
 * @Description :
 * @Modify record :
 */

public class MainDeviceAdapter extends SwipeMenuAdapter<MainDeviceAdapter.DefaultViewHolder> {
    private Context context;
    private int src;
    private List<ListMain> results;
    private OnItemClickListener mOnItemClickListener;

    public MainDeviceAdapter(int src, List<ListMain> results) {
        this.results = results;
        this.src = src;
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(src, parent, false);
        return itemView;
    }

    @Override
    public MainDeviceAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainDeviceAdapter.DefaultViewHolder holder, int position) {
        MyApplication.getLogger().i("issub"+results.get(position).isSub()+"mac:"+results.get(position).getDeviceMac()+"type:"+results.get(position).getDeviceType());
        if (results.get(position).isSub()) {
            SubDevice subDevice = SubDeviceManage.getInstance().getDevice(results.get(position).getDeviceMac(), results.get(position).getSubMac());
            holder.textView.setText(subDevice.getDeviceName());

            switch (subDevice.getDeviceType()) {
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                    holder.imageView.setImageResource(R.drawable.device_light);
                    if (subDevice.getRgbOnoff() == 1) {
                        holder.textState.setText(context.getResources().getString(R.string.Open));
                    } else {
                        holder.textState.setText(context.getResources().getString(R.string.Close));
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                    holder.imageView.setImageResource(R.drawable.device_door);
                    switch (subDevice.getDeviceOnoff()) {
                        case 5:
                            holder.textState.setText(context.getResources().getString(R.string.Open));
                            break;
                        case 4:
                            holder.textState.setText(context.getResources().getString(R.string.Close));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Open));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Close));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                    holder.imageView.setImageResource(R.drawable.device_water);
                    switch (subDevice.getDeviceOnoff()) {
                        case 5:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 4:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                    holder.imageView.setImageResource(R.drawable.device_pir);
                    switch (subDevice.getDeviceOnoff()) {
                        case 5:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 4:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                    holder.imageView.setImageResource(R.drawable.device_smoke);
                    switch (subDevice.getDeviceOnoff()) {
                        case 5:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 4:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                    holder.imageView.setImageResource(R.drawable.device_thp);
                    holder.textState.setText(subDevice.getTemp() + "° " + subDevice.getHumidity() + "%");
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                    holder.imageView.setImageResource(R.drawable.device_gas);
                    switch (subDevice.getDeviceOnoff()) {
                        case 5:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 4:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                    holder.imageView.setImageResource(R.drawable.device_co);
                    switch (subDevice.getDeviceOnoff()) {
                        case 5:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 4:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Clear));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                    holder.imageView.setImageResource(R.drawable.device_sos);
                    switch (subDevice.getDeviceOnoff()) {
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                    holder.imageView.setImageResource(R.drawable.device_sw);
                    switch (subDevice.getDeviceOnoff()) {
                        case 3:
                            holder.textState.setText(context.getResources().getString(R.string.Alarm));
                            break;
                        case 2:
                            holder.textState.setText(context.getResources().getString(R.string.GoHome));
                            break;
                        case 1:
                            holder.textState.setText(context.getResources().getString(R.string.OutAway));
                            break;
                        case 0:
                            holder.textState.setText(context.getResources().getString(R.string.Disarm));
                            break;
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                    holder.imageView.setImageResource(R.drawable.device_plug);
                    if (subDevice.getRelayOnoff() == 1) {
                        if (subDevice.getUsbOnoff() == 1) {
                            holder.textState.setText(context.getResources().getString(R.string.PNUN));

                        } else {
                            holder.textState.setText(context.getResources().getString(R.string.PNUF));

                        }
                    } else {
                        if (subDevice.getUsbOnoff() == 1) {
                            holder.textState.setText(context.getResources().getString(R.string.PFUN));

                        } else {
                            holder.textState.setText(context.getResources().getString(R.string.PFUF));

                        }
                    }
                    break;
                case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                    holder.imageView.setImageResource(R.drawable.device_e_plug);
                    if (subDevice.getRelayOnoff() == 1) {
                        holder.textState.setText(context.getResources().getString(R.string.Open));

                    } else {
                        holder.textState.setText(context.getResources().getString(R.string.Close));

                    }
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.device_gw);
                    break;
            }
        } else {
            XlinkDevice xlinkDevice = DeviceManage.getInstance().getDevice(results.get(position).getDeviceMac());
            holder.textView.setText(xlinkDevice.getDeviceName());
            switch (xlinkDevice.getDeviceType()) {
                case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:
                    holder.textState.setText("");
                    holder.imageView.setImageResource(R.drawable.device_rc);
                    break;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY:
                    holder.textState.setText("");
                    holder.imageView.setImageResource(R.drawable.device_gw);
                    break;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                    holder.textState.setText("");
                    holder.imageView.setImageResource(R.drawable.device_plug);
                    break;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                    holder.textState.setText("");
                    holder.imageView.setImageResource(R.drawable.device_e_plug);
                    break;
                case Constant.DEVICE_TYPE.DEVICE_WIFI_AIR:
                    holder.textState.setText("");
                    holder.imageView.setImageResource(R.drawable.device_e_plug);
                    break;
            }
        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public TextView textState;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.tv_txt);
            textState = (TextView) itemView.findViewById(R.id.tv_state);
            imageView = (ImageView) itemView.findViewById(R.id.image_icon);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
