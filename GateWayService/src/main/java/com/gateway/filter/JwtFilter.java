package com.gateway.filter;

import javax.crypto.SecretKey;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter {
	
	
	String SECRET="asdfhgjfhdsfgdhfghjgjreawtjkdjhrezcvdhfcgbxxcbgaj";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		 String path = exchange.getRequest().getURI().getPath();
		 
		  // Public APIs
	        if (path.contains("/auth")) {
	            return chain.filter(exchange);
	        }
	        String header = exchange.getRequest().getHeaders().getFirst("Authorization");

	        if (header == null || !header.startsWith("Bearer ")) {
	            throw new RuntimeException("Missing Token");
	        }

	        String token = header.substring(7);

	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(getKey())   // 🔥 use same key logic
	                .build()
	                .parseClaimsJws(token)
	                .getBody();

	        String role = claims.get("role", String.class);

	        // Example role check
	        if (path.startsWith("/admin") && !"ADMIN".equals(role)) {
	            throw new RuntimeException("Access Denied");
	        }

	        return chain.filter(exchange);
	}

	
	private SecretKey getKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
}
