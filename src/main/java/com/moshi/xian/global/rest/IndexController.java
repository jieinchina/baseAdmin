package com.moshi.xian.global.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moshi.xian.db.pojo.User;
import com.moshi.xian.db.service.UserService;
import com.moshi.xian.global.util.TokenUtil;

@RestController
public class IndexController {

	 private final Log log = LogFactory.getLog(IndexController.class);
	
	 @Autowired
	 private TokenUtil jwtTokenUtil;
	 
	 @Value("${jwt.header}")
	 private String tokenHeader;
	 
	 @Autowired
	 private UserService userService;
	 
    //do not require authorization
    @GetMapping(value = {"/", "${base_path}"})
    public Map index(HttpServletRequest request) {
    	String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        log.info("user: " + username);
        
        Map map = new HashMap<>();
        map.put("status", "OK");
        map.put("message", "Welcome " + username);
        return map;
    }

    //require authorization
    @GetMapping(value = {"${base_path}/user"})
    public User user(HttpServletRequest request) {
    	String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        return userService.findByUsername(username);
    }    

    //require authorization Admin Role
    @GetMapping(value = {"${base_path}/admin"})
    public Map admin(HttpServletRequest request) {
    	String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        log.info("user: " + username);
        
        Map map = new HashMap<>();
        map.put("status", "OK");
        map.put("message", "Hello admininistrator " + username);
        return map;
    }   
    
    //require authorization Admin Role
    @GetMapping(value = {"${base_path}/admin/sub"})
    public Map adminsub(HttpServletRequest request) {
    	String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        log.info("user: " + username);
        
        Map map = new HashMap<>();
        map.put("status", "OK");
        map.put("message", "Hello admininistrator sub:" + username);
        return map;
    }   
    
    
}
