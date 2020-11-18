package com.hhmedic.sdk.server.demo;

import com.hhmedic.sdk.server.family.HhmedicFamilyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServerDemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(ServerDemoApplication.class, args);
	}

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

}
