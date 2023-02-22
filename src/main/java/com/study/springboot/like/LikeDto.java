package com.study.springboot.like;

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
public class LikeDto {

	private int like_id;
	private int post_id;
	private String mem_id;
	
}
