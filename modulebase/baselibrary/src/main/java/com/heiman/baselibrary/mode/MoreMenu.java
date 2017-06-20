package com.heiman.baselibrary.mode;

/**
 * @Author : 肖力
 * @Time :  2017/5/24 15:38
 * @Description :   更多
 * @Modify record :
 */

public class MoreMenu {
    /**
     * @param leftText      左边文字
     * @param rightText     右边文字
     * @param isVRightImage 是否显示右边图片
     */
    public MoreMenu(String leftText, String rightText, boolean isVRightImage) {
        super();
        this.leftText = leftText;
        this.rightText = rightText;
        this.isVRightImage = isVRightImage;
    }

    private String leftText;//左边文字
    private String rightText;//右边文字
    private boolean isVRightImage;//是否显示右边图片
    private String roomID;//房间ID

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public boolean isVRightImage() {
        return isVRightImage;
    }

    public void setVRightImage(boolean VRightImage) {
        isVRightImage = VRightImage;
    }
}
