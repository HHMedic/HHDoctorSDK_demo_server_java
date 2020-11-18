# HHDoctorSDK_demo_server_java
视频医生服务器SDK演示Java版2.0

如您使用的是1.0版本的SDK，请切换至分支v1.x

**强烈建议您将SDK升级至2.0版本**

## 说明
如果您的后台开发语言是Java或.Net，推荐使用我们提供的SDK，实现服务器的快速接入。如果您的开发语言非Java或.Net，也可通过API接口实现服务器的对接。

API文档地址：https://api.hh-medic.com/project/47/interface/api

本项目使用maven管理，基于spring boot框架，用于演示视频医生服务器SDK相关功能。
#### Java版地址
https://github.com/HHMedic/HHDoctorSDK_demo_server_java

#### .Net版地址
https://github.com/HHMedic/HHDoctorSDK_demo_server_dotnet

## 一. 工程启动
* 克隆当前项目到本地目录
* 导入IDE，如IntelliJ IDEA
* 修改ServerDemoApplication.java中的各参数
* 创建新的Run/Debug Configuration
* 执行Run或Debug
* 在浏览器中访问：http://localhost:8080/demo

## 二. 依赖引入
##### 1. pom.xml中引入repository，或者在您的nexus仓库中增加和缓的对外公开仓库地址，<font color='red'>推荐后者</font>
```xml
    <repositories>
        <repository>
            <id>hhmedic</id>
            <url>http://develop.hh-medic.com/repository/maven-public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
                <checksumPolicy>warn</checksumPolicy>
            </snapshots>
        </repository>
    </repositories>
```
***说明：***
和缓视频医生SDK包未发布至maven中央仓库

##### 2. pom.xml中引入和缓视频医生SDK包
```xml
        <dependency>
            <groupId>com.hhmedic.sdk</groupId>
            <artifactId>server.family</artifactId>
            <version>2.0.11</version>
        </dependency>
```
***说明：***
SDK中默认的client需要依赖spring-web，您需要单独添加

## 三. 接口列表 - 用户账号
| 接口名称 | SDK请求 | SDK响应 |
| :-----| :---- | :---- |
| 01.用户注册 | RegisterUserRequest | RegisterUserResponse |
| 02.用户信息更新 | UpdateUserRequest | Void |
| 03.添加套餐 | AddProductByUserTokenRequest | AddProductByUserTokenResponse |
| 04.获取小程序码 | GetUserWxCodeByTokenRequest | GetUserWxCodeResponse |
| 05.添加家庭成员 | AddMemberRequest | AddMemberResponse |
| 06.查询用户套餐信息 | GetUserProductRequest | GetUserProductResponse |
| 07.停止用户套餐服务 | DeleteProductByUserTokenRequest | DeleteProductByUserTokenResponse |
| 08.删除用户信息 | DeleteUserRequest | Boolean |
| 09.设备-续费套餐 | 未实现 | 未实现 |
| 10.激活码开通套餐 | ActiveProductRequest | ActiveProductResponse |

## 四. 接口列表 - 用户健康
| 接口名称 | SDK请求 | SDK响应 |
| :-----| :---- | :---- |
| 上传健康数据-新版 | UploadHealthDataRequest | Void |

## 五. 示例代码
##### 0. 初始化客户端，相关参数请与和缓联系索取
```java
	/**
	 * 定义和缓视频医生请求客户端
	 *
	 * @param restTemplateBuilder
	 * @return
	 */
	@Bean
	public HhmedicFamilyClient hhmedicFamilyClient(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		// 此处提供的restTemplate仅用于演示，建议您创建自己的restTemplate，维护requestFactory，connectTimeout，readTimeout等参数
		return new HhmedicFamilyClient("<固定为：https>", "<此处更改为和缓的host，测试环境为：test.hh-medic.com>", restTemplate, "<此处更改为和缓分配的sdkProductId>", "<此处更改为和缓分配的appSecret>");
	}
```
***联网说明：***
联网请求被分为 request-> client -> response 三个部分，client初始化后直接注入即可，不需要每次都new

*** 

***异常说明：***
* API文档参看：https://api.hh-medic.com/project/47/interface/api/1740
* 缺省情况下，返回码code不为200时，SDK会抛出ServiceExcepiton异常

##### 1. 用户注册
```java
		// 1.创建注册用户请求对象
		RegisterUserRequest request = new RegisterUserRequest();
		request.setPhoneNum(phoneNum);
		request.setName(name);
		request.setBirthday(birthday);
		request.setSex(sex);
		request.setAccountId(accountId);
		// 2.client执行请求
		RegisterUserResponse response = hhmedicFamilyClient.doAction(request);
		// 3.返回注册用户的userToken
		return response.getUserToken();
```

##### 2. 用户信息更新
```java

```

##### 3. 添加套餐
```java
		// 1.创建添加用户套餐请求
		AddProductByUserTokenRequest request = new AddProductByUserTokenRequest();
		request.setPid(pid);
		if(StringUtils.isNotBlank(userToken)){
			request.setUserToken(userToken);
		}
		if(StringUtils.isNotBlank(imei)){
			request.setImei(imei);
		}
        request.setThirdOrderId(thirdOrderId);
		// 2.client执行请求
		AddProductByUserTokenResponse response = hhmedicFamilyClient.doAction(request);
		// 3.返回用户套餐过期时间
		return response.getExpireTime();
```

##### 4. 获取小程序码
```java
		// 1.创建获取小程序二维码请求
		GetUserWxCodeByTokenRequest request = new GetUserWxCodeByTokenRequest();
		request.setUserToken(userToken);
		// 2.client执行请求
		GetUserWxCodeResponse response = hhmedicFamilyClient.doAction(request);
		// 3.返回小程序二维码
		return response.getWxacode();
```

##### 5. 添加家庭成员
```java
		// 1.创建添加家庭成员请求
		AddMemberRequest request = new AddMemberRequest();
		request.setUserToken(userToken);
		request.setName(name);
		request.setBirthday(birthday);
		request.setSex(sex);
		request.setPhotoUrl(photoUrl);
		request.setRelation(relation);
		request.setAccountId(accountId);
		// 2.client执行请求
		AddMemberResponse response = hhmedicFamilyClient.doAction(request);
		// 3.返回新注册家庭成员userToken
		return response.getUserToken();
```



## 六. 其他说明
如需数据抄送功能，可参考API：https://api.hh-medic.com/project/47/interface/api/1592

SDK实现抄送数据校验功能
```java
	@PostMapping(value = "/syncData")
	@ResponseBody
	public String syncDataApi(@RequestBody String data,
							  HttpServletRequest request) {
		if (StringUtils.isBlank(data)) {
			//没有request body
			return "";
		}

		String curTime = request.getHeader("CurTime");
		String md5 = request.getHeader("MD5");
		String checkSum = request.getHeader("CheckSum");

		if(!hhmedicFamilyClient.validate(data,curTime,md5,checkSum)){
			//当前访问请求不合法
			return "";
		}

		// todo 处理您的业务

		return "";
	}
```


## 七. 对接步骤说明
请参考：[HOWTODO.md](HOWTODO.md)
