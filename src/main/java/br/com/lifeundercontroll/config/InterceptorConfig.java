package br.com.lifeundercontroll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.lifeundercontroll.interceptors.LogInterceptor;
import br.com.lifeundercontroll.interceptors.TokenInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	LogInterceptor logInterceptor;
	
	@Autowired
	TokenInterceptor tokenInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor)
				.addPathPatterns("/api/**");
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/api/**");
	}
	
}
