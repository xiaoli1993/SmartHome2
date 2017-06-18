package com.heiman.devicecommon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.heiman.baselibrary.mode.Room;
import com.heiman.widget.flexbox.adapter.TagAdapter;

import java.util.List;


/**
 * 作者：ZhouYou
 * 日期：2017/3/27
 */
public class StringTagAdapter extends TagAdapter<StringTagView, Room> {

    public  StringTagAdapter(Context context, List<Room> data) {
        this(context, data, null);
    }

    public StringTagAdapter(Context context, List<Room> data, List<Room> selectItems) {
        super(context, data, selectItems);
    }

    /**
     * 检查item和所选item是否一样
     *
     * @param view
     * @param item
     * @return
     */
    @Override
    protected boolean checkIsItemSame(StringTagView view, Room item) {
        return TextUtils.equals(view.getItem().getRoom_name(), item.getRoom_name());
    }

    /**
     * 检查item是否是空指针
     *
     * @return
     */
    @Override
    protected boolean checkIsItemNull(Room item) {
        return TextUtils.isEmpty(item.getRoom_name());
    }

    /**
     * 添加标签
     *
     * @param item
     * @return
     */
    @Override
    protected StringTagView addTag(Room item) {
        StringTagView tagView = new StringTagView(getContext());
        tagView.setPadding(20, 20, 20, 20);

        TextView textView = tagView.getTextView();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        textView.setGravity(Gravity.CENTER);

        tagView.setItemDefaultDrawable(itemDefaultDrawable);
        tagView.setItemSelectDrawable(itemSelectDrawable);
        tagView.setItemDefaultTextColor(itemDefaultTextColor);
        tagView.setItemSelectTextColor(itemSelectTextColor);
        tagView.setItem(item);
        return tagView;
    }
}
