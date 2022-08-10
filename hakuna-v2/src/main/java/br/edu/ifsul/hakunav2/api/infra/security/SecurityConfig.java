package br.edu.ifsul.hakunav2.api.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true) //habilita a Security por método no(s) controller(s)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Configuração para Basic Authentication
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic()
            .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //autenticação em memória
        auth
            // enable in memory based authentication with a user named "user" and "admin"
            .inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("user").password(encoder.encode("user")).roles("USER")
                .and()
                .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
    }
}
