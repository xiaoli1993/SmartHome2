package com.heiman.smarthome.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.AsyncBitmapLoader;
import com.heiman.utils.SmartHomeSave;
import com.heiman.utils.UsefullUtill;
import com.heiman.widget.UCropView.WeiChatCropActivity;
import com.kevin.crop.UCrop;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import top.zibin.luban.OnCompressListener;

public class UserInfoActivity extends BaseActivity {

    private TextView txtHead;
    private TextView txtHeadRight;
    private TextView txtNickName;
    private TextView txtNickNameInfo;
    private TextView txtAccount;
    private TextView txtEmail;
    private TextView txtPhoneNumber;
    private TextView txtBirthday;
    private TextView txtGender;
    private TextView txtGenderInfo;
    private TextView txtAuthorizedLogin;
    private TextView txtAuthorizedLoginInfo;
    private ImageView imgHead;
    private TextView txtBirthdayInfo;
    private String nickName;

    // 剪切后图像文件
    private Uri mDestinationUri;
    String tempPhotoPath;


    private UserInfo userInfo;

    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.getInstance();

    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int GALLERY_REQUEST_CODE = 2;

    private final static int MSG_RESET_NICK_NAME_SUCCEED = 10000;
    private final static int MSG_RESET_NICK_NAME_FAIL = 10001;
    private final static int MSG_RESET_GENDER_FAIL = 10002;
    private final static int MSG_RESET_GENDER_SUCCEED = 10003;
    private final static int MSG_REST_UPLOAD_HEADER_PHOTO = 10004;
    private final static int MSG_SET_HEADER_PHOTO = 10005;
    private final static int MSG_DOWNLOAD_HEADER_PHOTO_COMPLETE = 10006;
    private final static int MSG_RESET_HEAD_PHOTO_SUCCEED = 10007;
    private final static int MSG_RESETHEAD_PHOTO_FAIL = 10008;
    private final static int MSG_RESET_BIRTHDAY_SUCCEED = 10009;
    private final static int MSG_RESET_BIRTHDAY_FAIL = 10010;
    private final static int MSG_INIT_USERINFO = 10011;
    private final static int MSG_GET_GENDER_SUCCEED = 10012;
    private final static int MSG_GET_BIRTHDAY_SUCCEED = 10013;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RESET_NICK_NAME_SUCCEED:
                    Toast.makeText(UserInfoActivity.this, R.string.toast_reset_nick_name_succeed, Toast.LENGTH_SHORT).show();
                    txtNickNameInfo.setText(nickName);
                    userInfo.setNickname(nickName);
                    MyApplication.getMyApplication().setUserInfo(userInfo);
                    break;
                case MSG_RESET_NICK_NAME_FAIL:
                    String str = msg.getData().getString("str");
                    if (TextUtils.isEmpty(str)) {
                        Toast.makeText(UserInfoActivity.this, R.string.reset_nick_name_fail, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserInfoActivity.this, str, Toast.LENGTH_LONG).show();
                    }
                    break;
                case MSG_RESET_GENDER_SUCCEED:
                    Toast.makeText(UserInfoActivity.this, R.string.reset_gender_succeed, Toast.LENGTH_LONG).show();
                    break;
                case MSG_RESET_GENDER_FAIL:
                    String string = msg.getData().getString("str");
                    Toast.makeText(UserInfoActivity.this, string, Toast.LENGTH_LONG).show();
                    break;
                case MSG_REST_UPLOAD_HEADER_PHOTO:
                    File file = new File(SmartHomeSave.getSmarthomePhotoPath() + File.separator + "header.jpg");

                    if (file.exists()) {
                        OnCompressListener onCompressListener = new OnCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                                if (bitmap != null) {
                                    HttpManage.getInstance().uploadHeadportrait(MyApplication.getMyApplication(), bitmap, upLoadPhotoCallback);
                                } else {
                                    MyApplication.getLogger().e("bitmap is null!");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        };
                        UsefullUtill.photoCompress(UserInfoActivity.this, file, onCompressListener);
                    }
                    break;
                case MSG_SET_HEADER_PHOTO:
                    String imgUrl = userInfo.getAvatar();
                    if (!TextUtils.isEmpty(imgUrl)) {
                        Bitmap bitmap = asyncBitmapLoader.loadBitmap(imgHead, imgUrl, new AsyncBitmapLoader.ImageCallback() {
                            @Override
                            public void imageLoad(View view, Bitmap bitmap) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("view", view);
                                map.put("bitmap", bitmap);
                                Message msg = new Message();
                                msg.what = MSG_DOWNLOAD_HEADER_PHOTO_COMPLETE;
                                msg.obj = map;
                                mHandler.sendMessage(msg);
                            }
                        });
                        if (bitmap != null) {
                            bitmap = UsefullUtill.toRoundBitmap(bitmap);
                            imgHead.setImageBitmap(bitmap);
                        }
                    }
                    break;
                case MSG_DOWNLOAD_HEADER_PHOTO_COMPLETE:
                    if (msg.obj != null && msg.obj instanceof HashMap) {
                        HashMap map = (HashMap) msg.obj;
                        Object object = map.get("bitmap");
                        if (object != null && object instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) object;
                            bitmap = UsefullUtill.toRoundBitmap(bitmap);
                            imgHead.setImageBitmap(bitmap);
                        }
                    }
                    break;
                case MSG_RESET_HEAD_PHOTO_SUCCEED:
                    Toast.makeText(UserInfoActivity.this, R.string.reset_head_photo_succeed, Toast.LENGTH_LONG).show();
                    mHandler.sendEmptyMessage(MSG_SET_HEADER_PHOTO);
                    break;
                case MSG_RESETHEAD_PHOTO_FAIL:

                    if (msg.obj != null) {
                        Toast.makeText(UserInfoActivity.this, getString(R.string.reset_head_photo_fail) + ":" + msg.obj.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UserInfoActivity.this, getString(R.string.reset_head_photo_fail) + "!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case MSG_RESET_BIRTHDAY_FAIL:

                    if (msg.obj != null) {
                        Toast.makeText(UserInfoActivity.this, getString(R.string.reset_birthday_fail) + ":" + msg.obj.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UserInfoActivity.this, getString(R.string.reset_birthday_fail) + "!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case MSG_RESET_BIRTHDAY_SUCCEED:
                    Toast.makeText(UserInfoActivity.this, R.string.reset_birthday_succeed, Toast.LENGTH_LONG).show();
                    break;
                case MSG_INIT_USERINFO:
                    userInfo = MyApplication.getMyApplication().getUserInfo();
                    if (userInfo != null) {
                        getBirthday();
                        getGender();
                        txtNickNameInfo.setText(userInfo.getNickname());
                        txtAccount.setText(userInfo.getAccount());
                        txtEmail.setText(userInfo.getEmail());
                        txtPhoneNumber.setText(userInfo.getPhone());
                        txtBirthdayInfo.setText("");
                        mHandler.sendEmptyMessage(MSG_SET_HEADER_PHOTO);

                        if (userInfo.getGender() == 1) {
                            txtGenderInfo.setText(R.string.txt_gender_male);
                        } else if (userInfo.getGender() == 2) {
                            txtGenderInfo.setText(R.string.txt_gender_female);
                        } else {
                            txtGenderInfo.setText("");
                        }
                    } else {
                        mHandler.sendEmptyMessageDelayed(MSG_INIT_USERINFO, 100);
                    }
                    break;
                case MSG_GET_GENDER_SUCCEED:

                    if (msg.obj != null) {
                        String gender = msg.obj.toString();
                        if ("1".equals(gender)) {
                            txtGenderInfo.setText(R.string.txt_gender_male);
                        } else if ("2".equals(gender)) {
                            txtGenderInfo.setText(R.string.txt_gender_female);
                        } else {
                            txtGenderInfo.setText("");
                        }
                    }
                    break;
                case MSG_GET_BIRTHDAY_SUCCEED:

                    if (msg.obj != null) {
                        txtBirthdayInfo.setText(msg.obj.toString());
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_info);
        setContentLayout(R.layout.activity_user_info);
        MyApplication.getLogger().e("onCreate");
        txtHead = (TextView) findViewById(R.id.txt_head);
        txtHeadRight = (TextView) findViewById(R.id.txt_head_right);
        txtNickName = (TextView) findViewById(R.id.txt_nick_name);
        txtNickNameInfo = (TextView) findViewById(R.id.txt_nick_name_info);
        txtAccount = (TextView) findViewById(R.id.txt_account_info);
        txtEmail = (TextView) findViewById(R.id.txt_email_info);
        txtPhoneNumber = (TextView) findViewById(R.id.txt_phone_number_info);
        txtBirthday = (TextView) findViewById(R.id.txt_birthday);
        txtBirthdayInfo = (TextView) findViewById(R.id.txt_birthday_info);
        txtGender = (TextView) findViewById(R.id.txt_gender);
        txtGenderInfo = (TextView) findViewById(R.id.txt_gender_info);
        txtAuthorizedLogin = (TextView) findViewById(R.id.txt_authorized_login);
        txtAuthorizedLoginInfo = (TextView) findViewById(R.id.txt_authorized_login_info);
        imgHead = (ImageView) findViewById(R.id.img_head);

        txtHead.setOnClickListener(mOnClickListener);
        txtHeadRight.setOnClickListener(mOnClickListener);
        txtNickName.setOnClickListener(mOnClickListener);
        txtNickNameInfo.setOnClickListener(mOnClickListener);
        txtGender.setOnClickListener(mOnClickListener);
        txtGenderInfo.setOnClickListener(mOnClickListener);
        txtAuthorizedLogin.setOnClickListener(mOnClickListener);
        txtAuthorizedLoginInfo.setOnClickListener(mOnClickListener);
        imgHead.setOnClickListener(mOnClickListener);
        txtBirthday.setOnClickListener(mOnClickListener);
        txtBirthdayInfo.setOnClickListener(mOnClickListener);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_user_info));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getLogger().e("onResume");
        mHandler.sendEmptyMessage(MSG_INIT_USERINFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (!TextUtils.isEmpty(tempPhotoPath)) {
                        Uri photoUri = Uri.fromFile(new File(tempPhotoPath));
                        mDestinationUri = Uri.fromFile(new File(SmartHomeSave.getSmarthomePhotoPath() + File.separator + "header.jpg"));
                        startCropActivity(photoUri);
                    } else {
                        Toast.makeText(this, R.string.crop_photo_err, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case GALLERY_REQUEST_CODE:
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        mDestinationUri = Uri.fromFile(new File(SmartHomeSave.getSmarthomePhotoPath() + File.separator + "header.jpg"));
                        startCropActivity(data.getData());
                    } else {
                        Toast.makeText(this, R.string.crop_photo_err, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    mHandler.sendEmptyMessage(MSG_REST_UPLOAD_HEADER_PHOTO);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!UsefullUtill.judgeClick(R.layout.activity_user_info, 500)) {
                MyApplication.getLogger().e("点击过快！");
                return;
            }
            switch (v.getId()) {
                case R.id.txt_head:
                case R.id.txt_head_right:
                case R.id.img_head:
                    showSetHeadPicDialog();
                    break;
                case R.id.txt_nick_name:
                case R.id.txt_nick_name_info:
                    showResetNickNameDialog();
                    break;
                case R.id.txt_birthday:
                case R.id.txt_birthday_info:
                    showSettingBirthday();
                    break;
                case R.id.txt_gender:
                case R.id.txt_gender_info:
                    showGenderSelectorDialog();
                    break;
                case R.id.txt_authorized_login:
                case R.id.txt_authorized_login_info:
                    Intent intent = new Intent(UserInfoActivity.this, AuthorizeManaActivity.class);
                    startActivity(intent);
                    break;
                case R.id.reset_nick_name_btn_cancel:
                    dismissDialog(v);
                    break;
                case R.id.reset_nick_name_btn_confirm:
                    Object o = v.getTag(R.id.edit_nick_name);

                    if (o != null && o instanceof EditText) {
                        EditText editNickName = (EditText) o;
                        nickName = editNickName.getText().toString();
                        MyApplication.getLogger().e("nickName:" + nickName);
                        HttpManage.getInstance().modifyUser(MyApplication.getMyApplication(), nickName, resetNickNameCallback);
                    }
                    dismissDialog(v);
                    break;
                case R.id.btn_phone_takephoto:
                    dismissDialog(v);
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    tempPhotoPath = SmartHomeSave.getSmarthomePhotoPath() + File.separator + System.currentTimeMillis() + ".jpg";
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempPhotoPath)));
                    startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
                    break;
                case R.id.btn_local_photo:
                    dismissDialog(v);
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
                    break;
                case R.id.set_head_pic_dialog_btn_cancel:
                    dismissDialog(v);
                    break;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.rbtn_male:
                    if (isChecked) {
                        txtGenderInfo.setText(R.string.txt_gender_male);
                        HttpManage.getInstance().setProperty(MyApplication.getMyApplication(), "Gender", 1 + "", setGenderCallback);
                        userInfo.setGender(1);
                    }
                    dismissDialog(buttonView);
                    break;
                case R.id.rbtn_female:
                    if (isChecked) {
                        txtGenderInfo.setText(R.string.txt_gender_female);
                        HttpManage.getInstance().setProperty(MyApplication.getMyApplication(), "Gender", 2 + "", setGenderCallback);
                        userInfo.setGender(2);
                    }
                    dismissDialog(buttonView);
                    break;
            }
        }
    };

    private void showSettingBirthday() {
        Calendar calendar = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                try {
                    String dateStr = DateFormat.format("yyyyy-MM-dd", date).toString();
                    MyApplication.getLogger().e("date:" + dateStr);
                    txtBirthdayInfo.setText(dateStr);
                    HttpManage.getInstance().setProperty(MyApplication.getMyApplication(), "Birthday", dateStr, setBirthdayCallback);
                } catch (Exception e) {
                    MyApplication.getLogger().e(e.getMessage());
                }
            }
        }).setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR))
                .setType(new boolean[]{true,true,true,false,false,false})
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void showResetNickNameDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_reset_nick_name_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialog_exit_account)
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        Button btnCancel = (Button) view.findViewById(R.id.reset_nick_name_btn_cancel);
        Button btnComfirm = (Button) view.findViewById(R.id.reset_nick_name_btn_confirm);
        EditText editNickName = (EditText) view.findViewById(R.id.edit_nick_name);
        btnCancel.setTag(dialog);
        btnComfirm.setTag(dialog);
        btnComfirm.setTag(R.id.edit_nick_name, editNickName);
        btnCancel.setOnClickListener(mOnClickListener);
        btnComfirm.setOnClickListener(mOnClickListener);
        dialog.show();
    }

    private void showSetHeadPicDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_set_head_pic_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialog_set_head_pic)
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        Button btnTakePhoto = (Button) view.findViewById(R.id.btn_phone_takephoto);
        Button btnLocalPhoto = (Button) view.findViewById(R.id.btn_local_photo);
        Button btnCancel = (Button) view.findViewById(R.id.set_head_pic_dialog_btn_cancel);
        btnTakePhoto.setTag(dialog);
        btnLocalPhoto.setTag(dialog);
        btnCancel.setTag(dialog);
        btnTakePhoto.setOnClickListener(mOnClickListener);
        btnLocalPhoto.setOnClickListener(mOnClickListener);
        btnCancel.setOnClickListener(mOnClickListener);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void showGenderSelectorDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_gender_selector_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialog_exit_account)
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        RadioButton rbtnMale = (RadioButton) view.findViewById(R.id.rbtn_male);
        RadioButton rbtnFeMale = (RadioButton) view.findViewById(R.id.rbtn_female);
        RadioGroup rgpGenderSelector = (RadioGroup) view.findViewById(R.id.rgp_gender_selector);
        rbtnMale.setTag(dialog);
        rbtnMale.setTag(R.id.rgp_gender_selector, rgpGenderSelector);
        rbtnFeMale.setTag(dialog);
        rbtnFeMale.setTag(R.id.rgp_gender_selector, rgpGenderSelector);
        dialog.show();
        MyApplication.getLogger().e("gender:" + userInfo.getGender());
        if (userInfo.getGender() == 1) {
            rbtnMale.setChecked(true);
        } else if (userInfo.getGender() == 2) {
            rbtnFeMale.setChecked(true);
        }
        rbtnMale.setOnCheckedChangeListener(mOnCheckedChangeListener);
        rbtnFeMale.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private void dismissDialog(View v) {
        Object o = v.getTag();

        if (o != null && o instanceof AlertDialog) {
            AlertDialog dialog = (AlertDialog) o;
            dialog.dismiss();
        }
    }

    private HttpManage.ResultCallback<String> resetNickNameCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
                Message msg = new Message();
                msg.what = MSG_RESET_NICK_NAME_FAIL;
                Bundle bundle = new Bundle();
                bundle.putString("str", getString(R.string.toast_reset_nick_name_fail) + SmartHomeUtils.showHttpCode(error.getCode()));
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            } else {
                mHandler.sendEmptyMessage(MSG_RESET_NICK_NAME_FAIL);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            MyApplication.getLogger().e("code:" + code + "\tresponse:" + response);
            mHandler.sendEmptyMessage(MSG_RESET_NICK_NAME_SUCCEED);
        }
    };

    private HttpManage.ResultCallback<String> setGenderCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
                Message msg = new Message();
                msg.what = MSG_RESET_GENDER_FAIL;
                Bundle bundle = new Bundle();
                bundle.putString("str", getString(R.string.reset_gender_fail) + SmartHomeUtils.showHttpCode(error.getCode()));
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            MyApplication.getLogger().e("code:" + code + "\tresponse:" + response);
            mHandler.sendEmptyMessage(MSG_RESET_GENDER_SUCCEED);
        }
    };

    private HttpManage.ResultCallback<String> setBirthdayCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
                Message msg = new Message();
                msg.what = MSG_RESET_BIRTHDAY_FAIL;
                msg.obj = SmartHomeUtils.showHttpCode(error.getCode());
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            MyApplication.getLogger().e("code:" + code + "\tresponse:" + response);
            mHandler.sendEmptyMessage(MSG_RESET_BIRTHDAY_SUCCEED);
        }
    };

    /**
     * 开始剪切图片
     *
     * @param uri
     */
    private void startCropActivity(Uri uri) {
        UCrop uCrop = UCrop.of(uri, mDestinationUri)
                .withTargetActivity(WeiChatCropActivity.class)
                .withAspectRatio(1, 1);
//                .withMaxResultSize(500, 500)

        uCrop.start(this);
    }

    private HttpManage.ResultCallback<String> upLoadPhotoCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
                Message msg = new Message();
                msg.what = MSG_RESETHEAD_PHOTO_FAIL;
                msg.obj = SmartHomeUtils.showHttpCode(error.getCode());
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            MyApplication.getLogger().e("code:" + code + "\tresponse:" + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("url")) {
                    response = jsonObject.getString("url");
                }
            } catch (JSONException e) {
                MyApplication.getLogger().e(e.getMessage());
            }
            String url = "http://static.heimantech.com" + response.substring(response.lastIndexOf("/"));
            userInfo.setAvatar(url);
            MyApplication.getMyApplication().setUserInfo(userInfo);
            mHandler.sendEmptyMessage(MSG_RESET_HEAD_PHOTO_SUCCEED);
        }
    };

    private HttpManage.ResultCallback<String> getBirthdayCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            MyApplication.getLogger().e("code:" + code + "\tresponse:" + response);
            Message msg = new Message();
            msg.what = MSG_GET_BIRTHDAY_SUCCEED;
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("Birthday")) {
                    msg.obj = jsonObject.getString("Birthday");
                }
            } catch (JSONException e) {
                MyApplication.getLogger().e(e.getMessage());
            }
            mHandler.sendMessage(msg);
        }
    };

    private void getBirthday() {
        HttpManage.getInstance().getOneProperty(MyApplication.getMyApplication(), "Birthday", getBirthdayCallback);
    }

    private HttpManage.ResultCallback<String> getGenderCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            MyApplication.getLogger().e("code:" + code + "\tresponse:" + response);
            Message msg = new Message();
            msg.what = MSG_GET_GENDER_SUCCEED;
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("Gender")) {
                    msg.obj = jsonObject.getString("Gender");
                }
            } catch (JSONException e) {
                MyApplication.getLogger().e(e.getMessage());
            }
            mHandler.sendMessage(msg);
        }
    };

    private void getGender() {
        HttpManage.getInstance().getOneProperty(MyApplication.getMyApplication(), "Gender", getGenderCallback);
    }
}
