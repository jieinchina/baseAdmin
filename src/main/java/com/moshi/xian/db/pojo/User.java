package com.moshi.xian.db.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String username;
	@Column(name = "pwd")
	private String password;
	private Boolean enabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	// @ManyToMany(fetch = FetchType.EAGER)
	@javax.persistence.Transient
	private Set<Authority> authorities = new HashSet<>();

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "pwdut")
	private Date lastPasswordResetDate;

	public Long getId() {
		return id;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public Set<Authority> getAuthorities() {
		if (this.authorities.isEmpty()) {
			Set<Authority> auths = new HashSet<>();
			this.roles.forEach((Role role) -> {
				if (role.getEnabled()) {
					role.getAuthorities().forEach((Authority auth) -> {
						if (auth.getEnabled()) {
							auths.add(auth);
						}
					});
				}
			});
			return auths;
		}
		return this.authorities;
	}

	public void setAuthorities(Set<Authority> auths) {
		this.authorities.clear();
		auths.forEach((Authority auth) -> {
			if (auth.getEnabled()) {
				setAuthority(auth);
			}
		});

	}

	public void setAuthority(Authority authority) {
		authorities.add(authority);
	}

	public void setRoles(Set<Role> roles) {
		this.roles.clear();
		this.authorities.clear();
		roles.forEach((Role role) -> {
			if (role.getEnabled()) {
				setRole(role);
			}
		});
	}

	public void setRole(Role role) {
		roles.add(role);
		role.getAuthorities().forEach((Authority auth) -> {
			if (auth.getEnabled()) {
				setAuthority(auth);
			}

		});
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public boolean hasRole(String val) {
		boolean ret = false;
		for (Role role : roles) {
			if ((role.getEnabled()) && role.getName().equals(val)) {
				ret = true;
			}
		}
		return ret;
	}
}
