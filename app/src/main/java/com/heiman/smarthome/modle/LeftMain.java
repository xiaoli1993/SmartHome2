package com.heiman.smarthome.modle;

public class LeftMain {

    private int imageView;
    private String text;
    private int deviceType;
    private int index;
    private int activion;


    public LeftMain(int imageView, String text, int deviceType) {
        super();
        this.imageView = imageView;
        this.text = text;
        this.deviceType = deviceType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getActivion() {
        return activion;
    }

    public void setActivion(int activion) {
        this.activion = activion;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
