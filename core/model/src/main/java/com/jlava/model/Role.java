package com.jlava.model;

public class Role {
	private int roleId;
	private String roleDesc;

	public Role() {}
	public Role(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}