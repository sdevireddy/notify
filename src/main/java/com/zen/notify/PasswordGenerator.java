package com.zen.notify;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	public String generate() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}
}