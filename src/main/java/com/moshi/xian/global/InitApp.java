package com.moshi.xian.global;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.moshi.xian.db.dao.UserDao;
import com.moshi.xian.db.pojo.Authority;
import com.moshi.xian.db.pojo.Role;
import com.moshi.xian.db.pojo.User;
import com.moshi.xian.db.service.RoleService;
import com.moshi.xian.db.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitApp implements ApplicationListener<ContextRefreshedEvent> {
	
	@Value(value="${base_path}")
	private String BasePath;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null == userService.findByUsername("admin")) {
			Authority resAdmin =  new Authority(BasePath+"/admin", "a:admin:root");
			Authority resAdminSub =  new Authority(BasePath+"/admin/sub", "a:admin:sub");
			
			Role roleAdmin = new Role("ROLE_ADMIN");
			roleAdmin.setAuthority(resAdmin);
			
			Role roleTest = new Role("ROLE_TEST");

			User adminUser = new User();
			adminUser.setUsername("admin");
			adminUser.setPassword("123456");
			adminUser.setRole(roleAdmin);
			adminUser.setLastPasswordResetDate(new Date());
			//adminUser.setAuthority(resAdminSub);
						
			User testUser = new User();
			testUser.setUsername("test");
			testUser.setPassword("123456");
			testUser.setRole(roleTest);
			testUser.setLastPasswordResetDate(new Date());

			try {
				roleService.addAuthority(resAdmin);
				roleService.addAuthority(resAdminSub);
				userService.addRole(roleAdmin);
				userService.addUser(adminUser);
				
				userService.addRole(roleTest);
				userService.addUser(testUser);
				
			
			} catch (Exception ex) {
				log.error(ex.toString());
			}
		}

		log.info("... after sprint boot init...");
	}

}