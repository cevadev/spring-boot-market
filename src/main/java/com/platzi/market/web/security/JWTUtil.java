package com.platzi.market.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JWTUtil {
    private static final String KEY = "pl4tzi";
    //generamos y retornamos un Jason Web Token a partir de los UserDeatails que hayamos definido
    public String generateToken(UserDetails userDetails){
        //builder() en una secuencia de metodo nos permite construir el JWT
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }
}
