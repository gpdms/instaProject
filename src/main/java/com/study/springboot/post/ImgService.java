package com.study.springboot.post;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.springboot.member.MemberDao;
import com.study.springboot.member.MemberDto;
import com.study.springboot.post.img.PostImgEntity;
import com.study.springboot.post.img.PostImgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImgService {
	private final PostImgRepository imgRepository;
	private final MemberDao memberDao;
	
	@Value("${spring.servlet.multipart.location}")
    private String postImgDir;
	
	@Value("${file.dir}")
    private String profileImgDir;
	
	
	//포스트 이미지 저장 
    public List<Long> savePostImg(int post_id, List<MultipartFile> imgs) {
    	List<Long> imgIds = new ArrayList<Long>();
    	
        if (imgs.isEmpty()) {
            return imgIds;
        }
        
        for (MultipartFile imgFile: imgs) {
	        //확장자체크 : 업로드된 확장자가 이미지만 가능하도록 검사해야합니다
	        if(imgFile.getContentType().startsWith("image") == false){
	         	log.warn("this file is not image type");
	         	return imgIds;
	        }
	        
	        
	        // MultipartFile로부터 -->
	        // 원래 파일 이름 추출
	        String origin_name = imgFile.getOriginalFilename();
	        // 파일 이름으로 쓸 uuid 새로 생성  (서버에서 저장할 파일명이 겹치지 않도록)
	        String uuid = UUID.randomUUID().toString();
	        // 확장자 추출(ex : .png)
	        String extension = origin_name.substring(origin_name.lastIndexOf("."));
	        // uuid와 확장자 결합
	        String save_name = uuid + extension;
	        // 파일을 불러올 때 사용할 파일 경로
	        String save_path = postImgDir + save_name;
	
	        // 실제로 로컬에 uuid를 파일명으로 저장 (실제 물리적인 파일로 저장해준다)
	        // 스프링에서 제공하는 multipartFile을 자바의 파일 객체 File로 변환
	        try {
	        	imgFile.transferTo(new File(save_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
	        
	        // 데이터베이스에 이미지 정보를 저장 (insert한다, post_img에)
	        PostImgEntity newImgEntity = PostImgEntity.builder()
	        		.postId(post_id)
	                .originName(origin_name)
	                .saveName(save_name)
	                .savePath(save_path)
	                .build();
	        PostImgEntity imgEntity = imgRepository.save(newImgEntity);
	        
	        if (imgEntity != null) {
	        	imgIds.add(imgEntity.getImgId());
	        }
        }
        
		return imgIds;

    }
	
  //프로필 이미지 저장
    public int saveProfImg(String mem_id, MultipartFile img) {
    	if (img == null) {
            return 0;
    	}
	    if(img.getContentType().startsWith("image") == false){
         	log.warn("this file is not image type");
         	return 0;
	    }
	    
        String origin_name = img.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = origin_name.substring(origin_name.lastIndexOf("."));
        String save_name = uuid + extension;
        String save_path = profileImgDir + save_name;
	
        // 실제로 로컬에 uuid를 파일명으로 저장 (실제 물리적인 파일로 저장해준다)
        // 스프링에서 제공하는 multipartFile을 자바의 파일 객체 File로 변환
        try {
        	img.transferTo(new File(save_path));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
        
        // 데이터베이스에 이미지 정보를 저장 (insert한다, post_img에)
        MemberDto member = MemberDto.builder().mem_id(mem_id).profimg(save_path).build();
        int up_result =  memberDao.updateProfimg(member);
       
        return up_result;
}
    


}
