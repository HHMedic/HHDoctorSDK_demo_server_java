package com.hhmedic.sdk.server.demo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhmedic.sdk.server.core.common.ServerResponse;
import com.hhmedic.sdk.server.core.profile.SdkProfile;
import com.hhmedic.sdk.server.core.util.CheckSumBuilder;
import com.hhmedic.sdk.server.core.util.StringUtil;
import com.hhmedic.sdk.server.family.entity.User;
import com.hhmedic.sdk.server.family.request.ProductRequest;
import com.hhmedic.sdk.server.family.request.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Copyright: Copyright(c)2018 www.hh-medic.com All rights reserved
 * @author: mayonglei
 * @date: 2018年06月20日
 */
@Controller
@RequestMapping(value = "/")

public class MainContrller {
    private Boolean isInited = false;
    private Integer sdkProductId = -1;
    private String appSecret = "APPSECRET";
    private UserRequest userRequest = new UserRequest();
    private ProductRequest productRequest = new ProductRequest();

    public MainContrller() {
        try {
            //初始化sdk。在实际业务中，请自行确定初始化sdk的合适时机
            sdkInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setPhoneNum(phoneNum);
        user.setName(name);
        user.setBirthday(fmt.parse(birthday));
        user.setSex("男".equals(sex) ? User.SexEnum.男 : User.SexEnum.女);
        user.setAccountId(accountId);
        ServerResponse serverResponse = userRequest.registerUser(user);
        return JSON.toJSONString(serverResponse);
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
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setUserToken(userToken);
        user.setName(name);
        user.setBirthday(fmt.parse(birthday));
        user.setSex("男".equals(sex) ? User.SexEnum.男 : User.SexEnum.女);
        user.setPhotourl(photoUrl);
        user.setRelation(relation);
        user.setAccountId(accountId);
        ServerResponse serverResponse = userRequest.addMember(user, false, "'");
        return JSON.toJSONString(serverResponse);
    }

    @RequestMapping(value = "/getUserWxAcode")
    public String getUserWxAcode() {
        return "getUserWxAcode";
    }

    @PostMapping(value = "/getUserWxAcode")
    @ResponseBody
    public String getUserWxAcodeApi(@RequestParam(name = "userToken", required = true, defaultValue = "") String userToken) throws Exception {
        ServerResponse serverResponse = userRequest.getUserWxAcode(userToken);
        return JSON.toJSONString(serverResponse);
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
        ServerResponse serverResponse;
        if (!StringUtil.isNullOrEmpty(userToken)) {
            serverResponse = productRequest.addProduct(userToken, pid);
        } else if (!StringUtil.isNullOrEmpty(imei)) {
            serverResponse = productRequest.addProductByImei("", imei, pid);
        } else {
            serverResponse = new ServerResponse();
            serverResponse.setStatus(400);
            serverResponse.setMessage("userToken和imei至少应保证一个有值");
        }
        return JSON.toJSONString(serverResponse);
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
        if (StringUtil.isNullOrEmpty(data)) {
            //没有request body
            return "";
        }

        try {
            if (!validateRequest(data, request)) {
                //当前访问请求不合法
                return "";
            }

            JSONObject jsonObject = JSON.parseObject(data);
            saveData(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 初始化SDK
     *
     * @throws Exception
     */
    private void sdkInit() throws Exception {
        if (isInited) {
            return;
        }
        String red = "\u001B[31m", reset = "\u001B[0m";
        if (sdkProductId < 0 || "APPSECRET".equals(appSecret)) {
            System.out.println(red);
            System.out.println("########################################################参数未初始化########################################################################");
            System.out.println("##                                                                                                                                       ##");
            System.out.println("##                       请修改com.hhmedic.sdk.server.demo.web.MainController中29、30行的sdkProductId、appSecret变量值                      ##");
            System.out.println("##                                                                                                                                       ##");
            System.out.println("###########################################################################################################################################");
            System.out.println(reset);
            System.exit(0);
        }
        SdkProfile.initialize(sdkProductId, appSecret, SdkProfile.ProfileTypeEnum.TEST);
        isInited = true;
    }

    private Boolean validateRequest(String data, HttpServletRequest request) {
        try {
            String curTime = request.getHeader("CurTime");
            String md5 = request.getHeader("MD5");
            String checkSum = request.getHeader("CheckSum");

            //计算期望的checksum
            String expectedCheckSum = CheckSumBuilder.getCheckSum(SdkProfile.appSecret, md5, curTime);
            //计算期望的md5(request body)
            String expectedMd5 = CheckSumBuilder.getMD5(data);
            //根据实际值与期望值判断请求合法性
            return (expectedMd5.equals(md5) && expectedCheckSum.equals(checkSum));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 对抄送数据做业务处理
     *
     * @param data
     */
    private void saveData(JSONObject data) {
        //TODO:解析jsonObject数据内容并作相应业务处理
        System.out.println(data.toString());
    }
}
