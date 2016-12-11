package br.com.lifeundercontroll.DTO.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class UserUpdateRequest {
	
	@NotNull(message="O nome é obrigatorio")
	private String nome;
	
	@NotNull(message="O salario é obrigatorio")
	private BigDecimal salario;
	
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
