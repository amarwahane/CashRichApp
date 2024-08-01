package com.cashrich.cashrichapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cashrich.cashrichapp.service.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtAccessTokenFilter jwtAccessTokenFilter;
	
	
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    
    
    
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
	            .cors(withDefaults())
	            .csrf(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/login").permitAll()
	                .requestMatchers("/sign-up").permitAll()
	                .requestMatchers("/profile/**").authenticated()
	                .requestMatchers("/crypto/**").authenticated()
	            )
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class)
	            .exceptionHandling(ex -> ex
	                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	            )
	            .httpBasic(withDefaults())
	            .build();
    	
    	
    }
    
    
	@Bean
	PasswordEncoder passwordEncoder() {
	return NoOpPasswordEncoder.getInstance();
	}
	 
	 	@Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    public AuthenticationProvider authenticationProvider() {
	        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }


	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new UserDetailsService() {
	            @Override
	            public UserDetails loadUserByUsername(String emaild) throws UsernameNotFoundException {
	                return (UserDetails) userService.loadUserByUsername(emaild);
	            }
	        };
	    }

}
