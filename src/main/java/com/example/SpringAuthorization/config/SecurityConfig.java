package com.example.SpringAuthorization.config;

import com.example.SpringAuthorization.config.jwt.JwtAuthenticationFilter;
import com.example.SpringAuthorization.config.jwt.JwtAuthorizationFilter;
import com.example.SpringAuthorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CorsConfig corsConfig;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.addFilter(corsConfig.corsFilter())
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin().disable()
				.httpBasic().disable()

				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
				.authorizeRequests()
				.antMatchers("/api/v1/admin/**")
				.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				.antMatchers("/api/v1/manage/**")
				.access("hasRole('ROLE_MANAGER')")
				.anyRequest().authenticated()//.permitAll()
				.and().build();
	}
}






