package com.heiman.smarthome.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.smarthome.R;
import com.heiman.smarthome.modle.LeftMain;

import java.util.List;


public class MainLeftAdapter extends BaseAdapter {

    private Context context;
    private List<LeftMain> list;

    public MainLeftAdapter(Context context, List<LeftMain> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_left_main, null);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }

        hold.imageView = (ImageView) convertView.findViewById(R.id.item_imageview);
        hold.textView = (TextView) convertView.findViewById(R.id.item_textview);
//        hold.imageView.setColorFilter(context.getResources().getColor(R.color.text_title));
        hold.imageView.setImageResource(list.get(position).getImageView());
        hold.textView.setText(list.get(position).getText());
        return convertView;
    }

    static class ViewHold {
        public ImageView imageView;
        public TextView textView;
    }

}
