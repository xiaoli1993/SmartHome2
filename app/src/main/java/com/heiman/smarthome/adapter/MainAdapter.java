package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.baselibrary.mode.Scene;
import com.heiman.smarthome.R;
import com.heiman.smarthome.modle.ListMain;
import com.heiman.widget.recyclerview.FullyGridLayoutManager;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ListMain> listMainList;
    private List<Scene> sceneList;
    private List<String> recycList;

    //get & set
    public List<ListMain> getListMainList() {
        return listMainList;
    }

    public List<Scene> getSceneList() {
        return sceneList;
    }

    //type
    public static final int TYPE_1 = 0xff01;
    public static final int TYPE_2 = 0xff02;

    public MainAdapter(Context context, List<ListMain> listMainList, List<Scene> sceneList, List<String> recycList) {
        this.context = context;
        this.listMainList = listMainList;
        this.sceneList = sceneList;
        this.recycList = recycList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_1:
                return new MySceneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_scene, parent, false));
            case TYPE_2:
                return new MyDeviceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_device, parent, false));
            default:
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MySceneViewHolder) {
            bindSceneType((MySceneViewHolder) holder, position);
        } else if (holder instanceof MyDeviceViewHolder) {
            bindDeviceType((MyDeviceViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else if (position == 1) {
            return TYPE_2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_1:
                        case TYPE_2:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }


    private void bindDeviceType(MyDeviceViewHolder holder, int position) {
        holder.recyclerDevice.setLayoutManager(new FullyGridLayoutManager(holder.recyclerDevice.getContext(), 3, GridLayoutManager.VERTICAL, false));

        holder.recyclerDevice.setAdapter(new MainDevicesAdapter(context, listMainList));
        holder.recyclerDevice.setNestedScrollingEnabled(false);

    }

    private void bindSceneType(MySceneViewHolder holder, int position) {
        holder.recycler_scene.setLayoutManager(new FullyGridLayoutManager(holder.recycler_scene.getContext(), 3, GridLayoutManager.VERTICAL, false));

        holder.recycler_scene.setAdapter(new MainSceneAdapter(context, sceneList));
        holder.recycler_scene.setNestedScrollingEnabled(false);
    }


    public class MyDeviceViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerDevice;

        public MyDeviceViewHolder(View itemView) {
            super(itemView);
            recyclerDevice = (RecyclerView) itemView.findViewById(R.id.recycler_device);
        }
    }

    public class MySceneViewHolder extends RecyclerView.ViewHolder {
        public SwipeMenuRecyclerView recycler_scene;

        public MySceneViewHolder(final View itemView) {
            super(itemView);
            recycler_scene = (SwipeMenuRecyclerView) itemView.findViewById(R.id.recycler_scene);
        }
    }

}
