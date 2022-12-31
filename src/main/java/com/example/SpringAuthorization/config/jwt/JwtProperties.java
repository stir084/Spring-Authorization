package com.example.SpringAuthorization.config.jwt;

public interface JwtProperties {
	String SECRET = "loose";
	int EXPIRATION_TIME = 1000000;
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
