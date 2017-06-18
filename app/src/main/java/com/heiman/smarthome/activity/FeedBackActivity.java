package com.heiman.smarthome.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.SmartHomeSave;
import com.heiman.utils.UsefullUtill;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import top.zibin.luban.OnCompressListener;

public class FeedBackActivity extends BaseActivity {

    private EditText editFeedbackContent;
    private EditText editContactWay;
    private Button btnSubmit;
    private UserInfo userInfo;
    private ImageButton imgBtnChoosePhoto;
    private final static String LABEL = "意见反馈";
    private String tempPhotoPath;
    private List<String> localImgPath = new ArrayList<>();
    private List<String> netImgPath = new ArrayList<>();
    private List<String> compressedImgPath = new ArrayList<>();
    private String tempImgPath = null;

    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int GALLERY_REQUEST_CODE = 2;

    private static final int MSG_SUBMIT_FEEDBACK = 10000;
    private static final int MSG_IMAGE_COMPRESS_COMPLETE = 10001;
    private static final int MSG_SELET_IMAGE_FILE_TOO_LARGE = 10002;
    private static final int MSG_SUBMIT_FEEDBACK_COMPLETE = 10003;
    private static final int MSG_SUBMIT_FEEDBACK_FAIL = 10004;
    private static final int MSG_GET_USERINFO = 10005;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUBMIT_FEEDBACK:
                    submitFeedback();
                    break;
                case MSG_SELET_IMAGE_FILE_TOO_LARGE:
                    dismissHUMProgress();
                    Toast.makeText(FeedBackActivity.this, R.string.select_photo_too_large, Toast.LENGTH_LONG).show();
                    break;
                case MSG_IMAGE_COMPRESS_COMPLETE:
                    uploadSeletedImg();
                    break;
                case MSG_SUBMIT_FEEDBACK_COMPLETE:
                    dismissHUMProgress();
                    Toast.makeText(FeedBackActivity.this, R.string.submit_feedback_complete, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MSG_SUBMIT_FEEDBACK_FAIL:
                    dismissHUMProgress();

                    if (msg.obj != null) {
                        String errStr = msg.obj.toString();
                        Toast.makeText(FeedBackActivity.this, getString(R.string.submit_feedback_fail) + "：" + errStr, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FeedBackActivity.this, getString(R.string.submit_feedback_fail) + "！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case MSG_GET_USERINFO:
                    userInfo = MyApplication.getMyApplication().getUserInfo();
                    if (userInfo != null) {
                        HttpManage.getInstance().feedbackList(MyApplication.getMyApplication(), userInfo.getId() + "", 0, 10, feedbackListCallback);
                    } else {
                        mHandler.sendEmptyMessageDelayed(MSG_GET_USERINFO, 100);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed_back);
        setContentLayout(R.layout.activity_feed_back);
        editFeedbackContent = (EditText) findViewById(R.id.edit_feed_back_content);
        editContactWay = (EditText) findViewById(R.id.edit_contact_way);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        imgBtnChoosePhoto = (ImageButton) findViewById(R.id.ivbtn_choose_photo);

        btnSubmit.setOnClickListener(mOnClickListener);
        imgBtnChoosePhoto.setOnClickListener(mOnClickListener);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_feed_back));
//        setReturnImage(R.drawable.back_black);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("onResume");
        mHandler.sendEmptyMessage(MSG_GET_USERINFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (!TextUtils.isEmpty(tempPhotoPath)) {
                        localImgPath.add(new String(tempPhotoPath));
                    } else {
                        Toast.makeText(this, R.string.get_photo_err, Toast.LENGTH_LONG).show();
                    }
                    break;
                case GALLERY_REQUEST_CODE:
                    Uri selectedUri = data.getData();

                    if (selectedUri != null) {
                        String path = getRealFilePath(selectedUri);
                        LogUtil.e("path:" + path);
                        localImgPath.add(path);
                    } else {
                        Toast.makeText(this, R.string.get_photo_err, Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!UsefullUtill.judgeClick(R.layout.activity_feed_back, 500)) {
                LogUtil.e("点击过快！");
                return;
            }
            switch (v.getId()) {
                case R.id.btn_submit:
                    String content = editFeedbackContent.getText().toString();
                    if (TextUtils.isEmpty(content) || content.length() < 5) {
                        Toast.makeText(FeedBackActivity.this, R.string.feedback_content_too_less, Toast.LENGTH_LONG).show();
                        return;
                    }
                    showHUDProgress();
                    compressSelectedImage();
                    break;
                case R.id.ivbtn_choose_photo:
                    if (localImgPath.size() >= 3) {
                        Toast.makeText(FeedBackActivity.this, R.string.select_photo_too_much, Toast.LENGTH_LONG).show();
                        return;
                    }
                    showTakePhotOrSelectPhotoDialog();
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

    private HttpManage.ResultCallback<String> feedbackInsertCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                String errStr = SmartHomeUtils.showHttpCode(error.getCode());
                LogUtil.e("Code:" + error.getCode() + errStr);
                Message msg = new Message();
                msg.what = MSG_SUBMIT_FEEDBACK_FAIL;
                msg.obj = errStr;
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            LogUtil.e("code:" + code + "\tresponse:" + response);
            mHandler.sendEmptyMessage(MSG_SUBMIT_FEEDBACK_COMPLETE);
        }
    };

    private HttpManage.ResultCallback<String> feedbackListCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                String errStr = SmartHomeUtils.showHttpCode(error.getCode());
                LogUtil.e("Code:" + error.getCode() + errStr);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            LogUtil.e("code:" + code + "\tresponse:" + response);
        }
    };

    private void showTakePhotOrSelectPhotoDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_set_head_pic_dialog, null);
        Dialog dialog = new Dialog(this, R.style.dialog_set_head_pic);
        dialog.setContentView(view);
        dialog.setCancelable(true);
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

    private void dismissDialog(View v) {
        Object o = v.getTag();

        if (o != null && o instanceof Dialog) {
            Dialog dialog = (Dialog) o;
            dialog.dismiss();
        }
    }

    private void uploadSeletedImg() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (compressedImgPath.size() > 0) {
                        String path = compressedImgPath.remove(0);
                        String netPath = HttpManage.getInstance().feedbackUploadPhoto(new File(path));
                        netImgPath.add(netPath);
                    }
                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                }
                mHandler.sendEmptyMessage(MSG_SUBMIT_FEEDBACK);
            }
        };
        Executors.newCachedThreadPool().execute(runnable);
    }

    private void submitFeedback () {
        String content = editFeedbackContent.getText().toString();
        String contact = editContactWay.getText().toString();
        String userId = "";
        String images = "";

        if (userInfo != null) {
            userId = userInfo.getId() + "";
        }

        if (TextUtils.isEmpty(content) || content.length() < 5) {
            Toast.makeText(FeedBackActivity.this, R.string.feedback_content_too_less, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (netImgPath.size() > 0) {
                images = netImgPath.remove(0);
            }

            while (netImgPath.size() > 0) {
                images = images + ";" + netImgPath.remove(0);
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }

        HttpManage.getInstance().feedbackInsert(MyApplication.getMyApplication(), userId,
                content, LABEL, contact, images, feedbackInsertCallback);
        editFeedbackContent.setText("");
        editContactWay.setText("");
        localImgPath.clear();
        compressedImgPath.clear();
        netImgPath.clear();
    }

    private void compressSelectedImage() {

        OnCompressListener onCompressListener = new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                LogUtil.e("onSuccess");
                if (file.length() < HttpManage.MAX_UPLOAD_IMAGE_FILE_SIZE) {
                    compressedImgPath.add(file.getPath());
                    compressSelectedImage();
                } else {
                    mHandler.sendEmptyMessage(MSG_SELET_IMAGE_FILE_TOO_LARGE);
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("onError");
                mHandler.sendEmptyMessage(MSG_SELET_IMAGE_FILE_TOO_LARGE);
            }
        };

        if (localImgPath.size() > 0) {
            tempImgPath = localImgPath.remove(0);
            File file = new File(tempImgPath);
            if (file.exists()) {
                UsefullUtill.photoCompress(this, file, onCompressListener);
            } else {
                compressSelectedImage();
            }
        } else {
            mHandler.sendEmptyMessage(MSG_IMAGE_COMPRESS_COMPLETE);
        }
    }

    public String getRealFilePath(Uri uri ) {

        if ( null == uri ) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = "";

        if ( scheme == null ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );

            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );

                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
