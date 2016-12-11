package br.com.lifeundercontroll.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table( name = "roles" )
public class RoleEntity implements Serializable{

	private static final long serialVersionUID = 1890261499634295711L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String role;
	
	@ManyToMany( mappedBy = "roles")
	private Set<UserEntity> users = new HashSet<UserEntity>();
	
	public RoleEntity(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
