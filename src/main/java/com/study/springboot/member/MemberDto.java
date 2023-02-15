package com.study.springboot.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder //테스트할때 쓰려고.
public class MemberDto {
	private String mem_id;
	private String mem_pw;
	private String nickname;
	private String email;
	private String profimg;
}
