package com.study.springboot.comment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {
	public int insertComment(CommentDto commentDTO);
	public List<CommentDto> selectAllComment(int post_id);
	public int deleteComment(int com_id);
	public int calculateCommentTime(int com_id);
	public int insertSubComment(SubCommentDto subCommentDTO);
	public List<SubCommentDto> selectAllSubComment(int com_id);
	
	public Map<String,String> findSubComByPostId(int post_Id);
	public List<Map<String,String>> test1();
	public List<Map<String,String>> test2();
	
}
