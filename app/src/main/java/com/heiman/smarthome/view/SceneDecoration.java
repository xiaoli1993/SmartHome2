/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.smarthome.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.yanzhenjie.recyclerview.swipe.ResCompat;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/7 21:55
 * @Description : 
 * @Modify record :
 */

public class SceneDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;

    public SceneDecoration() {
        mDrawable = ResCompat.getDrawable(BaseApplication.getMyApplication(), R.drawable.scene_divider_recycler);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int spanCount = BaseApplication.getMyApplication().getResources().getInteger(R.integer.grid_columns);
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 以下计算主要用来确定绘制的位置
            int left = child.getLeft() + params.leftMargin;
            int right = child.getRight() + params.rightMargin;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);

            if ((i % spanCount) == (spanCount -1)) {
                continue;
            }
            int childLeft = child.getRight() + params.leftMargin;
            int childRight = childLeft + mDrawable.getIntrinsicHeight();
            int childTop = child.getTop() + params.topMargin;
            mDrawable.setBounds(childLeft, childTop, childRight, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
    }

}
