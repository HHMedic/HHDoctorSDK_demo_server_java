# 视频医生对接资料
微信菜单对接

## 对接流程
#### 1.微信公众号关联小程序

* 登录微信公众号管理后台:https://mp.weixin.qq.com/

* 点击【小程序】-【小程序管理】，在右侧设置页面点击【添加】按钮。
![sample1](https://imgs.hh-medic.com/icon/wmp/git/WX20190415-111308.png?x-oss-process=image/resize,m_fixed,h_500)

* 点击【关联小程序】
![sample2](https://imgs.hh-medic.com/icon/wmp/git/WX20190415-111335.png?x-oss-process=image/resize,m_fixed,h_500)

* 输入小程序APPID：wx15e414719996d59f，点击【搜索】，并点击【下一步】申请关联。
![sample3](https://imgs.hh-medic.com/icon/wmp/git/WX20190415-111405.png?x-oss-process=image/resize,m_fixed,h_500)

#### 2.增加微信菜单（微信管理后台界面模式）

* 在微信公众号管理后台，点击【功能】-【自定义菜单】，添加主菜单或子菜单，菜单内容选择"跳转小程序"，点击【选择小程序】
![sample1](https://imgs.hh-medic.com/icon/wmp/git/WX20190415-112737.png?x-oss-process=image/resize,m_fixed,h_500)

* 输入小程序APPID：wx15e414719996d59f，或在下方常用小程序中点击"在线咨询服务"，并点击确定。
![sample1](https://imgs.hh-medic.com/icon/wmp/git/WX20190415-112752.png?x-oss-process=image/resize,m_fixed,h_500)

* 输入小程序路径（请与接口人联系获取）和备用网页(https://d.hh-medic.com/wmp_unsupport.html)。
![sample1](https://imgs.hh-medic.com/icon/wmp/git/WX20190415-112828.png?x-oss-process=image/resize,m_fixed,h_500)

* 保存自定义菜单。

#### 3.增加微信菜单（开发者模式）

* 通过"获取access_token接口"获取微信access_token，参考文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183

* 通过"自定义菜单查询接口"接获取微信公众号当前菜单配置，参考文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014

* 在需要添加菜单的位置增加如下菜单配置：

```json
{
    "type":"miniprogram",
    "name":"视频医生",                //菜单项名称
    "url":"https://d.hh-medic.com/wmp_unsupport.html",   //备用网页
    "appid":"wx15e414719996d59f",
    "pagepath":"xxxxxxx"             //请与接口人联系获取
}
```

* 通过"自定义菜单创建接口"提交新的菜单配置，参考文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
