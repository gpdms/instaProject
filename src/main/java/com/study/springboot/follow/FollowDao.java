package com.study.springboot.follow;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FollowDao {
	public List<FollowDto> selectFollower(String mem_id);
	public List<FollowDto> selectFollowing(String mem_id);
}
