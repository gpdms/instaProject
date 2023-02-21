package com.study.springboot;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.springboot.comment.CommentDao;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class InstaPjApplicationTests {

	
	@Autowired
	CommentDao commentDao;
	
	@Test
	void contextLoads() {
	}
	
	
	@Test
	void test1() {
		commentDao.findSubComByPostId(1233);
		log.info(commentDao.findSubComByPostId(1233));
	}
	
}
