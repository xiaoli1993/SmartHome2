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

import com.heiman.baselibrary.mode.Link;
import com.heiman.smarthome.R;
import com.heiman.widget.togglebutton.ToggleButton;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/12 21:18
 * @Description : 
 * @Modify record :
 */

public class LinkAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Link> results;
    private View.OnClickListener onClickListener;
    private ToggleButton.OnToggleChanged onToggleChanged;

    public List<Link> getResults() {
        return results;
    }

    public LinkAdapter(Context context, List<Link> results, View.OnClickListener onClickListener, ToggleButton.OnToggleChanged onToggleChanged) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
        this.onToggleChanged = onToggleChanged;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new LinkAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LinkAdapter.ItemViewHolder) {
            bind((LinkAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(LinkAdapter.ItemViewHolder holder, int position) {
        Link link = results.get(position);

        if (link != null && link.getPL() != null && link.getPL().getOID() != null) {
            holder.txtLinkName.setText(link.getPL().getOID().getName());

            if (link.getPL().getOID().isEnable()){
                holder.toggleLinkEnable.setToggleOn();
            } else {
                holder.toggleLinkEnable.setToggleOff();
            }
        }


        holder.txtLinkName.setTag(link);
        holder.imgItemIconLeft.setTag(link);
        holder.imgItemIconRight.setTag(link);
        holder.toggleLinkEnable.setTag(link);

        if (onClickListener != null) {
            holder.txtLinkName.setOnClickListener(onClickListener);
            holder.imgItemIconLeft.setOnClickListener(onClickListener);
            holder.imgItemIconRight.setOnClickListener(onClickListener);
        }

        if (onToggleChanged != null) {
            holder.toggleLinkEnable.setOnToggleChanged(onToggleChanged);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLinkName;
        public ImageView imgItemIconLeft;
        public ImageView imgItemIconRight;
        public ToggleButton toggleLinkEnable;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtLinkName = (TextView) itemView.findViewById(R.id.txt_link_name);
            imgItemIconLeft = (ImageView) itemView.findViewById(R.id.img_item_link_left);
            imgItemIconRight = (ImageView) itemView.findViewById(R.id.img_item_link_right);
            toggleLinkEnable = (ToggleButton) itemView.findViewById(R.id.toggle_link_enable);
            toggleLinkEnable.setToggleOn();
        }
    }
}
