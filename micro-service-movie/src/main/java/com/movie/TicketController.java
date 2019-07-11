package com.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.movie.pojo.Ticket;
import com.user.pojo.UserInfo;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class TicketController {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${userInfo.userServiceUrl}")
	private String userServiceUrl;

	@GetMapping("/Ticket")
	@ResponseBody
	public String getTicket(@RequestParam("ticketId") Integer ticketId,@RequestParam("userId") Integer userId) {

		String url = this.userServiceUrl + userId;
		UserInfo userInfo = this.restTemplate.getForObject(url, UserInfo.class);

		String sql = "SELECT id, movie_name, seat_no FROM tickets WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", ticketId);
		Ticket result = jdbcTemplate.queryForObject(sql, param,
				(rs, rowNum) -> new Ticket(rs.getInt("id"), rs.getString("movie_name"), rs.getString("seat_no")));
		String ticket = "チッケト　映画名：" + result.getMovieName() + ", 席番号：" + result.getSeatNo();
		String user = "ユーザー：" + userInfo.getName() + ", 残高：" + userInfo.getBalance();
		return ticket + "\r\n" + user;
	}
}
