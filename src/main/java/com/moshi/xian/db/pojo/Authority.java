package com.moshi.xian.db.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Authority  implements GrantedAuthority{

	private static final long serialVersionUID = 3L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    private Integer pid = 0; //层级关系
    
    private Integer type = 3; //1:menu 2:button 3:restapi

    @JsonIgnore
    private Boolean enabled = true;
    
    @JsonIgnore
    private String perm;

    public Authority() {
    }
    public Authority(String name, String permission) {
        this.name = name;
        this.perm = permission;
    }
    
    @Override
    public String getAuthority() {
        return perm;
    }
    
}