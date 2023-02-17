package com.study.springboot.post;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.study.springboot.member.MemberDto;
import com.study.springboot.post.img.PostImgDto;
import com.study.springboot.post.img.PostImgEntity;
import com.study.springboot.post.img.PostImgRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class PostController {
	private final PostService postService;	
	private final PostDao postDao;
	private final PostImgRepository imgRepo;
	
//	@PostConstruct
//	public void init() throws MalformedURLException {
//		log.info("*************초기화*************");
//		PostImgEntity imgFile = imgRepo.findById(1L).orElse(null);
//        UrlResource urlResource;
//		urlResource = new UrlResource("file:" + imgFile.getSavePath());
//        log.info(urlResource);
//	}

	@GetMapping("/newPost")
	public String newPostForm() {
		//log.info("이미지 목록********" + imgRepo.findAll());
		return "new_post";
	}
	
	
    @PostMapping("/upload")
    public String insertPost(PostDto post, @RequestParam("imgs") List<MultipartFile> imgs, Model model) {
    	List<Long> newImgIds = new ArrayList<Long>();
    	
    	//post테이블에 insert하고 해당 post_id를 바로 가져온다.(MyBatis-postDao.xml)
        post.setContent(post.getContent().replace("\r\n","<br>")); //줄 개행
    	int insert_res = postDao.insertPost(post);
        int post_id  = post.getPost_id();
        log.info("포스트 아이디:  " + post_id);
        
        //post테이블에 insert 성공시에 post_img 테이블에 insert시도한다.(JPA-postImgRepository)
        if (insert_res != 0) {
	        	newImgIds = postService.saveImgFile(post_id, imgs);
        }
        
        if(!newImgIds.isEmpty()) {
        	 PostDto newPost = postDao.selectOnePost(post_id);
        	 //model.addAttribute("imgList", newImgIds);
        	 model.addAttribute("post", newPost);
        	 
        	 //로그인한 사람 게시물 리스트 뽑기.
//        	 model.addAttribute()
        	 return "feed";
        } else {
        	model.addAttribute("msg", "게시물 등록에 실패하였습니다!");
        	return "new_post";
        }
    }
	
    //이미지 출력 (img_id로)
    @GetMapping("/images/{imgId}")
    @ResponseBody
    public Resource viewImageOne(@PathVariable("imgId") Long id, Model model) throws IOException{
        PostImgEntity imgFile = imgRepo.findById(id).orElse(null);
        UrlResource urlResource = new UrlResource("file:" + imgFile.getSavePath());
        return urlResource;
    }
    
    //나의 feed 이미지 리스트 (post_id)로
//    @GetMapping("/feed/{login_id}")
//    public String toMyFeed(@PathVariable("login_id") String login_id, Model model) throws IOException{
//    	List<PostDto> myPostList = postDao.selectAllMyPost(login_id);
//    	log.info("-----------PostController toMyFeed()-------------");
//    	
//    	Map<String, List<Integer>> myImgMap = new HashMap<>();
//		
//    	for (PostDto post : myPostList) {
//    		//포스트 한개의 이미지Dto들
//    		List<PostImgDto> imgs= postDao.selectAllMyImg(post.getPost_id());
//    		//포스트 한개의 이미지Id들
//    		List<Integer> imgIds = new ArrayList<>();
//    		for(PostImgDto img : imgs) {
//    			imgIds.add(img.getImg_id());
//    		}
//    		myImgMap.put(String.valueOf(post.getPost_id()), imgIds);
//    		imgIds = null;
//    	}
//    	log.info(myImgMap);
//    	
//    	model.addAttribute("myImgMap", myImgMap);
//    	model.addAttribute("myPostList", myPostList);
//    	return "feed";
//    }
    
  @GetMapping("/myHome/{login_id}")
  public String toMyFeed(@PathVariable("login_id") String login_id, Model model) throws IOException{
  	//포스트 리스트
	List<PostDto> myPostList = postDao.selectAllMyPost(login_id);
  	log.info("-----------PostController toMyFeed()-------------");
  	
  	//포스트한 이미지 사진
  	List<PostImgDto> firstImgIds = new ArrayList<>();
  	for (PostDto post : myPostList) {
  		List<PostImgDto> myImgList  = postDao.selectAllImgByPost(post.getPost_id());
  		firstImgIds.add(myImgList.get(0));
  	}
  	
  	//내가 쓴 게시물 수
  	int myPostCount = postDao.countMyPost(login_id);
  	
  	model.addAttribute("myPostCount", myPostCount);
  	model.addAttribute("firstImgs", firstImgIds);
  	model.addAttribute("myPostList", myPostList);
  	
  	return "my_home";
  }
  
  
  @GetMapping("/view/{post_id}")
  public String toView(@PathVariable("post_id") String post_idS, Model model) throws IOException{
	int post_id = Integer.parseInt(post_idS);
  	PostDto post = postDao.selectOnePost(post_id);
  	log.info("-----------PostController toView()-------------");
  	
  	List<PostImgEntity> imgs = new ArrayList<>();
  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(post_id, "n");
  	
	//포스팅 시간.
	Map<String,Integer> postTimeMap= postService.calPostTime(post_id);
	log.info(postTimeMap);
	model.addAttribute("timeMap",postTimeMap);
  	model.addAttribute("imgs", imgs); 
  	model.addAttribute("post", post);
  	
  	return "view";
  }
        
}
    
    
