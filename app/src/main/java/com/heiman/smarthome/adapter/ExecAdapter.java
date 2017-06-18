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

import com.heiman.smarthome.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/11 14:19
 * @Description : 
 * @Modify record :
 */

public class ExecAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> results;
    private View.OnClickListener onClickListener;
    private String selectedExec;

    public List<String> getResults() {
        return results;
    }

    public String getSelectedExec() {
        return selectedExec;
    }

    public void setSelectedExec(String selectedExec) {
        this.selectedExec = selectedExec;
        notifyDataSetChanged();
    }

    public ExecAdapter(Context context, List<String> results, View.OnClickListener onClickListener) {
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
        return new ExecAdapter.ItemViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExecAdapter.ItemViewHolder) {
            bind((ExecAdapter.ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void bind(ExecAdapter.ItemViewHolder holder, int position) {
        String exec = results.get(position);
        holder.txtDeviceExec.setText(exec);
        holder.txtDeviceExec.setTag(exec);

        if (selectedExec != null && selectedExec.equals(exec)){
           holder.txtDeviceExec.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_device_exec_sel, 0);
        } else {
            holder.txtDeviceExec.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (onClickListener != null) {
            holder.txtDeviceExec.setOnClickListener(onClickListener);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDeviceExec;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtDeviceExec = (TextView) itemView.findViewById(R.id.txt_device_exec);
        }
    }
}
