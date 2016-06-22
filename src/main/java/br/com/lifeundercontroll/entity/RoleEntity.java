package br.com.lifeundercontroll.entity;

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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String role;
	
	@ManyToMany( mappedBy = "roles")
	private Set<UserApiEntity> users = new HashSet<UserApiEntity>();
	
	private RoleEntity(){}

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

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

	public Set<UserApiEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserApiEntity> users) {
		this.users = users;
	}
	
}
