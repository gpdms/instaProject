package com.study.springboot.follow;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {
	private int flw_id;
	private String from_user;
	private String to_user;
	private Timestamp flw_date;
	
}
