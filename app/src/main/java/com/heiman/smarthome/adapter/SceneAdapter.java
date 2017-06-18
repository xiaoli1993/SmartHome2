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

import com.heiman.baselibrary.mode.Scene;
import com.heiman.smarthome.R;
import com.heiman.widget.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/7 21:25
 * @Description : 
 * @Modify record :
 */

public class SceneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private List<Scene> results;
    private View.OnClickListener onClickListener;
    public List<Scene> getResults() {
        return results;
    }

    public SceneAdapter(Context context, List<Scene> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(results, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SceneAdapter.ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SceneAdapter.ItemViewHolder) {
            bind((SceneAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(SceneAdapter.ItemViewHolder holder, int position) {
        Scene scene = results.get(position);

        if (scene != null && scene.getPL() != null && scene.getPL().getOID() != null) {
            holder.txtSceneName.setText(scene.getPL().getOID().getName());
        }
        holder.btnSetting.setTag(scene);
        holder.imgSceneIcon.setTag(scene);
        holder.txtSceneName.setTag(scene);

        if (onClickListener != null) {
            holder.btnSetting.setOnClickListener(onClickListener);
            holder.imgSceneIcon.setOnClickListener(onClickListener);
            holder.txtSceneName.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtSceneName;
        public ImageView imgSceneIcon;
        public ImageButton btnSetting;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txtSceneName = (TextView) itemView.findViewById(R.id.txt_scene_name);
            imgSceneIcon = (ImageView) itemView.findViewById(R.id.img_scene_icon);
            btnSetting = (ImageButton) itemView.findViewById(R.id.btn_scene_item_setting);
        }
    }
}
