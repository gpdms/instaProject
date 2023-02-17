package com.study.springboot.post.img;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostImgDto {
	private int img_id;
	private int post_id;
	private String origin_name;
	private String save_name;
	private String save_path;
	private String delete_yn; //삭제여부 
	private Timestamp in_time; //등록일  
	private Timestamp del_time; //삭제일

    @Builder
    public PostImgDto(int post_id, String origin_name, String save_name, String save_path) {
    	this.post_id = post_id;
    	this.origin_name = origin_name;
        this.save_name = save_name;
        this.save_path = save_path;
    }


}
