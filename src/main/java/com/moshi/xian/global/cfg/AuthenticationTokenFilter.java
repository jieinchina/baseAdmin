package com.moshi.xian.global.cfg;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.moshi.xian.db.pojo.User;
import com.moshi.xian.global.util.TokenUtil;


public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        log.info("checking authentication for user " + username);
        
        //!request.getContextPath().endsWith("/auth") && 
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	try {
        		// It is not compelling necessary to load the use details from the database. You could also store the information
                // in the token and read it from it. It's up to you ;)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
                // the database compellingly. Again it's up to you ;)
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                	Set<GrantedAuthority> auths = (Set<GrantedAuthority>) userDetails.getAuthorities();
                	if(((User)userDetails).hasRole("ROLE_ADMIN")) {
                		auths.add(new GrantedAuthority() {
    						@Override
    						public String getAuthority() {
    							return "r:admin:all";
    						}                			
                		});
                	}
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, auths);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else{
                	throw new ServletException("token is broken");
                }
        	}catch(Exception e) {
        		response.setContentType("application/json");
        		response.getWriter().write("{\"status\":\"401\", \"message\": \"jwt Unauthorized\" }");
            	response.flushBuffer();
            	return;
        	}            
        }

        chain.doFilter(request, response);
    }
}