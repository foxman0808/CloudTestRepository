package com.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class UserController {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@GetMapping("/UserInfo/{userId}")
	@ResponseBody
	public UserInfo getUserInfo(@PathVariable("userId") Integer id) {
		String sql = "SELECT id,user_name,name,age,balance FROM userInfos WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		UserInfo result = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new UserInfo(rs.getInt("id"),
				rs.getString("user_name"), rs.getString("name"), rs.getInt("age"), rs.getBigDecimal("balance")));
		return result;
	}
}
