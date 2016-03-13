package br.com.lifeundercontroll.domain.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.lifeundercontroll.domain.service.TokenService;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter{

	Logger logger = Logger.getLogger(TokenInterceptor.class);
	
	private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
	
	private static final String TOKENINVALID = "Token invalido para a operação";
	private static final String URL_ALLOWED = "/api/token";
	
	@Autowired
	private TokenService tokenSenvice;
	
	private static final String TOKEN = "token" ;
	
	private Boolean isNotvalidToken(HttpServletRequest request){
		String token = request.getHeader(TOKEN);
		String uri = request.getRequestURI();
		String method = request.getMethod();
		
		if(uri.startsWith(URL_ALLOWED) && method.equals("GET"))
			return false;
		
		if(StringUtils.isEmpty(token))
			return true;
		
		return tokenSenvice.findByToken(token);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(isNotvalidToken(request)){
			accessDeniedHandler.handle(request, response, new AccessDeniedException(TOKENINVALID));
			return false;
		}
		
		return true;
	}
	
}
