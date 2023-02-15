package com.study.springboot.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDao {
	public int getId(MemberDto dto);
	public int addMember(MemberDto dto);
	public MemberDto login(MemberDto dto);
}
