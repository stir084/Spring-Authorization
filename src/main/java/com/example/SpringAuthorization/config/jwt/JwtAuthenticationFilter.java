package com.example.SpringAuthorization.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.SpringAuthorization.config.auth.PrincipalDetails;
import com.example.SpringAuthorization.dto.LoginRequestDto;
import com.example.SpringAuthorization.config.auth.PrincipalDetails;
import com.example.SpringAuthorization.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		

		ObjectMapper om = new ObjectMapper();
		LoginRequestDto loginRequestDto = null;
		try {
			loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(
						loginRequestDto.getUsername(), 
						loginRequestDto.getPassword());
		
		Authentication authentication =
				authenticationManager.authenticate(authenticationToken);
		
		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();

		return authentication;
	}

	// JWT Token 생성해서 response에 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
		
		String jwtToken = JWT.create()
				.withSubject(principalDetailis.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", principalDetailis.getUser().getId())
				.withClaim("username", principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
	}
	
}
