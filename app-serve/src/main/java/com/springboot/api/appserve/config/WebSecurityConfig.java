package com.springboot.api.appserve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.api.appserve.jwt.JwtAuthorizationFilter;
import com.springboot.api.appserve.jwt.JwtTokenProvider;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Using cross-origin-ressources-sharing
		http.cors().and()
			.authorizeRequests()
			//there are public path
			.antMatchers("/resources/**","/error","/api/user/**").permitAll()
			//These can be reachable by just admin role
			.antMatchers("/api/admin/**").hasRole("ADMIN")
			//All remaining path should need authenticatation
			.anyRequest().fullyAuthenticated()
			.and()
			//logout will log the user out by invalidate session
			.logout().permitAll()
			.logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout", "POST"))
			.and()
			//login form and path
			.formLogin().loginPage("/api/user/login")
			.and()
			//enable basic authentication. http header: basic username:password
			.httpBasic().and()
			//Cross Side Request Forgery
			.csrf().disable();
		
		http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
			}
		};
	}

}
