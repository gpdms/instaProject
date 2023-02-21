package com.study.springboot.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.study.springboot.comment.CommentDao;
import com.study.springboot.comment.CommentDto;
import com.study.springboot.member.MemberDao;
import com.study.springboot.post.img.PostImgEntity;
import com.study.springboot.post.img.PostImgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class PostController {
	private final PostService postService;	
	private final ImgService imgService;
	private final PostDao postDao;
	private final PostImgRepository imgRepo;
	private final MemberDao memberDao;
	private final CommentDao commentdao;
	
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
    @Transactional
    public String insertPost(PostDto post, @RequestParam("imgs") List<MultipartFile> imgs, Model model) {
    	//post테이블에 insert(postService -> postDao.xml(Mybatis))
    	int newPost_id = postService.insertPost(post);
    	
    	//post테이블에 insert 성공시에 post_img 테이블에 insert(imgService -> postImgRepository(JPA))
    	List<Long> newImgIds = new ArrayList<Long>();
    	if(newPost_id != 0)
    	newImgIds = imgService.savePostImg(newPost_id, imgs);
    	
        //post_img테이블에 insert 성공시에 post 반환
    	PostDto newPost = null;
    	if(!newImgIds.isEmpty()) {
       	 newPost = postDao.selectOnePost(newPost_id);
        }
        
        if(newPost != null) {
        	 model.addAttribute("imgList", newImgIds);
        	 model.addAttribute("post", newPost);
        	 return "feed";
        } else {
        	model.addAttribute("msg", "게시물 등록에 실패하였습니다!");
        	return "new_post";
        }
    }
	
    //포스트 이미지 출력 (img_id로)
    @GetMapping("/imgs/post/{imgId}")
    @ResponseBody
    public Resource viewImageOne(@PathVariable("imgId") Long id, Model model) throws IOException{
        PostImgEntity imgFile = imgRepo.findById(id).orElse(null);
        UrlResource urlResource = new UrlResource("file:" + imgFile.getSavePath());
        return urlResource;
    }
    
    //프로필 이미지 출력 (mem_id로)
    @GetMapping("/imgs/profile/{mem_id}")
    @ResponseBody
    public String viewProfileImg(@PathVariable("mem_id") String mem_id, Model model) throws IOException{
    	String pfImgPath = memberDao.selectOneMember(mem_id).getProfimg();
    	log.info(pfImgPath);
        return pfImgPath;
    }
    
  
  @GetMapping("/view/{post_id}")
  public String toView(@PathVariable("post_id") int post_id, Model model) throws IOException{
  	log.info("-----------PostController toView()-------------");
  	
  	PostDto post = postDao.selectOnePost(post_id);
  	List<PostImgEntity> imgs = new ArrayList<>();
  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(post_id, "n");
	
	
  	Map<String,Integer> postTimeMap= postService.calPostTime(post_id); //포스팅 시간
	List<CommentDto> cList = commentdao.selectAllComment(post_id);
	model.addAttribute("cList", cList);
	model.addAttribute("timeMap",postTimeMap);
  	model.addAttribute("imgs", imgs); 
  	model.addAttribute("post", post);
  	
  	return "view";
  }
        
}
    
    
