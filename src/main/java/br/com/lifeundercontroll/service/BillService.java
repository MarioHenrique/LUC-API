package br.com.lifeundercontroll.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lifeundercontroll.Dto.Response.BillResponse;
import br.com.lifeundercontroll.Dto.request.BillRequest;
import br.com.lifeundercontroll.builders.BillEntityBuilder;
import br.com.lifeundercontroll.builders.BillResponseBuilder;
import br.com.lifeundercontroll.entity.BillEntity;
import br.com.lifeundercontroll.entity.UserEntity;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.repository.UserRepository;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void createBill(BillRequest request) throws ResourceNotFound{
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByToken(request.getUserToken()));
		UserEntity userEntity = user.orElseThrow(()->new ResourceNotFound("Token de usuario invalido"));
		userEntity.getBills().add(BillEntityBuilder.build(request));
		userRepository.save(userEntity);
	}

	public BillResponse getBillById(Long billId) throws ResourceNotFound {
		Optional<BillEntity> bill = Optional.ofNullable(billRepository.findOne(billId));
		BillEntity billEntity = bill.orElseThrow(()->new ResourceNotFound("Conta não encontrada"));
		return BillResponseBuilder.build(billEntity);
	}
	
}
