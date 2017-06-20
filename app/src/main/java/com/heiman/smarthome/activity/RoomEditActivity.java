package com.heiman.smarthome.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.Room;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.modle.RoomEdit;
import com.heiman.utils.FileStorage;
import com.heiman.utils.permission.PermissionEnum;
import com.heiman.widget.edittext.ClearEditText;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

/**
 * @Author : 肖力
 * @Time :  2017/5/31 14:49
 * @Description :
 * @Modify record :
 */

public class RoomEditActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private TextView tvRoomDeviceNumber;
    private TextView titleTvRight;
    //    private NestedParent nestedScrollParentLayout;
    private ClearEditText etMobile;
    private FamiliarRecyclerView recyclerRoomDevice;
    private FamiliarRecyclerView recyclerNotRoomDevice;
    private RelativeLayout rlPhotograph;
    private RelativeLayout rlSelectExisting;
    private ImageView imageRoomBg;

    private FamiliarEasyAdapter<RoomEdit> mAdapter;
    private List<RoomEdit> roomList;

    private FamiliarEasyAdapter<RoomEdit> mNotAdapter;
    private List<RoomEdit> roomNotList;
    private String roomID;
    private String roomURL;
    private boolean isEdit = true;
    private static final int REQUEST_PICK_IMAGE = 1; //相册选取
    private static final int REQUEST_CAPTURE = 2;  //拍照
    private static final int REQUEST_PICTURE_CUT = 3;  //剪裁图片
    private Uri imageUri;//原图保存地址
    private boolean isClickCamera;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_edit);
        StatusBarUtil.setTranslucent(this, 50);
        initView();
        initEven();
        initData();

    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        roomID = bundle.getString(Constant.ROOM_ID);
        if (SmartHomeUtils.isEmptyString(roomID)) {
            titleBarTitle.setText(R.string.Create_room);
            isEdit = false;
        } else {
            titleBarTitle.setText(R.string.Edit_room);
            String roomName = bundle.getString(Constant.ROOM_NAME);
            roomURL = bundle.getString(Constant.ROOM_URL);
            MyApplication.getLogger().i("roomURL:" + roomURL);
            etMobile.setText(roomName);
            isEdit = true;
        }

        List<SubDevice> subDevicesList = SubDeviceManage.getInstance().getDevices();
        for (int i = 0; i < subDevicesList.size(); i++) {
            if (subDevicesList.get(i).getRoomID() == null || subDevicesList.get(i).getRoomID().equals("")) {
                roomNotList.add(new RoomEdit(true, subDevicesList.get(i).getDeviceMac(), subDevicesList.get(i).getDeviceType(), subDevicesList.get(i).getDeviceName()));
            } else if (subDevicesList.get(i).getRoomID().equals(roomID)) {
                roomList.add(new RoomEdit(true, subDevicesList.get(i).getDeviceMac(), subDevicesList.get(i).getDeviceType(), subDevicesList.get(i).getDeviceName()));
            }
        }
        List<XlinkDevice> xlinkDevicesList = DeviceManage.getInstance().getDevices();
        for (int i = 0; i < xlinkDevicesList.size(); i++) {
            if (xlinkDevicesList.get(i).getRoomID() == null || xlinkDevicesList.get(i).getRoomID().equals("")) {
                roomNotList.add(new RoomEdit(true, xlinkDevicesList.get(i).getDeviceMac(), xlinkDevicesList.get(i).getDeviceType(), xlinkDevicesList.get(i).getDeviceName()));
            } else if (subDevicesList.get(i).getRoomID().equals(roomID)) {
                roomList.add(new RoomEdit(true, subDevicesList.get(i).getDeviceMac(), subDevicesList.get(i).getDeviceType(), subDevicesList.get(i).getDeviceName()));
            }
        }
        Message message = new Message();
        message.what = 1;
        myHandler.sendMessage(message);
    }

    private void initEven() {
        roomList = new ArrayList<RoomEdit>();
        mAdapter = new FamiliarEasyAdapter<RoomEdit>(this, R.layout.item_room_edit, roomList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final RoomEdit roomEdit = roomList.get(position);
                ImageButton imageLeft = (ImageButton) holder.findView(R.id.image_Left);
                ImageView imageDeviceIcon = (ImageView) holder.findView(R.id.image_device_icon);
                TextView tvDeviceName = (TextView) holder.findView(R.id.tv_device_name);

                imageDeviceIcon.setImageResource(SmartHomeUtils.typeToIcon(roomEdit.isSub(), roomEdit.getDeviceType()));
                tvDeviceName.setText(roomEdit.getDeviceName());
                imageLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.getLogger().i("点击：" + roomEdit.getDeviceMac());
                        roomNotList.add(roomEdit);
                        roomList.remove(position);
                        Message message = new Message();
                        message.what = 1;
                        myHandler.sendMessage(message);
                    }
                });

            }
        };
        recyclerRoomDevice.setAdapter(mAdapter);


        roomNotList = new ArrayList<RoomEdit>();
        mNotAdapter = new FamiliarEasyAdapter<RoomEdit>(this, R.layout.item_room_edit, roomNotList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final RoomEdit roomEdit = roomNotList.get(position);
                ImageButton imageLeft = (ImageButton) holder.findView(R.id.image_Left);
                ImageView imageDeviceIcon = (ImageView) holder.findView(R.id.image_device_icon);
                TextView tvDeviceName = (TextView) holder.findView(R.id.tv_device_name);

                imageDeviceIcon.setImageResource(SmartHomeUtils.typeToIcon(roomEdit.isSub(), roomEdit.getDeviceType()));
                tvDeviceName.setText(roomEdit.getDeviceName());
                imageLeft.setImageResource(R.drawable.room_add);
                imageLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.getLogger().i("点击：" + roomEdit.getDeviceMac());
                        roomList.add(roomEdit);
                        roomNotList.remove(position);
                        Message message = new Message();
                        message.what = 1;
                        myHandler.sendMessage(message);
                    }
                });
            }
        };
        recyclerNotRoomDevice.setAdapter(mNotAdapter);

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvRoomDeviceNumber.setText("房间内包含" + roomList.size() + "个设备");
                    mAdapter.notifyDataSetChanged();
                    mNotAdapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void initView() {


        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        titleTvRight = (TextView) findViewById(R.id.title_tv_Right);

        tvRoomDeviceNumber = (TextView) findViewById(R.id.tv_room_device_number);

//        nestedScrollParentLayout = (NestedParent) findViewById(R.id.nested_scroll_parent_layout);
        etMobile = (ClearEditText) findViewById(R.id.et_mobile);
        recyclerRoomDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_room_device);
        recyclerNotRoomDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_not_room_device);

        rlPhotograph = (RelativeLayout) findViewById(R.id.rl_photograph);
        rlSelectExisting = (RelativeLayout) findViewById(R.id.rl_select_existing);
        imageRoomBg = (ImageView) findViewById(R.id.image_room_bg);

//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
//        recyclerRoomDevice.setLayoutManager(linearLayoutManager);
//        recyclerRoomDevice.setNestedScrollingEnabled(false);
//        recyclerNotRoomDevice.setLayoutManager(linearLayoutManager);
//        recyclerNotRoomDevice.setNestedScrollingEnabled(false);

        titleBarTitle.setVisibility(View.VISIBLE);
        titleBarTitleSpinner.setVisibility(View.GONE);
        titleBarMore.setVisibility(View.GONE);

        titleBarTitle.setTextColor(getResources().getColor(R.color.class_P));
        titleTvRight.setText(R.string.Save);
        titleTvRight.setTextColor(getResources().getColor(R.color.class_text_17));
        titleTvRight.setVisibility(View.VISIBLE);

        titleBarReturn.setOnClickListener(this);
        rlPhotograph.setOnClickListener(this);
        rlSelectExisting.setOnClickListener(this);
        titleTvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_return:
                finish();
                break;
            case R.id.rl_photograph:
                checkPermissions(PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.WRITE_EXTERNAL_STORAGE, PermissionEnum.CAMERA);
                openCamera();
                isClickCamera = true;
                break;
            case R.id.rl_select_existing:
                isClickCamera = false;
                checkPermissions(PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.WRITE_EXTERNAL_STORAGE);
                selectFromAlbum();
                break;
            case R.id.title_tv_Right:
                showHUDProgress(getString(R.string.saves));
                Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // 这里作判断，决定要不要排除该字段,return true为排除
                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose != null && expose.deserialize() == true) return true; //按注解排除
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 直接排除某个类 ，return true为排除
                        return (clazz == int.class || clazz == Integer.class);
                    }
                }).create();

                String data = gson.toJson(new Room(etMobile.getText().toString().trim(), roomURL));
                if (!isEdit) {
                    HttpManage.getInstance().postData(MyApplication.getMyApplication(), data, "Room", new HttpManage.ResultCallback<String>() {
                        @Override
                        public void onError(Header[] headers, HttpManage.Error error) {
                            MyApplication.getLogger().e("msg:" + error.getMsg() + "\tcode:" + error.getCode());
                            XlinkUtils.shortTips(MyApplication.getMyApplication(), SmartHomeUtils.showHttpCode(error.getCode()), 0, 0, 0, false);
                            dismissHUMProgress();
                        }

                        @Override
                        public void onSuccess(int code, String response) {
                            MyApplication.getLogger().json(response);
                            dismissHUMProgress();
                            finish();
                        }
                    });
                } else {
                    HttpManage.getInstance().putData(MyApplication.getMyApplication(), data, "Room", roomID, new HttpManage.ResultCallback<String>() {
                        @Override
                        public void onError(Header[] headers, HttpManage.Error error) {
                            MyApplication.getLogger().e("msg:" + error.getMsg() + "\tcode:" + error.getCode());
                            XlinkUtils.shortTips(MyApplication.getMyApplication(), SmartHomeUtils.showHttpCode(error.getCode()), 0, 0, 0, false);
                            dismissHUMProgress();
                        }

                        @Override
                        public void onSuccess(int code, String response) {
                            MyApplication.getLogger().json(response);
                            dismissHUMProgress();
                            finish();
                        }
                    });
                }
                break;
        }
    }

    /**
     * 打开系统相机
     */
    private void openCamera() {
        File file = new FileStorage().createIconFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(RoomEditActivity.this, "com.heiman.smarthome.fileProvider", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 从相册选择
     */
    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    /**
     * 裁剪
     */
    private void cropPhoto() {
        File file = new FileStorage().createCropFile();
        Uri outputUri = Uri.fromFile(file);//缩略图保存地址
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_PICTURE_CUT);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        imageUri = data.getData();
        if (DocumentsContract.isDocumentUri(this, imageUri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(imageUri);
            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(imageUri, null);
        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = imageUri.getPath();
        }

        cropPhoto();
    }

    private void handleImageBeforeKitKat(Intent intent) {
        imageUri = intent.getData();
        imagePath = getImagePath(imageUri, null);
        cropPhoto();
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_IMAGE://从相册选择
                if (Build.VERSION.SDK_INT >= 19) {
                    handleImageOnKitKat(data);
                } else {
                    handleImageBeforeKitKat(data);
                }
                break;
            case REQUEST_CAPTURE://拍照
                if (resultCode == RESULT_OK) {
                    cropPhoto();
                }
                break;
            case REQUEST_PICTURE_CUT://裁剪完成
                Bitmap bitmap = null;
                try {
                    if (isClickCamera) {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } else {
                        bitmap = BitmapFactory.decodeFile(imagePath);
                    }
                    imageRoomBg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
