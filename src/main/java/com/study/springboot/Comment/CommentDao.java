package com.study.springboot.comment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {
	public int insertComment(CommentDto commentDTO);
	public List<CommentDto> selectAllComment(int post_id);
	public int deleteComment(int com_id);
	public int calculateCommentTime(int com_id);
}
