package br.com.lifeundercontroll.security;

public class Permissions {

	/* User */
	public static final String LOGIN = "hasAuthority('login')";
	public static final String CREATE_USER = "hasAuthority('createUser')";
	public static final String UPDATE_USER = "hasAuthority('updateUser')";
	public static final String USER_INFO = "hasAuthority('infoUser')"; 
	
	/* Bills */
	public static final String POST_BILL = "hasAuthority('createBill')";
	public static final String GET_BILL = "hasAuthority('getBill')";
	public static final String GET_LIST_BILL = "hasAuthority('getUserBills')";
	public static final String PUT_BILL = "hasAuthority('putBill')";
}
