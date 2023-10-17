package br.com.mateussilva.todolist.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Cors implements Filter {
  
	private static final Logger logger = LoggerFactory.getLogger(Cors.class);
	
	private final String MAX_AGE_SECS = "3600";
	
	@Value("${app.cors.allowedOrigins}")
	private String allowedOrigins;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
			throws IOException, ServletException {
			
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String allowedOrigin = getAllowedOriginByRequest(request.getHeader("Origin"));
		
		logger.info("Origin: {}, Found: {}, Allowed: {}", request.getHeader("Origin"), allowedOrigin != null, allowedOrigins);
		
		response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if ("OPTIONS".equals(request.getMethod()) 
			&& allowedOrigin != null && allowedOrigin.equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PATCH, PUT, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", MAX_AGE_SECS);
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
	}
	
	private String getAllowedOriginByRequest(String requestOrigin) {
		
		String[] origins = allowedOrigins.split(",");
		
		Optional<String> origin = Arrays.stream(origins)
				.filter(o -> o.trim().equalsIgnoreCase(requestOrigin)).findFirst();
		
		return origin.isPresent() ? origin.get() : null;
	}
}