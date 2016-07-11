package br.com.lifeundercontroll.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import br.com.lifeundercontroll.dto.Response.BillResponse;
import br.com.lifeundercontroll.dto.Response.UserResponse;
import br.com.lifeundercontroll.dto.request.UserRequest;
import br.com.lifeundercontroll.dto.request.UserUpdateRequest;
import br.com.lifeundercontroll.entity.BillEntity;
import br.com.lifeundercontroll.entity.UserEntity;
import br.com.lifeundercontroll.exceptions.ResourceAlreadyExist;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.repository.UserRepository;

public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Captor
	private ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);

	@Captor
	private ArgumentCaptor<String> userKey = ArgumentCaptor.forClass(String.class);

	ShaPasswordEncoder sha = new ShaPasswordEncoder();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserByInvalidToken(){
		
		String token = "123-456";
		
		when(userRepository.findByToken(any(String.class))).thenReturn(null);
		
		try {
			userService.getUserByToken(token);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			assertEquals("Usuario não encontrado",e.getMessage());
			verify(userRepository,times(1)).findByToken(userKey.capture());
			String tokenReturned = userKey.getValue();
			assertEquals(token,tokenReturned);
		}
		
	}
	
	@Test
	public void getUserByValidToken() throws ResourceNotFound{
		
		String token = "123-456";
		
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("email");
		userEntity.setName("mario");
		userEntity.setSalary(new BigDecimal("135.86"));
		userEntity.setToken("123-456");
		
		when(userRepository.findByToken(any(String.class))).thenReturn(userEntity);
		
		UserResponse userResponse = userService.getUserByToken(token);

		verify(userRepository,times(1)).findByToken(userKey.capture());
		
		String tokenReturned = userKey.getValue();
		
		assertEquals(token,tokenReturned);
		assertEquals(userEntity.getEmail(),userResponse.getEmail());
		assertEquals(userEntity.getName(),userResponse.getName());
		assertEquals(userEntity.getSalary(),userResponse.getSalary());
		assertEquals(userEntity.getToken(),userResponse.getToken());
		
	}
	
	@Test
	public void updateUserWithValidUserToken() throws ResourceNotFound {

		String token = "123-456";
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setNome("Mario");
		userUpdateRequest.setSalario(new BigDecimal("134.60"));
		userUpdateRequest.setToken(token);

		when(userRepository.findByToken(any(String.class))).thenReturn(new UserEntity());

		userService.updateUser(userUpdateRequest);

		verify(userRepository, times(1)).findByToken(userKey.capture());
		verify(userRepository, times(1)).save(userEntityCaptor.capture());

		String tokenReturned = userKey.getValue();
		UserEntity userEntity = userEntityCaptor.getValue();

		assertEquals(token, tokenReturned);
		assertEquals(userUpdateRequest.getNome(), userEntity.getName());
		assertEquals(userUpdateRequest.getSalario(), userEntity.getSalary());

	}

	@Test
	public void updateUserWithInvalidToken() {

		String token = "123-456";

		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setNome("Mario");
		userUpdateRequest.setSalario(new BigDecimal("134.60"));
		userUpdateRequest.setToken(token);

		when(userRepository.findByToken(any(String.class))).thenReturn(null);
		
		try {
			userService.updateUser(userUpdateRequest);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			assertEquals("Usuario não encontrado",e.getMessage());
			verify(userRepository,times(1)).findByToken(userKey.capture());
			String tokenReturned = userKey.getValue();
			assertEquals(token,tokenReturned);
			verify(userRepository,times(0)).save(any(UserEntity.class));
		}

	}

	@Test
	public void getBillWithInvalidUserToken() {

		when(userRepository.findByToken(any(String.class))).thenReturn(null);

		String userToken = "123-456";
		try {
			userService.getBills(userToken);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			verify(userRepository, times(1)).findByToken(userKey.capture());
			String token = userKey.getValue();

			assertEquals("Usuario não encontrado", e.getMessage());
			assertEquals(userToken, token);
		}

	}

	@Test
	public void getBillsWithValidUserToken() throws ResourceNotFound {

		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
		String userToken = "123-456";
		UserEntity user = new UserEntity();
		BillEntity bill = new BillEntity();
		bill.setDueDate(new Date());
		bill.setName("Conta de luz");
		bill.setValue(new BigDecimal("200.50"));
		user.setBills(new ArrayList<BillEntity>());
		user.getBills().add(bill);

		when(userRepository.findByToken(any(String.class))).thenReturn(user);

		List<BillResponse> bills = userService.getBills(userToken);
		BillResponse billResponse = bills.get(0);

		assertEquals(bills.size(), 1);
		assertEquals(sdf.format(bill.getDueDate()), billResponse.getDueDate());
		assertEquals(bill.getName(), billResponse.getName());
		assertEquals(bill.getValue(), billResponse.getValue());

		verify(userRepository, times(1)).findByToken(userKey.capture());

		String token = userKey.getValue();

		assertEquals(userToken, token);

	}

	@Test
	public void loginInvalid() {

		String password = "password";
		String email = "email";

		when(userRepository.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(null);

		try {
			userService.login(email, password);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			assertEquals("Usuario ou senha incorreto", e.getMessage());
			verify(userRepository, times(1)).findByEmailAndPassword(userKey.capture(), userKey.capture());
			List<String> userAndPassword = userKey.getAllValues();
			assertEquals(email, userAndPassword.get(0));
			assertEquals(sha.encodePassword(password, null), userAndPassword.get(1));
		}

	}

	@Test
	public void loginWithSuccess() throws ResourceNotFound {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("m@m.com");
		userEntity.setName("Mario");
		userEntity.setSalary(new BigDecimal("135.50"));
		userEntity.setToken("123.456");

		when(userRepository.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(userEntity);

		String email = "email";
		String password = "password";

		UserResponse user = userService.login(email, password);

		assertEquals(userEntity.getEmail(), user.getEmail());
		assertEquals(userEntity.getName(), user.getName());
		assertEquals(userEntity.getSalary(), user.getSalary());
		assertEquals(userEntity.getToken(), user.getToken());

		verify(userRepository, times(1)).findByEmailAndPassword(userKey.capture(), userKey.capture());

		List<String> emailAndPassword = userKey.getAllValues();
		assertEquals(email, emailAndPassword.get(0));
		assertEquals(sha.encodePassword(password, null), emailAndPassword.get(1));

	}

	@Test
	public void createUserError() {
		UserRequest user = new UserRequest();
		user.setEmail("m@m.com");

		when(userRepository.findByEmail(any(String.class))).thenReturn(new UserEntity());

		try {
			userService.createUser(user);
			assertTrue(false);
		} catch (ResourceAlreadyExist e) {
			assertEquals("Email já cadastrado", e.getMessage());
			verify(userRepository, times(1)).findByEmail(userKey.capture());
			verify(userRepository, times(0)).save(any(UserEntity.class));

			String email = userKey.getValue();
			assertEquals(user.getEmail(), email);
		}

	}

	@Test
	public void createUserWithSuccess() throws ResourceAlreadyExist {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("m@m.com");
		userRequest.setName("mario");
		userRequest.setPassword("12345");
		userRequest.setSalary(new BigDecimal("1000.50"));

		when(userRepository.findByEmail(any(String.class))).thenReturn(null);

		userService.createUser(userRequest);

		verify(userRepository, times(1)).findByEmail(userKey.capture());
		verify(userRepository, times(1)).save(userEntityCaptor.capture());

		String email = userKey.getValue();

		assertEquals(userRequest.getEmail(), email);

		UserEntity userEntityReturned = userEntityCaptor.getValue();

		assertEquals(userRequest.getEmail(), userEntityReturned.getEmail());
		assertEquals(userRequest.getName(), userEntityReturned.getName());
		assertEquals(userRequest.getSalary(), userEntityReturned.getSalary());
		assertEquals(sha.encodePassword(userRequest.getPassword(), null), userEntityReturned.getPassword());
		assertEquals(
				sha.encodePassword(userRequest.getEmail() + userRequest.getEmail() + userRequest.getPassword(), null),
				userEntityReturned.getToken());

	}

}
