package br.com.lifeundercontroll.interceptors;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
@Order(value = 1)
public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Optional<String> token = Optional.ofNullable(request.getHeader("token"));

		if (token.isPresent()) {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token.get());
			
			if(accessToken == null){
				throw new UnauthorizedClientException("Token invalido");
			}
			
			if(accessToken.isExpired()){
				throw new UnauthorizedClientException("Token expirado");
			}
			
			OAuth2Authentication auth = tokenStore.readAuthentication(token.get());
						
			if (auth != null)
				SecurityContextHolder.getContext().setAuthentication(auth);
			
		}else{
			throw new UnauthorizedClientException("Token invalido");
		}

		return true;
	}

}
