package com.spring.resttemplateclient;

public class RoleDto {
	
	private Long id;
	private String name;
	
	public RoleDto() {
	}
	
	public RoleDto(String name) {
		this.name=name;
	}
	
	public RoleDto(Long id) {
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleDto [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}	
}
