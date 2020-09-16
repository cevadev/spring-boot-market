package com.platzi.market.web.security.filter;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//este filter intercepta todas las peticiones que recibe la app y verifica si el JWT es correcto
//OncePerRequestFilter -> haceque nuestra clase se ejecute cada vez que exista una peticion
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //verificamos si lo qe viene en el encabezado de la peticion es un token y si el mismo es correcto
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer"))
        {
            //si se cumplen las codiciones entonces prssuntamente viene un Web token
            //capturamos el web token
            String jwt = authorizationHeader.substring(7);
            //verificamos el user del web token
            String username = jwtUtil.extractUsername(jwt);
            //verificamos el username y que no exista ninguna autenticacion paa este usuario
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //verificamos que el usuario en el web token exista dentro del sistema de autenticacion
                UserDetails userDetails = platziUserDetailsService.loadUserByUsername(username);

                //preguntamos si el jason web token es correcto
                if(jwtUtil.validateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
