package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    private static final String KEY = "pl4tzi";
    //generamos y retornamos un Jason Web Token a partir de los UserDeatails que hayamos definido
    public String generateToken(UserDetails userDetails){
        //builder() en una secuencia de metodo nos permite construir el JWT
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    //VALIDAMOS QUE EL TOKEN SEA CORRECTO
    public boolean validateToken(String token, UserDetails userDetails){
        //1. verificamos que el token creado se efectivamente para el usuario qu esta haciendo la peticion
        //2. Verificamos que el token no haya expirado o vencido
        //validamos que el usuario que hace la peticion sea el mismo que el que veiene en el token y que no haya expirado el token
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token){
        //en el subject esta el usuario de la peticion
        return getClaims(token).getSubject();
    }

    //verificamos si el token ya experio
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    //metodo que retorna los claims que son los objetos dentro de JWT
    private Claims getClaims(String token){
        //Tenemos una parse, le pasamos la llave de la firma, cuando se verifique que la firma es correcta
        //obtenemos los claims o cuerpo de mi JWT separado por cada uno de los objetos.
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
