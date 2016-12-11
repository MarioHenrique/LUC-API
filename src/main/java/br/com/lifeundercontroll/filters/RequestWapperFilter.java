package br.com.lifeundercontroll.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lifeundercontroll.config.security.RequestWrapper;

@Component
public class RequestWapperFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		RequestWrapper requestWrapper = new RequestWrapper(request);
		filterChain.doFilter(requestWrapper,response);
		
	}

	
	
}
