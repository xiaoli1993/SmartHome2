package com.heiman.baselibrary.mode;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/22 17:29
 * @Description :
 */
public class Messages {


    /**
     * count : 20065
     * list : [{"id":"58d31df690124f202bfbd119","to":[],"content":"{\"message\":{\"electricity\":\"0.000\",\"time\":\"2017-3-23 23:59:59\"},\"plugMac\":\"ACCF23A18CDC\"}","alert_value":"{\"message\":{\"electricity\":\"0.000\",\"time\":\"2017-3-23 23:59:59\"},\"plugMac\":\"ACCF23A18CDC\"}","create_date":"2017-03-23T08:59:34.358Z","alert_name":"electricity_notice","notify_type":1,"from":1144505255,"type":1,"is_read":false,"is_push":false}]
     */
    @Expose
    private int count;
    @Expose
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 58d31df690124f202bfbd119
         * to : []
         * content : {"message":{"electricity":"0.000","time":"2017-3-23 23:59:59"},"plugMac":"ACCF23A18CDC"}
         * alert_value : {"message":{"electricity":"0.000","time":"2017-3-23 23:59:59"},"plugMac":"ACCF23A18CDC"}
         * create_date : 2017-03-23T08:59:34.358Z
         * alert_name : electricity_notice
         * notify_type : 1
         * from : 1144505255
         * type : 1
         * is_read : false
         * is_push : false
         */
        @Expose
        private String id;
        @Expose
        private String content;
        @Expose
        private String alert_value;
        @Expose
        private String create_date;
        @Expose
        private String alert_name;
        @Expose
        private int notify_type;
        @Expose
        private int from;
        @Expose
        private int type;
        @Expose
        private boolean is_read;
        @Expose
        private boolean is_push;
        @Expose
        private List<?> to;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAlert_value() {
            return alert_value;
        }

        public void setAlert_value(String alert_value) {
            this.alert_value = alert_value;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getAlert_name() {
            return alert_name;
        }

        public void setAlert_name(String alert_name) {
            this.alert_name = alert_name;
        }

        public int getNotify_type() {
            return notify_type;
        }

        public void setNotify_type(int notify_type) {
            this.notify_type = notify_type;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isIs_read() {
            return is_read;
        }

        public void setIs_read(boolean is_read) {
            this.is_read = is_read;
        }

        public boolean isIs_push() {
            return is_push;
        }

        public void setIs_push(boolean is_push) {
            this.is_push = is_push;
        }

        public List<?> getTo() {
            return to;
        }

        public void setTo(List<?> to) {
            this.to = to;
        }
    }
}
