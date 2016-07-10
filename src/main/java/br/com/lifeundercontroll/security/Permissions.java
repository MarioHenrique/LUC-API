package br.com.lifeundercontroll.security;

public class Permissions {

	/* User */
	public static final String login = "hasAuthority('login')";
	public static final String createUser = "hasAuthority('createUser')";
	public static final String updateUser = "hasAuthority('updateUser')";
	public static final String info = "hasAuthority('infoUser')"; 
	
	/* Bills */
	public static final String POST_BILL = "hasAuthority('createBill')";
	public static final String GET_BILL = "hasAuthority('getBill')";
	public static final String GET_LIST_BILL = "hasAuthority('getUserBills')";
	public static final String PUT_BILL = "hasAuthority(putBill)";

}
