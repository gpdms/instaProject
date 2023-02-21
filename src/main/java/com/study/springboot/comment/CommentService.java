package com.study.springboot.comment;

import java.util.HashMap;
import java.util.Map;

public class CommentService {

	
	CommentDao commentdao;
	
	public Map<String,Integer> commdiffMethod(int com_id) {
		
		int commSec = commentdao.diffCommTime(com_id);
		//com_id로 찾은 댓글의 작성 시간(초)
		
		int commMin = commSec / 60;
    	int commHour = commMin / 60;
    	int commDay = commHour / 24;
    	int commWeek = commDay / 7;
    	
    	Map<String, Integer> commTimeMap = new HashMap<>();
    	commTimeMap.put("sec", commSec);
    	commTimeMap.put("min", commMin);
    	commTimeMap.put("hour", commHour);
    	commTimeMap.put("day", commDay);
    	commTimeMap.put("week", commWeek);
    	
		return commTimeMap;
	}
}
