package com.example.pp_coursework_server;

import com.example.pp_coursework_server.service.UserRepositoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserRepositoryUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable();
            http.httpBasic();
            /*
            http
                    .authorizeRequests()
                    .antMatchers("/categories", "products/**").hasRole("ADMIN")
                    .and()
                    .csrf().disable()
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
            auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
