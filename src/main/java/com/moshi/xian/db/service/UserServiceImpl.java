package com.moshi.xian.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moshi.xian.db.dao.AuthorityDao;
import com.moshi.xian.db.dao.RoleDao;
import com.moshi.xian.db.dao.UserDao;
import com.moshi.xian.db.pojo.Authority;
import com.moshi.xian.db.pojo.Role;
import com.moshi.xian.db.pojo.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;
    
    @Autowired
    private AuthorityDao authorityRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(String.format("Load user by username '%s'.", username));
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }

    @Override
    public void addUser(User user) throws Exception {
        if (user == null) {
            throw new Exception("Invalid input user");
        } else {
            log.info(String.format("Add User '%s'.", user.getUsername()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
    
    @Override
    public void addRole(Role role)throws Exception{
    	if (role == null) {
            throw new Exception("Invalid input role");
        } else {
            log.info(String.format("Add Role '%s'.", role.getName()));
            roleRepository.save(role);
            role.getAuthorities().forEach((Authority auth) -> {
            	authorityRepository.save(auth);
            });
        }
    }
    

    @Override
    public void addAuthority(Authority authority) throws Exception {
        if (authority == null) {
            throw new Exception("Invalid input authority");
        } else {
            log.info(String.format("Add Authority '%s'.", authority.getAuthority()));
            authorityRepository.save(authority);
        }

    }

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
