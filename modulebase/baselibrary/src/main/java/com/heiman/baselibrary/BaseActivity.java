package com.heiman.baselibrary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aitangba.swipeback.SwipeBackActivity;
import com.heiman.utils.XlinkUtils;
import com.heiman.widget.spinner.NiceSpinner;
import com.heiman.widget.swipeback.CloseActivityClass;
import com.jaeger.library.StatusBarUtil;
import com.ldoublem.loadingviewlib.LVCircularCD;

/**
 * @Author : 肖力
 * @Time :  2017/5/5 11:03
 * @Description :
 * @Modify record :
 */

public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener {

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private NiceSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private LinearLayout contentLayout;

    // 内容区域的布局
    private View contentView;
    private LinearLayout ly_content;

    //错误等待
    private LVCircularCD loadBarLayout;

    private RelativeLayout resetLayout;
    private TextView resetTextView;
    private TextView resetButton;

    private RelativeLayout emptyLayout;
    private ImageView imgEmpty;
    private TextView tvEmtyHit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_title);
        StatusBarUtil.setTranslucent(this, 50);
        CloseActivityClass.activityList.add(this);
        initTitleView();
        XlinkUtils.StatusBarLightMode(this);
    }

    //初始化标题
    private void initTitleView() {
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (NiceSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        resetLayout = (RelativeLayout) findViewById(R.id.resetLayout);
        resetTextView = (TextView) findViewById(R.id.reset_textView);
        resetButton = (TextView) findViewById(R.id.reset_button);
        emptyLayout = (RelativeLayout) findViewById(R.id.emptyLayout);
        imgEmpty = (ImageView) findViewById(R.id.imgEmpty);
        tvEmtyHit = (TextView) findViewById(R.id.tvEmtyHit);
        titleBarReturn.setOnClickListener(this);
        titleBarMore.setOnClickListener(this);
        titleBarShare.setOnClickListener(this);
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
    //显示等待
    protected void showLoadView(boolean isShow) {
        loadBarLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        loadBarLayout.setViewColor(Color.rgb(0, 255, 0));
        loadBarLayout.startAnim();
    }

    //显示网络错误重置
    protected void showResetView(boolean isShow) {
        resetLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //显示空数据
    protected void showEmptyView(boolean isShow) {
        emptyLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public final void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_bar_return) {
            onClickedTopLeftButtton(v);
        } else if (i == R.id.title_bar_more) {
            onClickTopRightText(v);
        } else if (i == R.id.title_bar_share) {
            onClickTopShareImg(v);
        }
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
}

