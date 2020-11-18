package com.hhmedic.sdk.server.demo.web;

import com.hhmedic.sdk.server.core.util.CheckSumBuilder;
import com.hhmedic.sdk.server.family.HhmedicFamilyClient;
import com.hhmedic.sdk.server.family.product.AddProductByUserTokenRequest;
import com.hhmedic.sdk.server.family.product.AddProductByUserTokenResponse;
import com.hhmedic.sdk.server.family.user.GetUserWxCodeByTokenRequest;
import com.hhmedic.sdk.server.family.user.GetUserWxCodeResponse;
import com.hhmedic.sdk.server.family.user.v2.AddMemberRequest;
import com.hhmedic.sdk.server.family.user.v2.AddMemberResponse;
import com.hhmedic.sdk.server.family.user.v2.RegisterUserRequest;
import com.hhmedic.sdk.server.family.user.v2.RegisterUserResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")

public class MainContrller {

	@Autowired
	private HhmedicFamilyClient hhmedicFamilyClient;

	/**
	 * 首页
	 *
	 * @return
	 */
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

	/**
	 * 注册用户页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/regUser")
	public String regUser() {
		return "regUser";
	}

	/**
	 * 注册用户API
	 *
	 * @param phoneNum  手机号
	 * @param name      姓名
	 * @param accountId 用户ID
	 * @param sex       性别
	 * @param birthday  生日
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/regUser")
	@ResponseBody
	public String regUserApi(@RequestParam(name = "phoneNum", required = true, defaultValue = "") String phoneNum,
							 @RequestParam(name = "name", required = true, defaultValue = "") String name,
							 @RequestParam(name = "accountId", required = true, defaultValue = "") String accountId,
							 @RequestParam(name = "sex", required = true, defaultValue = "") String sex,
							 @RequestParam(name = "birthday", required = true, defaultValue = "") String birthday) throws Exception {
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
	}

	@RequestMapping(value = "/addMember")
	public String addMember() {
		return "addMember";
	}

	@PostMapping(value = "/addMember")
	@ResponseBody
	public String addMemberApi(@RequestParam(name = "userToken", required = true, defaultValue = "") String userToken,
							   @RequestParam(name = "name", required = true, defaultValue = "") String name,
							   @RequestParam(name = "photoUrl", required = true, defaultValue = "") String photoUrl,
							   @RequestParam(name = "sex", required = true, defaultValue = "") String sex,
							   @RequestParam(name = "birthday", required = true, defaultValue = "") String birthday,
							   @RequestParam(name = "accountId", required = true, defaultValue = "") String accountId,
							   @RequestParam(name = "relation", required = true, defaultValue = "") String relation) throws Exception {
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
	}

	@RequestMapping(value = "/getUserWxAcode")
	public String getUserWxAcode() {
		return "getUserWxAcode";
	}

	@PostMapping(value = "/getUserWxAcode")
	@ResponseBody
	public String getUserWxAcodeApi(@RequestParam(name = "userToken", required = true, defaultValue = "") String userToken) throws Exception {
		// 1.创建获取小程序二维码请求
		GetUserWxCodeByTokenRequest request = new GetUserWxCodeByTokenRequest();
		request.setUserToken(userToken);
		// 2.client执行请求
		GetUserWxCodeResponse response = hhmedicFamilyClient.doAction(request);
		// 3.返回小程序二维码
		return response.getWxacode();
	}

	/**
	 * 添加产品套餐页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/addProduct")
	public String addProduct() {
		return "addProduct";
	}

	/**
	 * 添加产品套餐API
	 *
	 * @param userToken userToken
	 * @param pid       产品套餐ID
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/addProduct")
	@ResponseBody
	public String addProductÅpi(@RequestParam(name = "userToken", defaultValue = "") String userToken,
								@RequestParam(name = "imei", defaultValue = "") String imei,
								@RequestParam(name = "pid", required = true, defaultValue = "") Integer pid) throws Exception {
		// 1.创建添加用户套餐请求
		AddProductByUserTokenRequest request = new AddProductByUserTokenRequest();
		request.setPid(pid);
		if(StringUtils.isNotBlank(userToken)){
			request.setUserToken(userToken);
		}
		if(StringUtils.isNotBlank(imei)){
			request.setImei(imei);
		}
		// 2.client执行请求
		AddProductByUserTokenResponse response = hhmedicFamilyClient.doAction(request);
		// 3.返回用户套餐过期时间
		return response.getExpireTime();
	}

	/**
	 * 数据抄送页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/syncData")
	public String syncData() {
		return "syncData";
	}

	/**
	 * 数据抄送接口实现
	 *
	 * @param data
	 * @param request
	 * @return
	 */
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


}
