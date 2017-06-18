package com.heiman.devicecommon.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.heiman.baselibrary.mode.Room;
import com.heiman.widget.flexbox.widget.BaseTagView;


/**
 * 作者：ZhouYou
 * 日期：2017/3/25.
 */
public class StringTagView extends BaseTagView<Room> {

    public StringTagView(Context context) {
        this(context, null);
    }

    public StringTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public StringTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setItem(Room room) {
        super.setItem(room);
        textView.setText(room.getRoom_name());
    }
}
