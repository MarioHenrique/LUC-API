package br.com.lifeundercontroll.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BillUpdateRequest {
	
	@NotNull(message="ID is required for Bill update")
	private Long id;
	
	@NotNull(message="Name is required for Bill update")
	private String name;
	
	@NotNull(message="Value is required for Bill update")
	@DecimalMin(value="0.00")
	private BigDecimal value;

	@NotNull(message="Duedate is required for Bill update")
	private Date dueDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
