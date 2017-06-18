package com.heiman.baselibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.utils.permission.PerUtils;
import com.heiman.utils.permission.PerimissionsCallback;
import com.heiman.utils.permission.PermissionEnum;
import com.heiman.utils.permission.PermissionManager;
import com.heiman.widget.popwindow.HintPopupWindow;
import com.heiman.widget.swipeback.CloseActivityClass;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/5 11:03
 * @Description :基础类
 * @Modify record :
 */

public abstract class BaseActivity extends AppCompatActivity {

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private LinearLayout contentLayout;

    // 内容区域的布局
    private View contentView;
    private LinearLayout ly_content;
    private KProgressHUD hud = null;
    private static final int ACTIVITY_CREATE = 1;
    private static final int ACTIVITY_START = 2;
    private static final int ACTIVITY_RESUME = 3;
    private static final int ACTIVITY_PAUSE = 4;
    private static final int ACTIVITY_STOP = 5;
    private static final int ACTIVITY_DESTROY = 6;
    public int activityState;
    public Context mContext;
    private HintPopupWindow hintPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_title);
        StatusBarUtil.setTranslucent(this, 50);
        CloseActivityClass.activityList.add(this);
        initTitleView();
        XlinkUtils.StatusBarLightMode(this);
        mContext = this;
    }

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public void startActivityForName(String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
            Intent intent = new Intent(this, clazz);
            if (paramBundle != null)
                intent.putExtras(paramBundle);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 跳转界面
     *
     * @param paramClass
     */
    protected void openActivity(Class<?> paramClass) {
        BaseApplication.getLogger().e(getClass().getSimpleName(), "openActivity：：" + paramClass.getSimpleName());
        openActivity(paramClass, null);
    }

    protected void openActivity(Class<?> paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent(this, paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    //初始化标题
    private void initTitleView() {
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickedTopLeftButtton(v);
            }
        });
        titleBarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTopRightText(v);
            }
        });
        titleBarShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTopShareImg(v);
            }
        });
    }

    /**
     * 跳转 Activity
     *
     * @param cls
     */
    public void starActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    /***
     * 设置内容区域
     *
     * @param resId
     *            资源文件ID
     */
    @SuppressWarnings("deprecation")
    public void setContentLayout(int resId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(layoutParams);
        contentView.setBackgroundDrawable(null);
        if (null != ly_content) {
            ly_content.addView(contentView);
        }

    }


    /**
     * @param resId 修改返回图标
     */
    public void setReturnImage(int resId) {
        if (resId != 0) {
            titleBarReturn.setImageResource(resId);
        }
    }

    /**
     * @param resId 分享按键设置图标
     */
    public void setShareImage(int resId) {
        if (resId != 0) {
            titleBarShare.setImageResource(resId);
        }
    }

    /**
     * @param resId 修改更多图标
     */
    public void setMoreImage(int resId) {
        if (resId != 0) {
            titleBarMore.setImageResource(resId);
        }
    }

    /***
     * 设置内容区域
     *
     * @param view
     *            View对象
     */
    public void setContentLayout(View view) {
        if (null != ly_content) {
            ly_content.addView(view);
        }
    }

    /**
     * 得到内容的View
     *
     * @return
     */
    public View getLyContentView() {

        return contentView;
    }


    /**
     * 得到左边的按钮
     *
     * @return
     */
    public View getbtn_left() {
        return titleBarReturn;
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (null != titleBarTitle) {
            titleBarTitle.setText(title);
        }
    }

    //显示返回按键
    protected void showReturnView(boolean isShow) {
        titleBarReturn.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示下拉标题
    protected void showTitleSpinnerView(boolean isShow) {
        titleBarTitleSpinner.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示子标题
    protected void showSubTitleView(boolean isShow) {
        subTitleBarTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示更多
    protected void showMoreView(boolean isShow) {
        titleBarMore.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示小红点
    protected void showRedpointView(boolean isShow) {
        titleBarRedpoint.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示分享标题
    protected void showShareView(boolean isShow) {
        titleBarShare.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示标题
    protected void showTitleView(boolean isShow) {
        titleBarTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示头部
    protected void showHeadView(boolean isShow) {
        titleBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示头部
    protected void showContenView(boolean isShow) {
        contentLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    //返回按键
    protected void onClickedTopLeftButtton(View view) {
        finish();
    }

    //左侧更多按键
    protected void onClickTopRightText(View view) {

    }

    //分享按键
    protected void onClickTopShareImg(View view) {

    }

    /**
     * 显示HUM等待
     */
    public void showHUDProgress() {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();
        }
    }

    public void showHUDProgress(String message) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(true).show();
        }
    }

    public void showHUDProgress(String message, String DetailsLabel) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setDetailsLabel(DetailsLabel).show();
        }
    }

    public void showHUDProgress(View imageView, String message) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setCustomView(imageView)
                    .setLabel(message).show();
        }
    }

    public void showHUDProgress(int color, String message) {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(color)
                .setLabel(message)
                .setAnimationSpeed(2).show();
    }

    public void showHUDProgressUpdata(String message) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel(message).show();
        }
    }

    public void showHUDProgressUpdata(String message, String DetailsLabel) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel(message)
                    .setDetailsLabel(DetailsLabel).show();
        }
    }

    public void setHUMMAXProgressUpdata(int max) {
        if (hud != null) {
            hud.setMaxProgress(max);
        }
    }

    public void setHUMProgressUpdata(int currentProgress) {
        if (hud != null) {
            hud.setProgress(currentProgress);
        }
    }

    public void setHUMLabel(String label) {
        if (hud != null) {
            hud.setLabel(label);
        }
    }

    public void dismissHUMProgress() {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
    }

    /**
     * 权限检查
     *
     * @param permissions 所需检测权限数组
     *                    PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.WRITE_EXTERNAL_STORAGE, PermissionEnum.CAMERA;
     */
    public void checkPermissions(PermissionEnum... permissions) {
        PermissionManager
                .with(this)
                .tag(1000)
                .permission(permissions)
                .callback(new PerimissionsCallback() {
                    @Override
                    public void onGranted(ArrayList<PermissionEnum> grantedList) {
                        Toast.makeText(mContext, R.string.Permission_is_granted, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(ArrayList<PermissionEnum> deniedList) {
                        Toast.makeText(mContext, R.string.Permission_denied, Toast.LENGTH_SHORT).show();
                        PermissionDenied(deniedList);
                    }
                })
                .checkAsk();
    }

    private void PermissionDenied(final ArrayList<PermissionEnum> permissionsDenied) {
        StringBuilder msgCN = new StringBuilder();
        for (int i = 0; i < permissionsDenied.size(); i++) {

            if (i == permissionsDenied.size() - 1) {
                msgCN.append(permissionsDenied.get(i).getName_cn());
            } else {
                msgCN.append(permissionsDenied.get(i).getName_cn() + ",");
            }
        }
        if (mContext == null) {
            return;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage(String.format(mContext.getResources().getString(R.string.permission_explain), msgCN.toString()))
                .setCancelable(false)
                .setPositiveButton(R.string.per_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PerUtils.openApplicationSettings(mContext, R.class.getPackage().getName());
                    }
                })
                .setNegativeButton(R.string.per_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, R.string.Click_Cancel, Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityState = ACTIVITY_START;
    }


    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        BaseApplication.getMyApplication().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOP;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
    }

    /**
     * 显示模糊pop
     *
     * @param view
     * @param strList
     * @param clickList
     */
    public void showHintPopupWindow(View view, ArrayList<String> strList, List<View.OnClickListener> clickList) {
        hintPopupWindow = new HintPopupWindow(this, strList, clickList);
        hintPopupWindow.showPopupWindow(view);
    }

    public void disHintPopupWind() {
        if (hintPopupWindow != null) {
            hintPopupWindow.dismissPopupWindow();
        }

    }

}

