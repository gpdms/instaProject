package com.study.springboot.comment;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor 
public class SubCommentDto {
	
	private int subcom_id;
	private int com_id;
	private String mem_id;
	private String subcom_text;
	private Timestamp subcom_time;
	private Timestamp up_time;

}
