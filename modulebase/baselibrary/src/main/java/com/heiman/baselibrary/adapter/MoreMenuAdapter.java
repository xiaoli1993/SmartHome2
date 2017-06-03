package com.heiman.baselibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.R;
import com.heiman.baselibrary.mode.MoreMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/24 15:37
 * @Description : 更多Adapter
 * @Modify record :
 */

public class MoreMenuAdapter extends BaseAdapter {

    private List<MoreMenu> moreMenuList = new ArrayList<MoreMenu>();

    private Context context;
    private LayoutInflater layoutInflater;

    public MoreMenuAdapter(Context context, List<MoreMenu> moreMenuList) {
        this.context = context;
        this.moreMenuList = moreMenuList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return moreMenuList.size();
    }

    @Override
    public MoreMenu getItem(int position) {
        return moreMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_more_menu, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((MoreMenu) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(MoreMenu moreMenu, ViewHolder holder) {
        //TODO implement
        holder.tvLeft.setText(moreMenu.getLeftText());
        holder.tvRight.setText(moreMenu.getRightText());
        holder.imageArrowRight.setVisibility(moreMenu.isVRightImage() ? View.VISIBLE : View.GONE);
    }

    protected class ViewHolder {
        private ImageView imageArrowRight;
        private TextView tvLeft;
        private TextView tvRight;

        public ViewHolder(View view) {
            imageArrowRight = (ImageView) view.findViewById(R.id.image_arrow_right);
            tvLeft = (TextView) view.findViewById(R.id.tv_Left);
            tvRight = (TextView) view.findViewById(R.id.tv_Right);
        }
    }
}
