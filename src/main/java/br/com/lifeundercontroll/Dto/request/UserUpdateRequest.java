package br.com.lifeundercontroll.Dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class UserUpdateRequest {

	@NotNull(message="É necessário passar o token para alterar os dados")
	private String token;
	
	@NotNull(message="O nome é obrigatorio")
	private String nome;
	
	@NotNull(message="O salario é obrigatorio")
	private BigDecimal salario;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	
	
}
