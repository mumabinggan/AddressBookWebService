package com.imooc.config;

import com.imooc.utils.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Date;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		//1, 增加配置信息
		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOrigin("http://localhost:8088");
//		config.addAllowedOrigin("*");
		config.addAllowedOriginPattern("*");

		//是否发送cookie信息
		config.setAllowCredentials(true);

		//设置允许请求方式
		config.addAllowedMethod("*");

		//设置允许header
		config.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
		corsSource.registerCorsConfiguration("/**", config);
		System.out.println("跨域:" + DateUtil.dateToString(new Date()));
		return new CorsFilter(corsSource);
	}
}
