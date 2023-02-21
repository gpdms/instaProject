package com.study.springboot.follow;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FollowDao {
	public List<FollowDto> selectFollower(String mem_id);
	public List<FollowDto> selectFollowing(String mem_id);
	public Integer checkFollow(@Param("sessionId") String sessionId, @Param("mem_id")String mem_id);
	public int addFollow(@Param("sessionId") String sessionId, @Param("mem_id")String mem_id);
	public int unfollow(@Param("sessionId") String sessionId, @Param("mem_id")String mem_id);
}
