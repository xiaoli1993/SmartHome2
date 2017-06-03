###HiSmartHome.jks 密码 heiman998

#SmartHome组件化程序结构说明

##应用层 
```
    1.app为工程类
```

##业务框架层
```
    1.gateway 网关
    2.plug 插座
    3.light 灯控
    4.ambient 环境类设备，包括温湿度、空气质量。
    5.home 家庭管理
    6.room 房间管理
    7.scene 场景
    8.link 联动
    9.user 用户信息管理。包含用户登录、设备分享、数据手册等。
    10.camera 摄像头
    11.等....
```

##基础架构层 
```
    1.baselibrary 所有基础类接口包含以下所有
    2.network 网络接口类
    3.dbsqlite 数据库接口
    4.utils 常用工具类
    5.datacom 透传数据透传含加密
    6.widget 控件类及资源库
    7.thirdparty 使用第三方SDK
```

在utils中增加LogUtil 2017/05/16 张泽晋
utils中的UsefullUtill类作了改动 2017/05/17 张泽晋
widget增加第三方图片剪裁 WeiChatCropActivity 2017/5/25张泽晋
dbsqlite中增加数据库greendao 2017/5/26 张泽晋


