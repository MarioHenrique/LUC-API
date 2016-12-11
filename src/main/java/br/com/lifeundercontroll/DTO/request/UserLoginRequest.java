package br.com.lifeundercontroll.DTO.request;

import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginRequest {

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
