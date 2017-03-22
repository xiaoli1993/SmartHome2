package com.heiman.smarthome.activity;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.heiman.smarthome.R;
import com.heiman.smarthome.widget.swipeback.CloseActivityClass;
import com.heiman.smarthome.widget.swipeback.SwipeBackLayout;


public class SwipeBackActivity extends FragmentActivity {

	private SwipeBackLayout mSwipeBackLayout;

	private boolean mOverrideExitAniamtion = true;

	private boolean mIsFinishing;
//	SharePreferenceUtil mSharedUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CloseActivityClass.activityList.add(this);
		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getWindow().getDecorView().setBackgroundDrawable(null);
		mSwipeBackLayout = new SwipeBackLayout(this);
//		mSharedUtil = MyApp.getApp().getSpUtil();
//		if (mSharedUtil.isSHARED_KEY_CROSS()) {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// landscape是横向
//		} else {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// portrait是纵向
//		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mSwipeBackLayout.attachToActivity(this);
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mSwipeBackLayout.findViewById(id);
	}

	public SwipeBackLayout getSwipeBackLayout() {
		return mSwipeBackLayout;
	}

	public void setSwipeBackEnable(boolean enable) {
		mSwipeBackLayout.setEnableGesture(enable);
	}

	/**
	 * slide from left
	 */
	public void setEdgeFromLeft() {
		final int edgeFlag = SwipeBackLayout.EDGE_LEFT;
		mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
	}

	/**
	 * Override Exit Animation
	 *
	 * @param override
	 */
	public void setOverrideExitAniamtion(boolean override) {
		mOverrideExitAniamtion = override;
	}

	/**
	 * Scroll out contentView and finish the activity
	 */
	public void scrollToFinishActivity() {
		mSwipeBackLayout.scrollToFinishActivity();
	}

	@Override
	public void finish() {
		if (mOverrideExitAniamtion && !mIsFinishing) {
			scrollToFinishActivity();
			mIsFinishing = true;
			return;
		}
		mIsFinishing = false;
		super.finish();
	}

	protected String[] mMonths = new String[] { "Jan", "Feb", "Mar", "Apr",
			"May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

	protected static String[] mParties = new String[] { "Party A", "Party B",
			"Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
			"Party I", "Party J", "Party K", "Party L", "Party M", "Party N",
			"Party O", "Party P", "Party Q", "Party R", "Party S", "Party T",
			"Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z" };

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
	}
}
