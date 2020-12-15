package com.myz.wxapp;

import com.myz.wxapp.util.JsonKit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WXAppWebApplication {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public JsonKit jsonKit() {
		return new JsonKit();
	}

	public static void main(String[] args) {
		SpringApplication.run(WXAppWebApplication.class, args);
	}

}
