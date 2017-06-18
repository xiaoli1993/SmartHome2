package com.heiman.smarthome.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;

import java.util.Calendar;
import java.util.Date;

public class TimerActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private TextView txtTitleRight;
    private RelativeLayout rlRepeatContainer;
    private RelativeLayout rlStartTimeContainer;
    private TextView txtRepateTimes;
    private TextView txtStartTime;
    private int weekFalg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_timer);
        txtTitleRight = (TextView) findViewById(R.id.title_tv_Right);
        rlRepeatContainer = (RelativeLayout) findViewById(R.id.rl_repeat_container);
        rlStartTimeContainer = (RelativeLayout) findViewById(R.id.rl_start_time_container);
        txtRepateTimes = (TextView) findViewById(R.id.txt_repeat_times);
        txtStartTime = (TextView) findViewById(R.id.txt_start_time);

        txtTitleRight.setOnClickListener(this);
        rlRepeatContainer.setOnClickListener(this);
        rlStartTimeContainer.setOnClickListener(this);

        txtTitleRight.setVisibility(View.VISIBLE);
        txtTitleRight.setText(R.string.device_exec_submit);
        txtTitleRight.setTextColor(getResources().getColor(R.color.btn_regist_color));

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_timer));
        setReturnImage(R.drawable.personal_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String time = date.getHours() + ":" + date.getMinutes();
        txtStartTime.setText(time);
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_timer, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.title_tv_Right:
                LogUtil.e("点击提交！");
                break;
            case R.id.rl_repeat_container:
                LogUtil.e("点击选择重复！");
                showSetLinkRepeatDialog();
                break;
            case R.id.rl_start_time_container:
                LogUtil.e("点击选择开启时间！");
                setStartTime();
                break;
            case R.id.txt_one_time:
                dismissDialog(v);
                LogUtil.e("选择只执行一次!");
                txtRepateTimes.setText(R.string.one_time);
                break;
            case R.id.txt_everyday:
                dismissDialog(v);
                LogUtil.e("选择每天执行！");
                txtRepateTimes.setText(R.string.everyday);
                break;
            case R.id.txt_monday_to_friday:
                dismissDialog(v);
                LogUtil.e("选择周一至周五执行！");
                txtRepateTimes.setText(R.string.monday_to_friday);
                break;
            case R.id.txt_custom:
                dismissDialog(v);
                LogUtil.e("选择自定义！");
                showSetCustomLinkRepeatDialog();
                break;
            case R.id.btn_cancel:
                dismissDialog(v);
                break;
            case R.id.btn_confirm:
                dismissDialog(v);
                LogUtil.e("weekFalg:" + weekFalg);
                if (weekFalg > 0) {
                    txtRepateTimes.setText(SmartHomeUtils.getWkString(TimerActivity.this, weekFalg));
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isPressed()) {
            return;
        }
        switch (buttonView.getId()) {
            case R.id.cbx_monday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Monday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Monday;
                }
                break;
            case R.id.cbx_tuesday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Tuesday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Tuesday;
                }
                break;
            case R.id.cbx_wednesday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Wednesday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Wednesday;
                }
                break;
            case R.id.cbx_thursday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Thursday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Thursday;
                }
                break;
            case R.id.cbx_friday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Friday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Friday;
                }
                break;
            case R.id.cbx_saturday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Saturday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Saturday;
                }
                break;
            case R.id.cbx_sunday:
                if (isChecked) {
                    weekFalg = weekFalg + SmartHomeUtils.Sunday;
                } else {
                    weekFalg = weekFalg - SmartHomeUtils.Sunday;
                }
                break;
        }
    }

    private void setStartTime() {

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                try {
                    LogUtil.e("h:" + date.getHours() + "\tm:" + date.getMinutes());
                    String str = date.getHours() + ":" + date.getMinutes();
                    txtStartTime.setText(str);
                } catch (Exception e) {
                    MyApplication.getLogger().e(e.getMessage());
                }
            }
        }).setType(new boolean[]{false,false,false,true,true,false})
                .build();
        pvTime.show();
    }

    private void showSetLinkRepeatDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_set_link_repeat_dialog, null);
        Dialog dialog = new Dialog(this, R.style.dialog_set_repeat);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TextView txtOneTime = (TextView) view.findViewById(R.id.txt_one_time);
        TextView txtEveryday = (TextView) view.findViewById(R.id.txt_everyday);
        TextView txtMondayToFriday = (TextView) view.findViewById(R.id.txt_monday_to_friday);
        TextView txtCustom = (TextView) view.findViewById(R.id.txt_custom);

        txtOneTime.setTag(dialog);
        txtEveryday.setTag(dialog);
        txtMondayToFriday.setTag(dialog);
        txtCustom.setTag(dialog);

        txtOneTime.setOnClickListener(this);
        txtEveryday.setOnClickListener(this);
        txtMondayToFriday.setOnClickListener(this);
        txtCustom.setOnClickListener(this);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void showSetCustomLinkRepeatDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_set_link_custom_repeat_dialog, null);
        Dialog dialog = new Dialog(this, R.style.dialog_set_repeat);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        CheckBox cbxMonday = (CheckBox) view.findViewById(R.id.cbx_monday);
        CheckBox cbxTuesday = (CheckBox) view.findViewById(R.id.cbx_tuesday);
        CheckBox cbxWednesday = (CheckBox) view.findViewById(R.id.cbx_wednesday);
        CheckBox cbxThursday = (CheckBox) view.findViewById(R.id.cbx_thursday);
        CheckBox cbxFriday = (CheckBox) view.findViewById(R.id.cbx_friday);
        CheckBox cbxSaturday = (CheckBox) view.findViewById(R.id.cbx_saturday);
        CheckBox cbxSunday = (CheckBox) view.findViewById(R.id.cbx_sunday);

        btnCancel.setTag(dialog);
        btnConfirm.setTag(dialog);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        cbxMonday.setOnCheckedChangeListener(this);
        cbxTuesday.setOnCheckedChangeListener(this);
        cbxWednesday.setOnCheckedChangeListener(this);
        cbxThursday.setOnCheckedChangeListener(this);
        cbxFriday.setOnCheckedChangeListener(this);
        cbxSaturday.setOnCheckedChangeListener(this);
        cbxSunday.setOnCheckedChangeListener(this);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void dismissDialog(View v) {
        Object o = v.getTag();

        if (o != null && o instanceof Dialog) {
            Dialog dialog = (Dialog) o;
            dialog.dismiss();
        }
    }

}
