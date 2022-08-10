package com.myz.wxapp;

import com.myz.wxapp.util.JsonKit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class WXAppWebApplication extends SpringApplication {

	public WXAppWebApplication() {
		super(WXAppWebApplication.class);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public JsonKit jsonKit() {
		return new JsonKit();
	}

	@Override
	protected void configurePropertySources(ConfigurableEnvironment environment, String[] args) {
		super.configurePropertySources(environment, args);
	}

	public static void main(String[] args) {
		new WXAppWebApplication().run(args);
	}

}
