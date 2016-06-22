package br.com.lifeundercontroll.builders;

import br.com.lifeundercontroll.Dto.Response.BillResponse;
import br.com.lifeundercontroll.entity.BillEntity;

public class BillResponseBuilder {

	public static BillResponse build(BillEntity billEntity){
		BillResponse billResponse = new BillResponse();
		billResponse.setDueDate(billEntity.getDueDate());
		billResponse.setName(billEntity.getName());
		billResponse.setValue(billEntity.getValue());
		return billResponse;
	}
	
}
