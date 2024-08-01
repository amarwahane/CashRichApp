package com.cashrich.cashrichapp.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.cashrich.cashrichapp.model.TokenType;
import com.cashrich.cashrichapp.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessTokenFilter extends OncePerRequestFilter{
	
	public static String CURRENT_USER="";
	
	@Autowired
	private JwtTokenUtil jwtTokenUtils;
	
	@Autowired 
	private UserService userService;
	

	 public JwtAccessTokenFilter(JwtTokenUtil jwtTokenUtils, UserService userService) {
	        this.jwtTokenUtils = jwtTokenUtils;
	        this.userService = userService;
	 }
	 @Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	         throws ServletException, IOException {
	     try {
	         final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

	         if (authHeader == null || !authHeader.startsWith(TokenType.Bearer.name())) {
	             filterChain.doFilter(request, response);
	             return;
	         }

	         final String token = authHeader.substring(7);
	         final String userName = jwtTokenUtils.extractUsername(token);

	         if (!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
	             UserDetails userDetails = userService.loadUserByUsername(userName);

	             if (jwtTokenUtils.validateToken(token, userDetails)) {
	                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                 SecurityContextHolder.getContext().setAuthentication(authToken);
	             } else {
	            	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	             }
	         }

	         filterChain.doFilter(request, response);
	     } catch (ExpiredJwtException e) {
	         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	         response.getWriter().write(e.getMessage());
	     } catch (Exception e) {
	         response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	         response.getWriter().write(e.getMessage());
	     
	     }
	 }

}


