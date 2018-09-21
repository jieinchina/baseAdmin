package com.moshi.xian.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moshi.xian.db.dao.AuthorityDao;
import com.moshi.xian.db.dao.RoleDao;
import com.moshi.xian.db.pojo.Authority;
import com.moshi.xian.db.pojo.Role;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService {
	@Autowired
	private RoleDao resDao;
	
	@Autowired
    private AuthorityDao authDao;
	
	public void addAuthority(Authority authority) throws Exception {
        if (authority == null) {
            throw new Exception("Invalid input authority");
        } else {
            log.info(String.format("Add Authority '%s'.", authority.getAuthority()));
            authDao.save(authority);
        }
    }
	
	public void addResource(Role role) throws Exception {
        if (role == null) {
            throw new Exception("Invalid input role");
        } else {
            log.info(String.format("Add Resource '%s'.", role.getName()));
            resDao.save(role);
        }
    }

}
