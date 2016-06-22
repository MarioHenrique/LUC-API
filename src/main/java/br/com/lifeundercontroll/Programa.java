package br.com.lifeundercontroll;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Programa {

	public static void main(String[] args) {
		
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		String senha = sha.encodePassword("123mudar",null);
		System.out.println(senha);
	}
	
}
