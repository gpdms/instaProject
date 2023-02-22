package com.study.springboot.like;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeDao {
	public int like(LikeDto postlike);
	public int likenum(LikeDto postlike);
	public int unlike(LikeDto postlike);
	public int likeheart(LikeDto postlike);
}
