# HHDoctorSDK_demo_server_java
视频医生服务器SDK演示(java)

## 简介
当前项目使用maven管理，基于spring boot框架，用于演示视频医生服务器SDK相关功能。

## 如何使用
* 克隆当前项目到本地目录
* 导入IDE，如IntelliJ IDEA
* 修改MainContrller.java中的sdkProductId和appSecret
* 创建新的Run/Debug Configuration
* 执行Run或Debug
* 在浏览器中访问：http://localhost:8080/demo

## 完整对接流程
请参考：https://github.com/HHMedic/HHDoctorSDK_demo_server_java/blob/master/HOWTODO.md


## SDK方法列表

### UserRequest

* registerUser：注册用户
* updateUser：更新用户信息
* addMember：添加成员
* getUserWxAcode：获取用户的微信小程序码
* deleteUser：删除用户
* getUserProduct：获取用户套餐信息
* uploadHealthData：上传用户健康数据

### ProductRequest

* addProduct：给指定用户添加套餐
* deleteProduct：删除指定用户最新套餐
