package com.study.springboot.Comment;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {

	
	private int com_id;
	private String mem_id;
	private int post_id;
	private String com_text;
	private Timestamp com_time;
	private String up_time;
	
}
