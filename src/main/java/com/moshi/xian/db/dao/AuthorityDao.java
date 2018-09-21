package com.moshi.xian.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.moshi.xian.db.pojo.Authority;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, Long>{
    Authority findByName(String name);
}
