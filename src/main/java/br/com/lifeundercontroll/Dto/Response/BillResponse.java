package br.com.lifeundercontroll.Dto.Response;

import java.math.BigDecimal;
import java.util.Date;

public class BillResponse {

	private String name;
	
	private Date dueDate;
	
	private BigDecimal value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
