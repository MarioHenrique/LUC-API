package br.com.lifeundercontroll.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import br.com.lifeundercontroll.Dto.Response.UserResponse;
import br.com.lifeundercontroll.Dto.request.UserRequest;
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
	public void loginInvalid() {

		String password = "password";
		String email = "email";

		when(userRepository.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(null);

		try {
			userService.login(email, password);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			assertEquals("Usuario ou senha incorreto", e.getMessage());
			verify(userRepository,times(1)).findByEmailAndPassword(userKey.capture(),userKey.capture());
			List<String> userAndPassword = userKey.getAllValues();
			assertEquals(email,userAndPassword.get(0));
			assertEquals(sha.encodePassword(password, null),userAndPassword.get(1));
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
