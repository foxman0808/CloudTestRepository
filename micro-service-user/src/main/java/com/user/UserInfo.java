package com.user;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
	public UserInfo() {}
	private Integer id;
	private String username;
	private String name;
	private Integer age;
	private BigDecimal balance;
}