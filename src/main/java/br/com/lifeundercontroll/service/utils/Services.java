package br.com.lifeundercontroll.service.utils;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.lifeundercontroll.service.auth.UserOAuthService;

public class Services {

	@Autowired
	private UserOAuthService userAuthService;
	
	@Autowired
	private RequestService requestService;
	
	
	public UserOAuthService getUserAuthService() {
		return userAuthService;
	}
	
	public RequestService getRequestService() {
		return requestService;
	}
	
}
