package br.com.lifeundercontroll.builders;

import br.com.lifeundercontroll.dto.request.BillRequest;
import br.com.lifeundercontroll.entity.BillEntity;

public class BillEntityBuilder {

	public static BillEntity build(BillRequest billRequest){
		BillEntity billEntity = new BillEntity();
		billEntity.setDueDate(billRequest.getDueDate());
		billEntity.setName(billRequest.getName());
		billEntity.setValue(billRequest.getValue());
		return billEntity;
	}
	
}
