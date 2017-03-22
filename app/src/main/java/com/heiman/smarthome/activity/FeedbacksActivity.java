package com.heiman.smarthome.activity;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heiman.smarthome.Constant;
import com.heiman.smarthome.R;
import com.heiman.smarthome.utils.SmartHomeUtils;
import com.heiman.smarthome.widget.button.FButton;
import com.heiman.smarthome.widget.edittext.ClearEditText;
import com.heiman.smarthome.widget.swipeback.CloseActivityClass;

import java.io.IOException;



/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/1/16 08:52
 * @Description :
 */
public class FeedbacksActivity extends SwipeBackActivity implements View.OnClickListener {

    private RelativeLayout setLayout;
    private RelativeLayout themeTable;
    private ImageView imageContent;
    private ImageButton back;
    private FButton btnConfirm;
    private Bitmap bitmap;

    private String appid = "";
    private String accesstoken = "";
    private String fileurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        CloseActivityClass.activityList.add(this);
        initView();
        initEven();
        initTheme();
    }

    private void initEven() {
//        back.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                // 用户当前为按下
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                    back.setImageResource(R.drawable.top_back);
//                }
//                // 用户当前为抬起
//                else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    finish();// 这个是关键
//                    overridePendingTransition(android.R.anim.slide_in_left,
//                            android.R.anim.slide_out_right);// 从右往左滑动动画
//                    back.setImageResource(R.drawable.top_back1);
//                }
//                return false;
//            }
//        });
        imageContent.setOnClickListener(this);

//        HttpAgent2.getInstance().onApplyPlugin(Constants.FEED_BACK_APPID, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                JLog.e("初始化反馈功能失败" + responseString);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                JLog.i("初始化反馈功能成功" + responseString);
//                Gson gson = new Gson();
//                appid = gson.fromJson("app_id", String.class);
//                accesstoken = gson.fromJson("access_token", String.class);
//            }
//        });

    }

    private void initView() {
        setLayout = (RelativeLayout) findViewById(R.id.set_layout);
        themeTable = (RelativeLayout) findViewById(R.id.theme_table);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        back = (ImageButton) findViewById(R.id.img_back_about);
        btnConfirm = (FButton) findViewById(R.id.btn_confirm);
        imageContent = (ImageView) findViewById(R.id.image_content);
    }

    private void initTheme() {
//        ThemeUtils.Theme currentTheme = ThemeUtils.getCurrentTheme(this);
//        themeTable.setBackgroundColor(getResources().getColor(
//                ThemeUtils.changeTheme(currentTheme)));
//        setLayout.setBackgroundColor(getResources().getColor(
//                ThemeUtils.changeTheme(currentTheme)));
//        btnConfirm.setButtonColor(getResources().getColor(
//                ThemeUtils.changeTheme(currentTheme)));
//        btnConfirm.setShadowEnabled(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(ThemeUtils.changeTheme(currentTheme));
        // 设置从做到右滑出
        setEdgeFromLeft();
        setOverrideExitAniamtion(false);
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private ClearEditText getEtContent() {
        return (ClearEditText) findViewById(R.id.et_Content);
    }

    private ClearEditText getEtContact() {
        return (ClearEditText) findViewById(R.id.et_Contact);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                //TODO implement

//                if (bitmap != null) {
//                    JLog.i("fileurl:" + fileurl);
//                    HttpAgent2.getInstance().upLoad(bitmap, "png", true, "fack.png", new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                            JLog.i("上传成功：" + statusCode);
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                            JLog.e("上传失败：" + statusCode);
//                        }
//
//                        @Override
//                        public void onProgress(int bytesWritten, int totalSize) {
//                            // TODO Auto-generated method stub
//                            super.onProgress(bytesWritten, totalSize);
//                            JLog.d("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
//                        }
//
//                        @Override
//                        public void onRetry(int retryNo) {
//                            // TODO Auto-generated method stub
//                            super.onRetry(retryNo);
//                            // 返回重试次数
//                        }
//
//                    });
//                } else {
//                    String Content = getEtContent().getText().toString();
//                    if (TextUtils.isEmpty(getEtContent().getText())) {
//                        // 设置晃动
//                        getEtContent().setShakeAnimation();
//                        // 设置提示
//                        XlinkUtils.shortTips(getResources().getString(R.string.Feedback_Content_isEmpty));
//                        return;
//                    }
//
//                    if (getEtContent().getText().length() < 5) {
//                        XlinkUtils.shortTips(getResources().getString(R.string.Feedback_Content_min5));
//                    }
//                    String Contact = getEtContact().getText().toString();
//                    if (TextUtils.isEmpty(getEtContact().getText())) {
//                        // 设置晃动
//                        getEtContent().setShakeAnimation();
//                        // 设置提示
//                        XlinkUtils.shortTips(getResources().getString(R.string.Feedback_Content_isEmpty));
//                        return;
//                    }
//
//                    Feedbackbean feedbackbean = new Feedbackbean();
//                    feedbackbean.setContent(Content);
//                    if (Utils.isEmial(Contact)) {
//                        feedbackbean.setEmail(Contact);
//                    } else {
//                        feedbackbean.setPhone(Contact);
//                    }
//                    feedbackbean.setLabel("");
//                    HttpAgent2.getInstance().onFeedback(accesstoken, appid, feedbackbean, new TextHttpResponseHandler() {
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                            JLog.e("上传反馈失败：" + responseString);
//                        }
//
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                            JLog.e("上传反馈成功：" + responseString);
//                        }
//                    });
//
//
//                }
                break;
            case R.id.image_content:
                //TODO implement
                pickImage(this, Constant.REQUESTCODE_UPLOADAVATAR_LOCATION);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUESTCODE_UPLOADAVATAR_LOCATION) {
                Uri uri = data.getData();
                fileurl = SmartHomeUtils.getRealFilePath(FeedbacksActivity.this, uri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageContent.setImageBitmap(getRoundedBitmap(resizeImage(bitmap, 200, 200)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void pickImage(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 图片圆角处理
     *
     * @param mBitmap 图片
     * @return
     */
    public Bitmap getRoundedBitmap(Bitmap mBitmap) {
        //创建新的位图
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //把创建的位图作为画板
        Canvas mCanvas = new Canvas(bgBitmap);

        Paint mPaint = new Paint();
        Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF mRectF = new RectF(mRect);
        //设置圆角半径为20
        float roundPx = 15;
        mPaint.setAntiAlias(true);
        //先绘制圆角矩形
        mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);

        //设置图像的叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制图像
        mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

        return bgBitmap;
    }

}

