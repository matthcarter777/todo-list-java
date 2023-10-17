package br.com.mateussilva.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
  @Value("${app.security.csrf.enabled}")
	private boolean csrfEnabled = false;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		if (!csrfEnabled) {
			http.csrf().disable();
		}

		return http.build();
	}
}
