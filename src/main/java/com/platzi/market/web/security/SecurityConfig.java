package com.platzi.market.web.security;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Inyectamos nuestro servicio para configurar el user y password
    @Autowired
    PlatziUserDetailsService platziUserDetailsService;

    /*con este metodo indicamos cual es el user y password valido segun lo especificado en PlatziUserDetailsService.java*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(platziUserDetailsService);
    }

    /*
    * Indicamos autorizar todas las operaciones que se hagan Authenticate ya que para invocar el servicio Authenticate
    * no necesitan estar autenticados ya que con este servicio es que se autenticaran
    * */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //deshabilitamos las peticiones cruzadas
        //autorize las peticiones
        //indicamos lo que queremos permitir con antMatchers()
        //lo que vamos a permitr sera el servicio authenticate, es decir todas las peticiones sin importar lo que haya antes y que terminen en authenticate las debe permitir
        http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate").permitAll()
                //ahora indicamos que el resto de peticiones si requieren autenticacion
                .anyRequest().authenticated();//ahora spring hara que las peticiones que terminen en autenticate las va a permitir todas las demas necesitaran autenticacion
    }

    //permitimos que sea spring que siga controlando la autenticacion
    @Override
    @Bean //lo elegimos como el gestion de autenticacion de la app
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //Nos aseguramos que los enlaces a la documentacion con Swagger sean permitidos ya que no requieren estar autenticados
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
    }
}
