package com.study.springboot.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.Session;
import com.study.springboot.comment.CommentDao;
import com.study.springboot.comment.CommentDto;
import com.study.springboot.comment.SubComShowDto;
import com.study.springboot.comment.SubCommentDto;
import com.study.springboot.member.MemberDao;
import com.study.springboot.member.MemberDto;
import com.study.springboot.post.img.PostImgDto;
import com.study.springboot.post.img.PostImgEntity;
import com.study.springboot.post.img.PostImgRepository;

import jakarta.servlet.http.HttpSession;
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
	
 
  @GetMapping("/view/{post_idS}")
  public String toView(@PathVariable("post_idS") int post_id, Model model) throws IOException{
  	log.info("-----------PostController toView()-------------");
  	
  	PostDto post = postDao.selectOnePost(post_id);
  	List<PostImgEntity> imgs = new ArrayList<>();
  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(post_id, "n");
  	
  	Map<String,Integer> postTimeMap= postService.calPostTime(post_id); //포스팅 시간
	List<CommentDto> cList = commentdao.selectAllComment(post_id); // 댓글 목록
	List<SubComShowDto> sList = commentdao.findSubComByPostId(post_id); // 대댓글 목록
	
	//댓글 시간
	Map<Integer,Integer> mapCom = new HashMap<>(); // 각 댓글에 출력할 시간을 담을 맵
	for(CommentDto commentDto : cList) {
		int com_id = commentDto.getCom_id();
		int time = commentdao.findComTime(com_id);
		mapCom.put(com_id, time);
		// view에서 com_id로 각 시간을 찾기 위해
	}
	
	//대댓글 시간
	Map<Integer,Integer> mapSub = new HashMap<>(); // 각 대댓글에 출력할 시간을 담을 맵
	for(SubComShowDto subComment : sList) {
		int subcom_id = subComment.getSubcom_id();
		int time = commentdao.findSubComTime(subcom_id);
		mapSub.put(subcom_id, time);
		// view에서 com_id로 각 시간을 찾기 위해
	}
  	MemberDto postingUser = memberDao.selectOneMember(post.getMem_id());//포스팅 유저 정보
	
  	model.addAttribute("tMapCom", mapCom);
  	model.addAttribute("tMapSub", mapSub);
	model.addAttribute("sList", sList);
	model.addAttribute("cList", cList);
	
	model.addAttribute("postUser", postingUser);
	model.addAttribute("timeMap",postTimeMap);
  	model.addAttribute("imgs", imgs); 
  	model.addAttribute("post", post);
  	
  	return "view";
  }
  
	@GetMapping("/updatePostForm/{post_id}")
	public String  toUpdateForm(@PathVariable("post_id") String post_idS, Model model) {
		int post_id = Integer.parseInt(post_idS);
		PostDto post = postDao.selectOnePost(post_id);
		post.setContent(post.getContent().replace("<br>" ,"\r\n"));
		
	  	List<PostImgEntity> imgs = new ArrayList<>();
	  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(post_id, "n");
	  	
	  	model.addAttribute("imgCount", imgs.size());
	  	model.addAttribute("post", post);
	  	model.addAttribute("imgs", imgs);
		return "update_post";
	}
	
	@PostMapping("/deleteImg")
	public String deleteImg(@RequestBody PostImgDto img, Model model) {
		int del_result = postDao.deletePostImg(img.getImg_id());
		
		if(del_result>0) {
		List<PostImgEntity> imgs = new ArrayList<>();
	  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(img.getPost_id(), "n");
	  	
		model.addAttribute("imgCount", imgs.size());
	  	model.addAttribute("imgs", imgs);
		}
	  	return "/update_post :: #img_preview"; 
	}
	
	
	@PostMapping("/updatePost")
	@ResponseBody
	public int updatePost(PostDto post, Model model) {
		int up_result = postService.updatePostCont(post); 
		if(up_result>0) {
			return post.getPost_id();
		}
		return 0;
	}
	
	@GetMapping("/deletePost/{post_id}")
	@ResponseBody
	public int deletePost(@PathVariable("post_id") String post_idS) {
		int post_id = Integer.parseInt(post_idS);
		int post_del_result =  postDao.deletePost(post_id);
		if (post_del_result > 0) {
			int img_del_result = postDao.realDeletePostImg(post_id);
			return post_del_result + img_del_result;
		}
		return 0;
	}
	
	//nav > 최근 삭제한 컨텐츠
	@GetMapping("/restoreImgList/{mem_id}")
	public String toRestoreImgs(@PathVariable("mem_id") String mem_id, Model model) {
		//mem_id가 쓴 게시글 리스트
		List<PostDto> postList = postDao.selectAllMyPost(mem_id);
		List<PostImgEntity> delFirstImgs = new ArrayList<>();
		for (PostDto post : postList) {
			//게시글 하나당 삭제한 이미지 리스트
			List<PostImgEntity> myImgList  = new ArrayList<>();
			myImgList =  imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(post.getPost_id(), "y");
			if(!myImgList.isEmpty()) 
			delFirstImgs.add(myImgList.get(0));
		}
		//mem_id가 지운 사진들(포스트 별로)의 첫번째 사진 리스트
		//model.addAttribute("deleteCount", delFirstImgs.size());
		model.addAttribute("firstImgs", delFirstImgs);
		return "restore_list";
	}
	
	//이미지 복원 폼으로
	@GetMapping("/restoreImgForm/{post_idS}")
	public String toRestoreImgPage(@PathVariable("post_idS") String post_idS, Model model) {
		int post_id = Integer.parseInt(post_idS);
		PostDto post = postDao.selectOnePost(post_id);
	  	List<PostImgEntity> imgs = new ArrayList<>();
	  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(post_id, "y");
	  	model.addAttribute("post", post);
	  	model.addAttribute("imgCount", imgs.size());
	  	model.addAttribute("imgs",imgs);
		return "restore_img";
	}
	
	//이미지 복원
	@PostMapping("/restoreImg")
	public String restoreImgOne(@RequestBody PostImgDto img, HttpSession session, Model model) {
		log.info("restoreImg()---------------------------------------");
		int up_result = postDao.restorePostImg(img.getImg_id());
		MemberDto user = (MemberDto)session.getAttribute("user");
		log.info(img);
		if(up_result>0) {
		List<PostImgEntity> imgs = new ArrayList<>();
	  	imgs = imgRepo.findByPostIdAndDeleteYnOrderByInTimeDesc(img.getPost_id(), "y");
	  	model.addAttribute("imgCount", imgs.size());
	  	model.addAttribute("imgs", imgs);
		}
	  	return "/restore_img :: #img_preview"; 
	}
	
	//이미지 전체 복원
	@PostMapping("/restoreAllImg")
	@ResponseBody
	public int  restoreAllImg(PostDto post, Model model) {
		int up_result = postDao.restoreAllPostImg(post.getPost_id());
	  	return up_result; 
	}
	
}
    
    
