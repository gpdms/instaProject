package com.study.springboot.comment;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {
	
	public int insertComment(CommentDto commentDTO);
	public List<CommentDto> selectAllComment(int post_id);
	public int deleteComment(int com_id);
	public int calculateCommentTime(int com_id);
	public int insertSubComment(SubCommentDto subCommentDTO);
	public List<SubCommentDto> selectAllSubComment(int com_id);
	public List<SubComShowDto> findSubComByPostId(int post_Id);
	public int deleteSubComment(int subcom_id);
	
	public int findComTime(int com_id);
	
	
	// findComTime
	// calComTimeSec
	// calComTimeMin
	
	
	/* public int diffCommTime(int com_id); */
}
