package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.mode.Room;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.widget.swipeback.CloseActivityClass;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

/**
 * @Author : 肖力
 * @Time :  2017/5/31 11:51
 * @Description :
 * @Modify record :
 */

public class RoomMoreActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;

    private TextView tvEdit;
    private FamiliarRecyclerView recyclerDevice;
    private FamiliarEasyAdapter<Room> mAdapter;
    private boolean isEdit = false;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_more);
        StatusBarUtil.setTranslucent(this, 50);
        CloseActivityClass.activityList.add(this);
        initView();
        initEven();
//        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {

        HttpManage.getInstance().ckData(MyApplication.getMyApplication(), "", "Room", new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("msg:" + error.getMsg() + "\tcode:" + error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                MyApplication.getLogger().json(response);
                try {
                    roomList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray list = jsonObject.getJSONArray("list");
                    int iSize = list.length();
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObj = list.getJSONObject(i);
                        Gson gson = new Gson();
                        Room room = gson.fromJson(jsonObj.toString(), Room.class);
                        MyApplication.getLogger().i("room_name:" + room.getRoom_name() + "\tcreator:" + room.getCreator()+"\nURL:"+room.getRoom_bg_url());
                        roomList.add(room);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initEven() {
        roomList = new ArrayList<Room>();
        mAdapter = new FamiliarEasyAdapter<Room>(this, R.layout.item_more_menu, roomList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final Room room = roomList.get(position);
                ImageButton imageReduce = (ImageButton) holder.findView(R.id.image_reduce);
                ImageView imageArrowRight = (ImageView) holder.findView(R.id.image_arrow_right);
                TextView tvLeft = (TextView) holder.findView(R.id.tv_Left);
                TextView tvRight = (TextView) holder.findView(R.id.tv_Right);
                tvLeft.setText(room.getRoom_name());
                tvRight.setText("");
                imageArrowRight.setVisibility(View.VISIBLE);
                imageReduce.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                imageReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpManage.getInstance().deleteData(MyApplication.getMyApplication(), "Room", room.getObjectId(), new HttpManage.ResultCallback<String>() {
                            @Override
                            public void onError(Header[] headers, HttpManage.Error error) {
                                MyApplication.getLogger().e("msg:" + error.getMsg() + "\tcode:" + error.getCode());
                                XlinkUtils.shortTips(MyApplication.getMyApplication(), SmartHomeUtils.showHttpCode(error.getCode()), 0, 0, 0, false);
                            }

                            @Override
                            public void onSuccess(int code, String response) {
                                roomList.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        };
        recyclerDevice.setAdapter(mAdapter);
        recyclerDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Room room = roomList.get(position);
                BaseApplication.getLogger().i("点击：" + room.getRoom_name() + "URL:" + room.getRoom_bg_url());
                Bundle paramBundle = new Bundle();
                paramBundle.putString(Constant.ROOM_ID, room.getObjectId());
                paramBundle.putString(Constant.ROOM_NAME, room.getRoom_name());
                paramBundle.putString(Constant.ROOM_URL, room.getRoom_bg_url());
                openActivity(RoomEditActivity.class, paramBundle);

            }
        });
        titleBarReturn.setOnClickListener(this);
        titleBarMore.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
    }

    private void initView() {
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        recyclerDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_device);

//        titleBarReturn.setImageResource(R.mipmap.home_menu);
        titleBarMore.setImageResource(R.mipmap.home_add);
        titleBarTitle.setVisibility(View.VISIBLE);
        titleBarTitleSpinner.setVisibility(View.GONE);
        titleBarMore.setVisibility(View.VISIBLE);
        titleBarTitle.setText(getResources().getString(R.string.Room));
        titleBarTitle.setTextColor(getResources().getColor(R.color.class_P));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_more:
                if (roomList.size() < 50) {
                    Bundle paramBundle = new Bundle();
                    paramBundle.putString(Constant.ROOM_ID, "");
                    openActivity(RoomEditActivity.class, paramBundle);
                } else {
                    XlinkUtils.shortTips(MyApplication.getMyApplication(), "房间最多添加100", 0, 0, 0, false);
                }
                break;
            case R.id.title_bar_return:
//                ((MainActivity) getActivity()).openDrawers();
                finish();
                break;
            case R.id.tv_edit:
                isEdit = !isEdit;
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


}
