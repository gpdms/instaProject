package com.study.springboot.post;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private int post_id;
	private String mem_id;
	private String content;
	private Timestamp posttime;
	private Timestamp uptime;
	private int likes;
}
