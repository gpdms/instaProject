package com.study.springboot.post;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.springboot.post.img.PostImgEntity;
import com.study.springboot.post.img.PostImgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostService {
	private final PostDao postDao;	
	private final ImgService imgService;
    
	public int insertPost(PostDto post) {
    	//post테이블에 insert하고 해당 post_id를 바로 가져온다.(MyBatis-postDao.xml)
        post.setContent(post.getContent().replace("\r\n","<br>")); //줄 개행
    	int insert_result = postDao.insertPost(post);
        int post_id  = post.getPost_id();
        
        //post테이블에 insert 성공시에 post_img 테이블에 insert시도한다.
        if (insert_result != 0)
        	return post_id;
        
        return 0;
	}
	
    public Map<String, Integer> calPostTime (int post_id) {
    	int postSec = postDao.diffPostTime(post_id);
    	log.info(postSec);
    	int postMin = postSec / 60;
    	int postHour = postMin / 60;
    	int postDay = postHour / 24;
    	int postWeek = postDay / 7;
    	
    	Map<String, Integer> postTimeMap = new HashMap<>();
    	postTimeMap.put("sec", postSec);
    	postTimeMap.put("min", postMin);
    	postTimeMap.put("hour", postHour);
    	postTimeMap.put("day", postDay);
    	postTimeMap.put("week", postWeek);
		return postTimeMap;
    	
    }
}