package br.com.lifeundercontroll.builders;

import br.com.lifeundercontroll.Dto.request.BillRequest;
import br.com.lifeundercontroll.Dto.request.BillUpdateRequest;
import br.com.lifeundercontroll.entity.BillEntity;

public class BillEntityBuilder {

	public static BillEntity build(BillRequest billRequest){
		BillEntity billEntity = new BillEntity();
		billEntity.setDueDate(billRequest.getDueDate());
		billEntity.setName(billRequest.getName());
		billEntity.setValue(billRequest.getValue());
		return billEntity;
	}
	
	public static BillEntity build(BillUpdateRequest billUpdateRequest, BillEntity billEntity){
		billEntity.setDueDate(billUpdateRequest.getDueDate());
		billEntity.setName(billUpdateRequest.getName());
		billEntity.setValue(billUpdateRequest.getValue());
		return billEntity;
	} 
	
}
