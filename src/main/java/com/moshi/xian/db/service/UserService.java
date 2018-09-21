package com.moshi.xian.db.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.moshi.xian.db.pojo.Authority;
import com.moshi.xian.db.pojo.Role;
import com.moshi.xian.db.pojo.User;

public interface UserService extends UserDetailsService{
	void addRole(Role role)throws Exception;
    void addAuthority(Authority authority) throws Exception;
    void addUser(User user) throws Exception;
	User findByUsername(String string);
}
