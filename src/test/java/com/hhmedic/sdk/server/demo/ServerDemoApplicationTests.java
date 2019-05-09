package com.hhmedic.sdk.server.demo;

import com.hhmedic.sdk.server.core.common.ServerResponse;
import com.hhmedic.sdk.server.core.profile.SdkProfile;
import com.hhmedic.sdk.server.family.entity.Product;
import com.hhmedic.sdk.server.family.request.ProductRequest;
import com.hhmedic.sdk.server.family.request.UserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerDemoApplicationTests {
    private Integer appId = -1;
    private String appKey = "APPKEY", appSecret = "APPSECRET";
    private UserRequest userRequest = new UserRequest();
    private ProductRequest productRequest = new ProductRequest();

    @Test
    public void contextLoads() throws Exception {
        //初始化SDK
        SdkProfile.initialize(appId, appSecret, SdkProfile.ProfileTypeEnum.TEST);


        /*DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        //新用户注册
        User user = new User();
        user.setPhoneNum("13900000000");                    //用户手机号
        user.setName("用户名");                              //用户姓名
        user.setBirthday(fmt.parse("1970-01-01"));   //用户生日
        user.setSex(User.SexEnum.男);                       //用户性别
        user.setUserId("000001");                           //用户在第三方系统的唯一标识
        ServerResponse serverResponse = userRequest.registerUser(user);*/
        //为新用户添加产品套餐
        Product product = new Product();
        product.setPhoneNum("");
        product.setPid(100);                                //产品套餐ID
        ServerResponse serverResponse = productRequest.addProduct(product);
    }
}
