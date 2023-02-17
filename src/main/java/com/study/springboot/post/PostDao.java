package com.study.springboot.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.post.img.PostImgDto;


@Mapper
public interface PostDao {
	public PostDto selectOnePost(int post_id); 
	public List<PostDto> selectAllMyPost(String mem_id);
	public int insertPost(PostDto post);
	public List<PostImgDto> selectAllImgByPost(int post_id);
	public int countMyPost(String mem_id);
	public int diffPostTime(int post_id);
}
