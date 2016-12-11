package br.com.lifeundercontroll.service.auth;

import java.sql.Types;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.lifeundercontroll.DTO.request.AuthenticationRequestDTO;
import br.com.lifeundercontroll.entities.RoleEntity;
import br.com.lifeundercontroll.entities.UserEntity;
import br.com.lifeundercontroll.repository.RoleRepository;
import br.com.lifeundercontroll.service.utils.RequestService;

@Service
public class UserOAuthService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RequestService requestService;
	
	private static final String ROLE_DEFAULT = "user"; 
	
	private static String INSERT_CLIENT = "insert into oauth_client_details(client_id,client_secret, scope, authorized_grant_types)values(?, ?, ?, ?)";
	private static String GLOBAL = "global";
	private static String PASSWORD = "password";

	public OAuth2AccessToken getToken(String email, String password) {
		RestTemplate rest = new RestTemplate();	
		AuthenticationRequestDTO requestDTO = new AuthenticationRequestDTO();
		requestDTO.setScope("global");
		requestDTO.setGrant_type("password");
		requestDTO.setUsername(email);
		requestDTO.setPassword(password);
		Map<String,Object> uri = new HashMap<>();
		uri.put("password",password);
		uri.put("username", email);
		uri.put("grant_type", "password");
		uri.put("scope","global");
		HttpEntity<String> entity = new HttpEntity<String>(getHeaders(email,password));
		ResponseEntity<OAuth2AccessToken> response = rest.exchange(requestService.getHost()+"/oauth/token?password={password}&username={username}&grant_type={grant_type}&scope={scope}",HttpMethod.POST,entity,OAuth2AccessToken.class,uri);
		
		return response.getBody();
	}

	
	private HttpHeaders getHeaders(String user,String password) {
		String encodedAuthorization = Base64.getEncoder().encodeToString(user.concat(":").concat(password).getBytes());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		httpHeaders.add("Content-Type","application/json;charset=utf-8");
		httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuthorization);
		return httpHeaders;
	}
	
	public void save(UserEntity userEntity) {
		userEntity.getRoles().add(getRoleDefault());
		Object[] params = new Object[]{userEntity.getEmail(),userEntity.getPassword(),GLOBAL,PASSWORD};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		jdbcTemplate.update(INSERT_CLIENT,params, types);
	}
	
	private RoleEntity getRoleDefault(){
		Optional<RoleEntity> role = Optional.ofNullable(roleRepository.findByRole(ROLE_DEFAULT));
		if(role.isPresent()){
			return role.get();
		}else{
			RoleEntity roleEntity = new RoleEntity();
			roleEntity.setRole(ROLE_DEFAULT);
			roleRepository.save(roleEntity);
			return roleEntity;
		}
	}
	
}
