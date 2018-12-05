package com.haniln.hicp.spring.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring Security
 * @author  : Lee Jung Min
 * @version : 2018-11-22 creation
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Cross Site Request Forgery
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and().headers().frameOptions().disable()
            .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/", "/login").permitAll()
                //.antMatchers("/settings/**").hasAnyAuthority("admin")
                //.antMatchers("/settings/**").hasAnyAuthority("user")
            //.anyRequest().authenticated()
            //.and().requiresChannel().anyRequest().requiresSecure() // SSL
            .and().logout();
    }
}