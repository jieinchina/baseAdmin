package com.moshi.xian.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moshi.xian.db.pojo.Role;
import com.moshi.xian.db.pojo.User;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>{
    User findByName(String resname);
}
