package com.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@PropertySource("classpath:application.properties")
public class LDAPSpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private AccessDeniedHandler accessDeniedHandler;
	
	//@Value("${security.saml2.appId}")
	//private String appID;

	//@Value("${security.saml2.entityId}")
	//private String entityId;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/home", "/about").permitAll()
				.antMatchers("/admin/**").access("hasRole('ADMIN')")
				.antMatchers("/user/**").access("hasRole('USER')")
				.anyRequest().authenticated()
				.and()
			.formLogin().loginPage("/login").permitAll()
				.and()
			.logout().permitAll()
				.and()
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 auth.ldapAuthentication()
			 .userDnPatterns("(uid={0},ou=people)")
		        .userSearchBase("(ou=people)")
		        .userSearchFilter("(uid={0})")
		        .groupSearchBase("(ou=groups)")
		        .groupSearchFilter("(uniqueMember={0})")
	         .contextSource()
	         .url("ldap://localhost:8389/dc=springframework,dc=com");
	}
}
