package com.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	String key="asdfhgjfhdsfgdhfghjgjreawtjkdjhrezcvdhfcgbxxcbgaj";

	public String generateToken(String username) {
		Map<String, Object> claims=new HashMap<String, Object>();
		
		return Jwts.builder()
		.claims()
		.add(claims)
		.subject(username)
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
		.and()
		.signWith(getKey())
		.compact();
		
		
	
	}
	
	
		private SecretKey getKey() {
			byte[] keyBytes=Decoders.BASE64.decode(key);
			return Keys.hmacShaKeyFor(keyBytes);
		}

}
