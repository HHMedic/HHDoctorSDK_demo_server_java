# 视频医生对接资料
通用版

## 一、对接流程
#### 1.服务端对接
* 注册用户：提交用户基本信息，获取用户userToken。
* 添加套餐：为用户添加产品套餐。
* 获取小程序码：获取用户唯一的小程序码。（小程序对接专用，如只对接服务器可忽略此项）

#### 2.小程序对接
* H5页面显示二维码，提示用户长按识别二维码，即可调用小程序，图文咨询医助，视频咨询医生。可参考下方示意图：
![sample](https://imgs.hh-medic.com/icon/wmp/sample1.jpg?x-oss-process=image/resize,m_fixed,w_300)

**重要提示**
* 通过测试系统SDK/API获取的小程序码仅用来展示，无法正常使用线上微信小程序的相关功能，扫码后会提示“暂无使用权限”错误。请使用生产系统SDK/API获取的小程序码来唤起小程序并使用。
* 小程序码第一次扫码时会与当前微信用户绑定，绑定后其他微信用户扫描或长按识别当前小程序码时会提示“暂无使用权限”错误。
* 如小程序码被异常扫描绑定，请联系您的接口人解除绑定。每个小程序码最多可解除绑定1次。

***

## 二、SDK/API
如果您的后台开发语言是Java或.Net，推荐使用我们提供的SDK，实现服务器的快速接入。如果您的开发语言非Java或.Net，也可通过API接口实现服务器的对接。
#### Java版
https://github.com/HHMedic/HHDoctorSDK_demo_server_java

#### .Net版
https://github.com/HHMedic/HHDoctorSDK_demo_server_dotnet

#### API
##### 通用规则
https://api.hh-medic.com/project/44/interface/api/1349
##### 注册用户
https://api.hh-medic.com/project/47/interface/api/1712
##### 添加套餐
https://api.hh-medic.com/project/47/interface/api/1684
##### 获取小程序码（小程序对接专用）
https://api.hh-medic.com/project/47/interface/api/1696

**API重要提示**
* 如果您使用API对接服务器，访问API网站时需要登录用户名和密码，此用户名和密码由我方分配，请向您的接口人索取。
* 请首先阅读API文档的通用规则部分，并确保已完全了解CurTime、Nonce、CheckSum相关参数的计算生成方式，其他业务API均在遵守通用规则的前提下提供服务。

***

## 三、APP如何调用小程序
请参考：https://api.hh-medic.com/project/53/interface/api/1528

***

## 四、SDK/API开发帐号
开发帐号包括sdkProductId和appSecret,由我方分配，在调用SDK或API时使用，请向您的接口人索取。

***

## 五、其他说明
如需数据抄送功能，可参考API：https://api.hh-medic.com/project/47/interface/api/1592
