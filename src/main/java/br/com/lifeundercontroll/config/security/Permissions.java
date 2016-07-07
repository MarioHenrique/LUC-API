package br.com.lifeundercontroll.config.security;

public class Permissions {

	
	/* User */
	public static final String login = "hasAuthority('login')";
	public static final String createUser = "hasAuthority('createUser')";
	public static final String updateUser = "hasAuthority('updateUser')";
	public static final String info = "hasAuthority('infoUser')"; 
	
	/* Bills */
	public static final String createBill = "hasAuthority('createBill')";
	public static final String getBill = "hasAuthority('getBill')";

	
}
