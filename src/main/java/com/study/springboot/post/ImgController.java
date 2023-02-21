package com.study.springboot.post;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.springboot.follow.FollowDao;
import com.study.springboot.member.MemberController;
import com.study.springboot.member.MemberDao;
import com.study.springboot.post.img.PostImgEntity;
import com.study.springboot.post.img.PostImgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ImgController {
	private final MemberDao memberDao;
	private final PostDao postDao;
	private final ImgService imgService;
	private final PostImgRepository imgRepo;
	
	//프로필 이미지 출력 (mem_id로)
    @GetMapping("/profile/{mem_id}")
    public  Resource viewProfileImg(@PathVariable("mem_id") String mem_id) throws IOException{
    	String pfImgPath = memberDao.selectOneMember(mem_id).getProfimg();
    	UrlResource urlResource = new UrlResource("file:" + pfImgPath);
    	log.info(urlResource);
        return urlResource;
    }
    
    //포스트 이미지 출력 (img_id로)
    @GetMapping("/imgs/post/{imgId}")
    public Resource viewImageOne(@PathVariable("imgId") Long id, Model model) throws IOException{
        PostImgEntity imgFile = imgRepo.findById(id).orElse(null);
        UrlResource urlResource = new UrlResource("file:" + imgFile.getSavePath());
        return urlResource;
    }
    
    
    
}
