package com.andres.curso.springboot.app.springboot_crud.security.filter;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJWTConfig {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "bEARER ";
    public static final String HEADER_AUTHORIZATRION = "Authorisation";
    public static final String CONTENT_TYPE = "application/json";

}
