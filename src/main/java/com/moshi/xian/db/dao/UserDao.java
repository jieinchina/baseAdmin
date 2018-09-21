package com.moshi.xian.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.moshi.xian.db.pojo.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
