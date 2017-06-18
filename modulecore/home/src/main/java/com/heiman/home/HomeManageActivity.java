package com.heiman.home;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.os.Bundle;
import android.view.View;

import com.heiman.baselibrary.BaseActivity;
import com.qintong.library.InsLoadingView;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/2/20 18:46
 * @Description :
 */
public class HomeManageActivity extends BaseActivity {
    //    private RelativeLayout theme_table, set_layout;
//    private ImageButton imgback;
//    private ImageButton imgok;
//
//    private RelativeLayout setLayout;
//    private CircularImageView ivSetAvator;
//    private TextView tvHomeName;
//    private TextView tvMembership;
//    private TextView tvDeviceNumber;
//    private FamiliarRefreshRecyclerView mGridRecyclerView;
//    private FamiliarRecyclerView mListRecyclerView;
//    private Home home;
//
    InsLoadingView mInsLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_home_manage);
        mInsLoadingView = (InsLoadingView) findViewById(R.id.loading_view);
/*        mInsLoadingView.setCircleDuration(2000);
        mInsLoadingView.setRotateDuration(10000);
        mInsLoadingView.setStartColor(Color.YELLOW);
        mInsLoadingView.setEndColor(Color.BLUE);*/
        mInsLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mInsLoadingView.getStatus()) {
                    case UNCLICKED:
                        mInsLoadingView.setStatus(InsLoadingView.Status.LOADING);
                        break;
                    case LOADING:
                        mInsLoadingView.setStatus(InsLoadingView.Status.CLICKED);
                        break;
                    case CLICKED:
                        mInsLoadingView.setStatus(InsLoadingView.Status.UNCLICKED);
                }
            }
        });
        mInsLoadingView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
//        initView();
//        initEven();
//        initTheme();
    }
//
//    private void initView() {
//        setLayout = (RelativeLayout) findViewById(R.id.set_layout);
//        ivSetAvator = (CircularImageView) findViewById(R.id.iv_set_avator);
//        tvHomeName = (TextView) findViewById(R.id.tv_home_name);
//        tvMembership = (TextView) findViewById(R.id.tv_Membership);
//        tvDeviceNumber = (TextView) findViewById(R.id.tv_device_number);
//        mGridRecyclerView = (FamiliarRefreshRecyclerView) findViewById(R.id.mGridRecyclerView);
//        mListRecyclerView = (FamiliarRecyclerView) findViewById(R.id.mListRecyclerView);
//        imgback = (ImageButton) findViewById(R.id.zigbee_back);
//        imgok = (ImageButton) findViewById(R.id.zigbee_ok);
//        Intent intent = getIntent();
//        String Homeid = intent.getStringExtra(Constants.HOME_ID);
//        home = HomeManage.getInstance().getHome(Homeid);
//        tvHomeName.setText(home.getName());
//        tvMembership.setText("一共有：" + home.getUser_list().size());
//        getHomeDevice();
//    }
//
//    private void getHomeDevice() {
//        HttpAgent2.getInstance().GetDeviceHomeList(home.getId(), new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                BaseApplication.getLogger().e("获取设备失败:" + responseString);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                BaseApplication.getLogger().json(responseString);
//            }
//        });
//
//        for (int i = 0; i < home.getUser_list().size(); i++) {
//            final int finalI = i;
//            HttpAgent2.getInstance().GetOpenInfo(home.getUser_list().get(i).getUser_id(), new TextHttpResponseHandler() {
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    BaseApplication.getLogger().e("获取用户信息失败:" + responseString);
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    BaseApplication.getLogger().json(responseString);
//                    try {
//                        JSONObject json = new JSONObject(responseString);
//                        home.getUser_list().get(finalI).setAvatar(json.getString("avatar"));
//                        home.getUser_list().get(finalI).setNickname(json.getString("nickname"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        }
//
//    }
//
//    private void initEven() {
//        imgback.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                // 用户当前为按下
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                    imgback.setImageResource(R.drawable.top_back);
//                }
//                // 用户当前为抬起
//                else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    finish();// 这个是关键
//                    overridePendingTransition(android.R.anim.slide_in_left,
//                            android.R.anim.slide_out_right);// 从右往左滑动动画
//                    // actionAlertDialog();
//                    imgback.setImageResource(R.drawable.top_back1);
//                }
//                return false;
//            }
//        });
//        imgok.setImageResource(R.drawable.um_more_pressed);
////        imgok.setVisibility(View.GONE);
//        imgok.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                // 用户当前为按下
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                    imgok.setImageResource(R.drawable.um_more_normal);
//                }
//                // 用户当前为抬起
//                else if (event.getAction() == MotionEvent.ACTION_UP) {
////                    Intent intent = new Intent();
////                    intent.putExtra("iswifi", false);
////                    intent.setClass(HomeListActivity.this, ChoiceAddDeviceActivity.class);
////                    startActivity(intent);
//                    imgok.setImageResource(R.drawable.um_more_pressed);
//                }
//                return false;
//            }
//        });
//    }
//
//    private void initTheme() {
//        theme_table = (RelativeLayout) findViewById(R.id.theme_table);
//        set_layout = (RelativeLayout) findViewById(R.id.set_layout);
//
//    }

}
