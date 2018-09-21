package com.moshi.xian.db.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Role implements Serializable {

	private static final long serialVersionUID = 2L;

	public Role() {
	}
    public Role(String resname) {
    	name = resname;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @JsonIgnore
    private Boolean enabled = true;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
    
    public void setAuthority(Authority authority){
        authorities.add(authority);
    }
}
