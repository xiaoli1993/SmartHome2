/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/5/26 15:46
 * @Description : 
 * @Modify record :
 */

@Entity
public class Setting {
    @Id
    private Long id;
    private String key;
    private String value;
    @Generated(hash = 1623505287)
    public Setting(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }
    @Generated(hash = 909716735)
    public Setting() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}
