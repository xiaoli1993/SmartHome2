package com.heiman.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static InputStream getStreamFromURL(String imageURL) {
        InputStream in=null;
        try {
            URL url=new URL(imageURL);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();
            LogUtil.e("code:" + code);
            in=connection.getInputStream();
        } catch (Exception e) {
            if (e != null) {
                LogUtil.e(e.getMessage());
            }
        }
        return in;
    }
}
