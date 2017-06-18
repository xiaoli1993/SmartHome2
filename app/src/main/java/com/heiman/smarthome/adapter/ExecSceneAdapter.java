/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heiman.baselibrary.mode.Scene;
import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/13 22:55
 * @Description : 
 * @Modify record :
 */

public class ExecSceneAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Scene> results;
    private View.OnClickListener onClickListener;
    private Scene selectedExec;

    public List<Scene> getResults() {
        return results;
    }

    public Scene getSelectedExec() {
        return selectedExec;
    }

    public void setSelectedExec(Scene selectedExec) {
        this.selectedExec = selectedExec;
        notifyDataSetChanged();
    }

    public ExecSceneAdapter(Context context, List<Scene> results, View.OnClickListener onClickListener) {
        this.context = context;
        this.results = results;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_exec, parent, false);
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ExecSceneAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExecSceneAdapter.ItemViewHolder) {
            bind((ExecSceneAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(ExecSceneAdapter.ItemViewHolder holder, int position) {
        Scene exec = results.get(position);
        if (exec != null && exec.getPL() != null && exec.getPL().getOID() != null) {
            holder.txtExecSceneName.setText(exec.getPL().getOID().getName());
        }

        holder.txtExecSceneName.setTag(exec);

        if (selectedExec != null && selectedExec.equals(exec)){
            holder.txtExecSceneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_device_exec_sel, 0);
        } else {
            holder.txtExecSceneName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (onClickListener != null) {
            holder.txtExecSceneName.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtExecSceneName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtExecSceneName = (TextView) itemView.findViewById(R.id.txt_device_exec);
        }
    }
}
