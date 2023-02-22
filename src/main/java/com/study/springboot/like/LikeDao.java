package com.study.springboot.like;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeDao {

	public int likeInsert(LikeDto likeDto);
	public int likeDeleteTest(LikeDto likeDto);
}
