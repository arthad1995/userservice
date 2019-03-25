package com.abc.userservice.userservice.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.abc.userservice.userservice.security.handlers.AuthenticationFailureHandlerImpl;
import com.abc.userservice.userservice.security.handlers.AuthenticationSuccessHandlerImpl;
import com.google.common.hash.Hashing;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;
	
	@Autowired
	private AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()

				.authorizeRequests() // ,"/products/*"
				.antMatchers("/users/register").permitAll()
			
				//.antMatchers("/users").hasAnyAuthority("student") // hasRole
																								// /hasAnyRole("XXXX"),
																									// ROLE_xxxx
				.and().formLogin()
				.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureHandler(authenticationFailureHandlerImpl)
				.successHandler(authenticationSuccessHandlerImpl)
				.and().httpBasic();

	}

	@Bean // @Bean : put the return object into spring container.
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {

				return encode(rawPassword.toString()).equals(encodedPassword);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				
				return Hashing.sha256().hashString(rawPassword.toString(), StandardCharsets.UTF_8).toString();
			}
		};
		return encoder;
	}

	// Autowire the parameter for this function
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*"); // You should only set trusted site here. e.g. http://localhost:4200 means
												// only this site can access.
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
