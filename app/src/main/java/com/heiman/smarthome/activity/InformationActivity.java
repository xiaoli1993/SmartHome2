package com.heiman.smarthome.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;

public class InformationActivity extends Activity {

    private ImageView imgTittleBack;
    private TextView txtSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        imgTittleBack = (ImageView) findViewById(R.id.img_tittle_back);
        txtSave = (TextView) findViewById(R.id.txt_save);

        imgTittleBack.setOnClickListener(mOnClickListener);
        txtSave.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!UsefullUtill.judgeClick(R.layout.activity_information, 500)) {
                LogUtil.e("点击过快");
                return;
            }
            switch (v.getId()) {
                case R.id.img_tittle_back:
                    onBackPressed();
                    break;
                case R.id.txt_save:

                    break;
            }
        }
    };
}
