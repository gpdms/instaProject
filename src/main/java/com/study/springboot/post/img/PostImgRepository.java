package com.study.springboot.post.img;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PostImgRepository extends JpaRepository<PostImgEntity,Long> {
	// where a and b
	public PostImgEntity findByImgIdAndDeleteYn(Long id, String delete_yn); 
	public List<PostImgEntity> findByPostIdAndDeleteYnOrderByInTimeDesc(int post_id, String delete_yn);
}
