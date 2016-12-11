package br.com.lifeundercontroll.DTO.Response;

import java.math.BigDecimal;

public class UserResponseDTO {

	private String name;	
	private String email;
	private BigDecimal salary;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
}
