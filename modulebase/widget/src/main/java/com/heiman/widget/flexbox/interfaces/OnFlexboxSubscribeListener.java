package com.heiman.widget.flexbox.interfaces;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/6/17 9:07
 * @Description :
 * @Modify record :
 */
public interface OnFlexboxSubscribeListener<T> {

    /**
     * @param selectedItem 已选中的标签
     */
    void onSubscribe(List<T> selectedItem);
}
