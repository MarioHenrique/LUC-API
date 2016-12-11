package br.com.lifeundercontroll.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontroll.DTO.Response.UserResponseDTO;
import br.com.lifeundercontroll.DTO.request.NewUserRequest;
import br.com.lifeundercontroll.DTO.request.UserLoginRequest;
import br.com.lifeundercontroll.DTO.request.UserUpdateRequest;
import br.com.lifeundercontroll.controller.utils.Controller;
import br.com.lifeundercontroll.exceptions.ResourceAlreadyExist;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.security.Permissions;
import br.com.lifeundercontroll.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController extends Controller {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Criação de um usuario", notes = "Criação de um usuario novo no sistema")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public UserResponseDTO createUser(@RequestBody @Valid NewUserRequest userRequest, BindingResult result)
			throws ResourceAlreadyExist {
		verifyInvalidParam(result);
		return userService.create(userRequest);
	}

	@ApiOperation(value = "Login de usuario", notes = "A partir de um email e password é vefiricado se o usuario existe")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public OAuth2AccessToken login(@RequestBody @Valid UserLoginRequest userLogin, BindingResult result) throws ResourceNotFound {
		verifyInvalidParam(result);
		return userService.login(userLogin);
	}
	
	@ApiOperation(value = "Atualização dos dados do usuario", notes = "Atualiza as informações do usuario logado")
	@RequestMapping(value = "/info", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize(Permissions.DEFAULT)
	public UserResponseDTO update(@RequestBody @Valid UserUpdateRequest userUpdate,BindingResult result){
		verifyInvalidParam(result);
		return userService.update(userUpdate);
	}

}
