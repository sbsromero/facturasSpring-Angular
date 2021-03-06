package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.auth.handler.LoginSuccessHandler;
import com.example.demo.models.service.JpaUserDetailService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String ADMIN_ROLE = "ADMIN";
	private static final String USER_ROLE = "USER";
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**","/listar","/locale")
		.permitAll()
//		.antMatchers("/ver/**").hasAnyRole(USER_ROLE)
//		.antMatchers("/uploads/**").hasAnyRole(USER_ROLE)
//		.antMatchers("/form/**").hasAnyRole(ADMIN_ROLE)
//		.antMatchers("/eliminar/**").hasAnyRole(ADMIN_ROLE)
//		.antMatchers("/factura/**").hasAnyRole(ADMIN_ROLE)
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successHandler)
		.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		UserBuilder users = User.builder().passwordEncoder(encoder::encode);

//		build.inMemoryAuthentication()
//		.withUser(users.username("admin").password("12345").roles(ADMIN_ROLE,USER_ROLE))
//		.withUser(users.username("andres").password("12345").roles(USER_ROLE));
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}
