package com.heiman.home.adapter;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.mode.Home;
import com.heiman.home.HomeManageActivity;
import com.heiman.home.R;

import java.util.ArrayList;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/2/20 16:24
 * @Description :
 */
public class HomeAdapter extends BaseAdapter {
    private ArrayList<Home> apk_list;
    private Context context;

    // BadgeView bgvtext;

    public HomeAdapter(Context context, ArrayList<Home> apk_list) {
        this.apk_list = apk_list;
        this.context = context;
    }

    public void setDevices(ArrayList<Home> apk_list) {
        this.apk_list = apk_list;
    }

    @Override
    public int getCount() {
        return apk_list.size();
    }

    @Override
    public Object getItem(int position) {
        return apk_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        final Home entity = apk_list.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.home_item_list, null);
            holder.device_name = (TextView) convertView
                    .findViewById(R.id.device_name);
            holder.device_state = (TextView) convertView
                    .findViewById(R.id.device_state);
            holder.device_id = (TextView) convertView
                    .findViewById(R.id.device_id);
            holder.img = (ImageView) convertView.findViewById(R.id.device_img);
            holder.lowbattery = (ImageView) convertView
                    .findViewById(R.id.lowbattery);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.device_name.setText(entity.getName());

        holder.device_state.setText(entity.getUser_list().size() + "");

        if (apk_list.get(position) != null) {
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), HomeManageActivity.class);
                    intent.putExtra(Constant.HOME_ID, entity.getId());
                    intent.setClass(v.getContext(), HomeManageActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }


    class ViewHolder {
        TextView device_name;
        TextView device_state;
        TextView device_offline;
        TextView device_id;
        ImageView img;
        ImageView lowbattery;
    }

    public void onDateChange(ArrayList<Home> apk_list) {
        this.apk_list = apk_list;
        this.notifyDataSetChanged();
    }

}
