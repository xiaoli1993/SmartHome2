package com.heiman.smarthome.mode;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

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
     * count : 总数量
     * list : [{"id":"消息ID","type":"消息类型","notify_type":"通知类型","from":"消息发送者","to":"接收者","content":"消息内容","create_date":"创建时间","is_read":"是否已读","is_push":"是否推送","alert_name":"触发告警规则名称","alert_value":"触发告警的数据端点值"}]
     */

    private String count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
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
         * id : 消息ID
         * type : 消息类型
         * notify_type : 通知类型
         * from : 消息发送者
         * to : 接收者
         * content : 消息内容
         * create_date : 创建时间
         * is_read : 是否已读
         * is_push : 是否推送
         * alert_name : 触发告警规则名称
         * alert_value : 触发告警的数据端点值
         */
        private String id;
        private String type;
        private String notify_type;
        private String from;
        private String to;
        private String content;
        private String create_date;
        private String is_read;
        private String is_push;
        private String alert_name;
        private String alert_value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNotify_type() {
            return notify_type;
        }

        public void setNotify_type(String notify_type) {
            this.notify_type = notify_type;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getIs_push() {
            return is_push;
        }

        public void setIs_push(String is_push) {
            this.is_push = is_push;
        }

        public String getAlert_name() {
            return alert_name;
        }

        public void setAlert_name(String alert_name) {
            this.alert_name = alert_name;
        }

        public String getAlert_value() {
            return alert_value;
        }

        public void setAlert_value(String alert_value) {
            this.alert_value = alert_value;
        }
    }
}
