package com.heiman.baselibrary.http;/**
 * Created by hp on 2016/12/16.
 */

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/16 10:05
 * @Description : HTTP文件
 */
public class XHeader implements Header {
    private String name;
    private String value;

    public XHeader(String name, String value, HeaderElement[] element) {
        // TODO Auto-generated constructor stub
        this.value = value;
        this.name = name;
    }
    public XHeader(String name, String value) {
        // TODO Auto-generated constructor stub
        this.value = value;
        this.name = name;
    }
    @Override
    public HeaderElement[] getElements() throws ParseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public String getValue() {
        // TODO Auto-generated method stub
        return value;
    }

}
