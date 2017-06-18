package com.heiman.home;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.HomeManage;
import com.heiman.baselibrary.mode.Home;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.widget.dialog.MDDialog;
import com.heiman.widget.edittext.ClearEditText;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.FamiliarRefreshRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;


/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/2/20 15:33
 * @Description :
 */
public class HomeListActivity extends BaseActivity {

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private TextView titleTvRight;
    private ImageView titleBarShare;

    private FamiliarRefreshRecyclerView listHome;


    //    private HomeAdapter homeadapter;
    public static ArrayList<Home> homeArrayList;
    private FamiliarEasyAdapter<Home> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_home_list);
        StatusBarUtil.setTranslucent(this, 50);
        initView();
        initEven();
    }

    private void initEven() {
        getHomes();
    }

    private void getHomes() {

        HttpManage.getInstance().getHomeList(HomeListActivity.this, new HttpManage.ResultCallback<String>() {


            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                BaseApplication.getLogger().e("获取失败：" + error.getCode() + "\t" + SmartHomeUtils.showHttpCode(error.getCode()));
                mHandler.sendEmptyMessage(2);

            }

            @Override
            public void onSuccess(int code, String response) {
                BaseApplication.getLogger().json(response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray list = object.getJSONArray("list");
                    int count = object.getInt("count");
                    if (count == 0) {
                        mHandler.sendEmptyMessage(2);
                        return;
                    }
                    final int iSize = list.length();
                    for (int i = 0; i < iSize; i++) {
                        Gson gson = new Gson();
                        Home home = gson.fromJson(list.get(i).toString(), Home.class);
                        HomeManage.getInstance().addHome(home);
                    }
                    mHandler.sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dismissHUMProgress();
                    listHome.pullRefreshComplete();
                    homeArrayList = HomeManage.getInstance().getHome();
//                    homeadapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    dismissHUMProgress();
                    break;
            }
        }
    };

    private void showMDDialog(final String FamilyName) {
        new MDDialog.Builder(HomeListActivity.this)
                .setContentView(R.layout.item_md_dialog)
                .setContentViewOperator(new MDDialog.ContentViewOperator() {
                    @Override
                    public void operate(View contentView) {
                        ClearEditText et = (ClearEditText) contentView.findViewById(R.id.edit0);
                        et.setHint(getResources().getString(R.string.Enter_Family_Name));
                        et.setText(FamilyName);
                    }
                })
                .setTitle(getResources().getString(R.string.Create_family))
                .setPositiveButtonMultiListener(new MDDialog.OnMultiClickListener() {

                    @Override
                    public void onClick(View clickedView, View contentView) {
                        ClearEditText et = (ClearEditText) contentView.findViewById(R.id.edit0);
                        String homename = et.getText().toString();
                        if (TextUtils.isEmpty(et.getText())) {
                            // 设置提示
                            XlinkUtils.shortTips(HomeListActivity.this, getResources().getString(R.string.name_isempty), 0, 0, 0, false);
                            return;
                        }
                        showHUDProgress(getResources().getString(R.string.gateway_Send_in));
                        HttpManage.getInstance().createHome(HomeListActivity.this, homename, new HttpManage.ResultCallback<String>() {

                            @Override
                            public void onError(Header[] headers, HttpManage.Error error) {
                                BaseApplication.getLogger().e("创建失败：" + error.getCode() + "\t" + SmartHomeUtils.showHttpCode(error.getCode()));
                                mHandler.sendEmptyMessage(2);
                            }

                            @Override
                            public void onSuccess(int code, String response) {
                                BaseApplication.getLogger().json(response);
                                Gson gson = new Gson();
                                Home home = gson.fromJson(response, Home.class);
                                HomeManage.getInstance().addHome(home);
                                mHandler.sendEmptyMessage(1);
                            }
                        });
                    }
                })
                .setWidthMaxDp(600)
                .setShowButtons(true)
                .create()
                .show();
    }


    private void initView() {
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleTvRight = (TextView) findViewById(R.id.title_tv_Right);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);

        titleBarMore.setImageResource(R.drawable.std_tittlebar_main_device_add);
        titleBarTitle.setVisibility(View.VISIBLE);
        titleBarTitleSpinner.setVisibility(View.GONE);
        titleBarMore.setVisibility(View.VISIBLE);
        titleBarTitle.setText(getResources().getString(R.string.Family_management));
        titleBarTitle.setTextColor(getResources().getColor(R.color.class_P));


        listHome = (FamiliarRefreshRecyclerView) findViewById(R.id.list_home);
        homeArrayList = HomeManage.getInstance().getHome();
//        homeadapter = new HomeAdapter(HomeListActivity.this, homeArrayList);
        mAdapter = new FamiliarEasyAdapter<Home>(this, R.layout.home_item_list, homeArrayList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final Home home = homeArrayList.get(position);
                TextView device_name = (TextView) holder.findView(R.id.device_name);
                TextView device_state = (TextView) holder.findView(R.id.device_state);
                ImageView device_img = (ImageView) holder.findView(R.id.device_img);
                device_img.setImageResource(R.drawable.main_right_home);
                device_name.setText(home.getName());
                device_state.setText(getString(R.string.Member_of_family) + ":" + home.getUser_list().size() + getString(R.string.people));

            }
        };
        listHome.setAdapter(mAdapter);
        listHome.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                final Home home = homeArrayList.get(position);
                Intent intent = new Intent(HomeListActivity.this, HomeManageActivity.class);
                intent.putExtra(Constant.HOME_ID, home.getId());
                intent.setClass(HomeListActivity.this, HomeManageActivity.class);
                startActivity(intent);
            }
        });
        listHome.setOnItemLongClickListener(new FamiliarRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                final Home home = homeArrayList.get(position);
                ArrayList<String> strList = new ArrayList<String>();
                strList.add(getString(R.string.home_Modify_family_name));
                strList.add(getString(R.string.home_Delete_family));
                ArrayList<View.OnClickListener> clickList = new ArrayList<>();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disHintPopupWind();
                        showMDDialog(home);
                    }
                };
                clickList.add(clickListener);
                View.OnClickListener clickListeners = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disHintPopupWind();
                        delteHome(home);
                    }
                };
                clickList.add(clickListeners);
                showHintPopupWindow(view, strList, clickList);

                return true;
            }
        });
        listHome.setOnPullRefreshListener(new FamiliarRefreshRecyclerView.OnPullRefreshListener() {
            @Override
            public void onPullRefresh() {
                getHomes();
            }
        });

        titleBarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMDDialog(getString(R.string.home_My_Home));
            }
        });
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showMDDialog(final Home home) {
        new MDDialog.Builder(HomeListActivity.this)
                .setContentView(R.layout.item_md_dialog)
                .setContentViewOperator(new MDDialog.ContentViewOperator() {
                    @Override
                    public void operate(View contentView) {
                        ClearEditText et = (ClearEditText) contentView.findViewById(R.id.edit0);
                        et.setText(home.getName());
                        et.setHint(getResources().getString(R.string.Enter_Family_Name));
                    }
                })
                .setTitle(getResources().getString(R.string.Create_family))
                .setPositiveButtonMultiListener(new MDDialog.OnMultiClickListener() {

                    @Override
                    public void onClick(View clickedView, View contentView) {
                        ClearEditText et = (ClearEditText) contentView.findViewById(R.id.edit0);
                        final String homename = et.getText().toString();
                        if (TextUtils.isEmpty(et.getText())) {
                            // 设置提示
                            XlinkUtils.shortTips(HomeListActivity.this, getResources().getString(R.string.name_isempty), 0, 0, 0, false);
                            return;
                        }
                        showHUDProgress(getResources().getString(R.string.gateway_Send_in));
                        HttpManage.getInstance().changeHomeName(HomeListActivity.this, home.getId(), homename, new HttpManage.ResultCallback<String>() {
                            @Override
                            public void onError(Header[] headers, HttpManage.Error error) {
                                BaseApplication.getLogger().e("创建失败：" + error.getCode() + "\t" + SmartHomeUtils.showHttpCode(error.getCode()));
                                mHandler.sendEmptyMessage(2);
                            }

                            @Override
                            public void onSuccess(int code, String response) {
                                home.setName(homename);
                                HomeManage.getInstance().addHome(home);
                                mHandler.sendEmptyMessage(1);
                            }
                        });
                    }
                })
                .setWidthMaxDp(600)
                .setShowButtons(true)
                .create()
                .show();
    }

    private void delteHome(final Home home) {

        HttpManage.getInstance().deleteHome(HomeListActivity.this, home.getId(), new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                BaseApplication.getLogger().e("创建失败：" + error.getCode() + "\t" + SmartHomeUtils.showHttpCode(error.getCode()));
            }

            @Override
            public void onSuccess(int code, String response) {
                BaseApplication.getLogger().json(response);
                HomeManage.getInstance().removeHome(home);
                mHandler.sendEmptyMessage(1);
            }
        });
    }
}
