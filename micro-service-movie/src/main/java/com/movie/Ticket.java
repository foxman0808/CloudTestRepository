package com.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ticket {
	private Integer id;
    private String movieName;
    private String seatNo;
}