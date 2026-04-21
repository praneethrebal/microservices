package com.gateway.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorrelationFilter implements GlobalFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String headerName="X-Correlation-Id";
		String corelatedId=exchange.getRequest()
								.getHeaders()
								.getFirst(headerName);
		if(corelatedId == null)
		{
			corelatedId=UUID.randomUUID().toString();
		}
		
		ServerHttpRequest request=exchange.getRequest()
								.mutate()
								.header(headerName, corelatedId)
								.build();
		  System.out.println("Correlation ID: " + corelatedId);
		
		
		return chain.filter(exchange.mutate().request(request).build());
	}

}
