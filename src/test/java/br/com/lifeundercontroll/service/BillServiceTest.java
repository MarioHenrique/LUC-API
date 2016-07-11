package br.com.lifeundercontroll.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lifeundercontroll.dto.request.BillRequest;
import br.com.lifeundercontroll.dto.response.BillResponse;
import br.com.lifeundercontroll.entity.BillEntity;
import br.com.lifeundercontroll.entity.UserEntity;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.repository.BillRepository;
import br.com.lifeundercontroll.repository.UserRepository;

public class BillServiceTest {

	@Mock
	private BillRepository billRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private BillService billService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Captor
	private ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);

	@Captor
	private ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

	@Captor
	private ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

	@Test
	public void createBillWithSuccess() throws ResourceNotFound {

		BillRequest billRequest = new BillRequest();
		billRequest.setUserToken("123-456");
		billRequest.setName("Mario");
		billRequest.setValue(new BigDecimal("350.65"));
		billRequest.setDueDate(new Date());

		UserEntity user = new UserEntity();
		user.setBills(new ArrayList<BillEntity>());

		when(userRepository.findByToken(any(String.class))).thenReturn(user);

		billService.createBill(billRequest);

		verify(userRepository, times(1)).findByToken(tokenCaptor.capture());
		verify(userRepository, times(1)).save(userEntityCaptor.capture());

		UserEntity userCaptured = userEntityCaptor.getValue();
		BillEntity bill = userCaptured.getBills().get(0);

		assertEquals(user, userCaptured);
		assertEquals(bill.getDueDate(), billRequest.getDueDate());
		assertEquals(bill.getName(), billRequest.getName());
		assertEquals(bill.getValue(), billRequest.getValue());

		String token = tokenCaptor.getValue();

		assertEquals(billRequest.getUserToken(), token);
	}

	@Test
	public void createBillError() {
		BillRequest billRequest = new BillRequest();
		billRequest.setUserToken("123-456");

		when(userRepository.findByToken(any(String.class))).thenReturn(null);

		try {
			billService.createBill(billRequest);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			assertEquals("Token de usuario invalido", e.getMessage());
			verify(userRepository, times(1)).findByToken(any(String.class));
			verify(userRepository, times(0)).save(any(UserEntity.class));
		}

	}

	@Test
	public void getBillWithSuccess() throws ResourceNotFound {

		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
		BillEntity billEntity = new BillEntity();
		billEntity.setDueDate(new Date());
		billEntity.setName("Mario");
		billEntity.setValue(new BigDecimal("300.56"));
		billEntity.setId(12L);

		Long id = 12L;

		when(billRepository.findOne(any(Long.class))).thenReturn(billEntity);

		BillResponse billResponse = billService.getBillById(id);

		assertEquals(sdf.format(billEntity.getDueDate()), billResponse.getDueDate());
		assertEquals(billEntity.getName(), billResponse.getName());
		assertEquals(billEntity.getValue(), billResponse.getValue());

		verify(billRepository, times(1)).findOne(idCaptor.capture());

		Long idReturned = idCaptor.getValue();

		assertEquals(id, idReturned);

	}

	@Test
	public void getBillError() {

		Long id = 12L;

		when(billRepository.findOne(id)).thenReturn(null);

		try {
			billService.getBillById(id);
			assertTrue(false);
		} catch (ResourceNotFound e) {
			assertEquals("Conta não encontrada", e.getMessage());
			verify(billRepository, times(1)).findOne(idCaptor.capture());
			Long idReturned = idCaptor.getValue();
			assertEquals(id, idReturned);
		}

	}

}
