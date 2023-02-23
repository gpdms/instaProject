package com.study.springboot.follow;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.study.springboot.member.MemberDto;

@Mapper
@Repository
public interface FollowDao {
	public List<FollowDto> selectFollower(String mem_id);
	public List<FollowDto> selectFollowing(String mem_id);
	public Integer checkFollow(@Param("sessionId") String sessionId, @Param("mem_id")String mem_id);
	public int addFollow(@Param("sessionId") String sessionId, @Param("mem_id")String mem_id);
	public int unfollow(@Param("sessionId") String sessionId, @Param("mem_id")String mem_id);
	
	//아이디 검색
		public List<MemberDto> searchId(String searchWord);
}

