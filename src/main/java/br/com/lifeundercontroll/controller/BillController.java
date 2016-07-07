package br.com.lifeundercontroll.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontroll.Dto.Response.BillResponse;
import br.com.lifeundercontroll.Dto.request.BillRequest;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.service.BillService;
import br.com.lifeundercontroll.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/bill")
public class BillController extends BaseController{

	@Autowired
	private BillService billService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="Cria uma conta e associa a um usuario",notes="A partir das informações da conta e do token do usuario é feito a associação da conta")
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('createBill')")
	public void createBill(
			@RequestBody @Valid BillRequest billRquest,BindingResult result) throws ResourceNotFound{
		 verifyInvalidParam(result);
		 
		 billService.createBill(billRquest);
	}
	
	@ApiOperation(value="Recupera dados de uma determinada conta")
	@RequestMapping(value="/{billId}",method=RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	@PreAuthorize("hasAuthority('getBill')")
	public BillResponse getBill(@PathVariable Long billId) throws ResourceNotFound{
		return billService.getBillById(billId);
	}
	
	@ApiOperation(value="Lista todas as contas de um usuario")
	@RequestMapping(value="/{userToken}/bills",method=RequestMethod.GET)
	@PreAuthorize("hasAuthority('getUserBills')")
	@ResponseStatus(value=HttpStatus.OK)
	public List<BillResponse> getUserBills(
			@PathVariable String userToken) throws ResourceNotFound{
		return userService.getBills(userToken);
	}
	
}
