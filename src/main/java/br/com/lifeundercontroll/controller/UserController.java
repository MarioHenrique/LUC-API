package br.com.lifeundercontroll.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontroll.Dto.Response.UserResponse;
import br.com.lifeundercontroll.Dto.request.UserRequest;
import br.com.lifeundercontroll.Dto.request.UserUpdateRequest;
import br.com.lifeundercontroll.exceptions.ResourceAlreadyExist;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="Criação de um usuario",notes="Criação de um usuario novo no sistema")
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('createUser')")
	@ResponseStatus(value=HttpStatus.CREATED)
	public void createUser(@RequestBody @Valid UserRequest userRequest,BindingResult result) throws ResourceAlreadyExist{
		verifyInvalidParam(result);
		userService.createUser(userRequest);
	}
	
	@ApiOperation(value="Login de usuario",notes="A partir de um email e password é vefiricado se o usuario existe")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('login')")
	@ResponseStatus(value=HttpStatus.OK)
	public UserResponse login(
			@RequestParam String email,
			@RequestParam String password) throws ResourceNotFound{
		return userService.login(email,password);
	}
	
	@ApiOperation(value="Alteração de usuario",notes="A partir do token do usuario é possivel alterar seu nome e seu salario")
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	@PreAuthorize("hasAuthority('updateUser')")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void update(
			@RequestBody @Valid UserUpdateRequest userUpdateRequest,BindingResult result) throws ResourceNotFound{
		   verifyInvalidParam(result);
		   userService.updateUser(userUpdateRequest);
	}
	
}
