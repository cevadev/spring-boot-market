package com.platzi.market.web.controller;

import com.platzi.market.domain.dto.AuthenticationRequest;
import com.platzi.market.domain.dto.AuthenticationResponse;
import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //gestor de autenticacion de spring
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    //inyectamos el JWT creado
    @Autowired
    private JWTUtil jwtUtil;

    //metodo que responde un JWT cuando alguien trata de iniciar sesion. El post por donde recibiara peticiones es /authenticate
    //como recibe las peticiones por medio de Post adicionamos la anotacion @RequestBody como parametro
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){

        try{
            //le decimos al gestor de autenticacion de spring que verifique si el usuario y la contraseña son correctos
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            //obtenemos los detalles de usuario del servicio que creamos PlatziUserdetailsService el cual se encarga de
            //generar la seguridad por usuario y contraseña.
            UserDetails userDetails = platziUserDetailsService.loadUserByUsername(request.getUsername());

            //generamos el JWT
            String jwt = jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        }
        catch(BadCredentialsException badCredentialsException)//se lanza la excepcion cuando no coincide usuario o password
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
