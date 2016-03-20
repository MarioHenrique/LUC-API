package br.com.lifeundercontroll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.lifeundercontroll.domain.interceptors.LogInterceptor;
import br.com.lifeundercontroll.domain.interceptors.TokenInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	@Autowired
	TokenInterceptor tokenInterceptor;
	
	@Autowired
	LogInterceptor logInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/api/**");
		registry.addInterceptor(logInterceptor)
				.addPathPatterns("/api/**");
	}
	
}
