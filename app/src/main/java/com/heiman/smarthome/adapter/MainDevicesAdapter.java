package com.heiman.smarthome.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.modle.ListMain;
import com.heiman.widget.dragindicator.DragIndicatorView;
import com.heiman.widget.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class MainDevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context context;
    private List<ListMain> results;

    //get & set
    public List<ListMain> getResults() {
        return results;
    }

    public MainDevicesAdapter(Context context, List<ListMain> results) {
        this.context = context;
        this.results = results;
    }

    MyItemClickListener mItemClickListener;

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }



    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_device, parent, false);
        return new ItemViewHolder(view);
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
        MyApplication.getLogger().i("issub" + results.get(position).isSub() + "mac:" + results.get(position).getDeviceMac() + "type:" + results.get(position).getDeviceType());
        if (results.get(position).isSub()) {
            SubDevice subDevice = SubDeviceManage.getInstance().getDevice(results.get(position).getDeviceMac(), results.get(position).getSubMac());
            setSubView(holder, subDevice);
        } else {
            XlinkDevice xlinkDevice = DeviceManage.getInstance().getDevice(results.get(position).getDeviceMac());
            setXlinkView(holder, xlinkDevice);
        }
    }

    private void setXlinkView(ItemViewHolder holder, final XlinkDevice xlinkDevice) {
        MyApplication.getLogger().i("设备名称：" + xlinkDevice.getDeviceName());
        holder.dragIndicatorView.setText(String.valueOf(4));
        holder.dragIndicatorView.setOnDismissAction(new DragIndicatorView.OnIndicatorDismiss() {
            @Override
            public void OnDismiss(DragIndicatorView view) {

            }
        });

        holder.textView.setText(xlinkDevice.getDeviceName());
        if (xlinkDevice.getDeviceState() != 0) {
            holder.textState.setText(context.getResources().getString(R.string.online));
        } else {
            holder.textState.setText(context.getResources().getString(R.string.offline));
        }
//        if (xlinkDevice.isupdevice()) {
        holder.imageUpdata.setVisibility(View.VISIBLE);
//        } else {
//            holder.imageUpdata.setVisibility(View.GONE);
//        }

        switch (xlinkDevice.getDeviceType()) {
            case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:
                holder.imageView.setImageResource(R.drawable.device_rc);
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY:
                holder.imageView.setImageResource(R.drawable.device_gw);
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW:
                holder.imageView.setImageResource(R.drawable.device_gw);
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                holder.imageView.setImageResource(R.drawable.device_plug);
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                holder.imageView.setImageResource(R.drawable.device_e_plug);
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_AIR:
                holder.imageView.setImageResource(R.drawable.device_e_plug);
                break;
        }
        holder.llRecyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getLogger().i("点击：" + xlinkDevice.getDeviceName());
                if (xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY || xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW) {
                    Bundle paramBundle = new Bundle();
                    paramBundle.putBoolean(Constant.IS_DEVICE, true);
                    paramBundle.putString(Constant.DEVICE_MAC, xlinkDevice.getDeviceMac());
                    paramBundle.putBoolean(Constant.IS_SUB, false);
                    startActivityForName("com.heiman.gateway.GwActivity", paramBundle);
                }
            }
        });
    }

    private void setSubView(final ItemViewHolder holder, final SubDevice subDevice) {
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
        holder.llRecyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getLogger().i("点击：" + subDevice.getDeviceName());
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public TextView textState;
        public RelativeLayout llRecyc;
        public ImageView imageUpdata;
        public DragIndicatorView dragIndicatorView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_txt);
            textState = (TextView) itemView.findViewById(R.id.tv_state);
            imageView = (ImageView) itemView.findViewById(R.id.image_icon);
            llRecyc = (RelativeLayout) itemView.findViewById(R.id.ll_recyc);
            imageUpdata = (ImageView) itemView.findViewById(R.id.image_updata);
            dragIndicatorView = (DragIndicatorView) itemView.findViewById(R.id.indicator);
        }
    }

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public void startActivityForName(String activityName, Bundle paramBundle) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(context, clazz);
            if (paramBundle != null)
                intent.putExtras(paramBundle);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
