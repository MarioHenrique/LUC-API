package br.com.lifeundercontroll.Dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserRequest {

	@NotEmpty(message="Nome não pode ser vazio")
	private String name;
	
	@Email(message="Email invalido")
	@NotEmpty(message="Email não pode ser vazio")
	private String email;

	@NotEmpty
	private String password;
	
	@DecimalMin(value="0.00",message="O salario precisa ser um valor positivo")
	private BigDecimal salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
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
