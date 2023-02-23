package com.study.springboot.like;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeDao {
	public int like(LikeDto postlike);
	public int likeOne(LikeDto postlike);
	
	public int unlike(LikeDto postlike);
	public int likeheart(LikeDto postlike);
	public LikeDto likeview(@Param("post_id")int post_id, @Param("mem_id") String mem_id);
	
	public List<Integer> mylike(@Param("mem_id") String mem_id);
}
