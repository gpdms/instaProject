package com.study.springboot.comment;

import java.sql.Timestamp;

import com.study.springboot.post.PostDto;

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
public class CommentDto {

	private int com_id;
	private String mem_id;
	private int post_id;
	private String com_text;
	private Timestamp com_time;
	private String up_time;
}
