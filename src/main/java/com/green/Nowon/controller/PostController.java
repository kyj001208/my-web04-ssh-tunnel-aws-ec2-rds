package com.green.Nowon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.Nowon.domain.dto.PostSaveDTO;
import com.green.Nowon.domain.dto.PostVO;
import com.green.Nowon.service.PostService;
import com.green.Nowon.service.impl.PostServiceProcess;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor  // 이아이가 있어야 final 필드를 초기화
public class PostController {
	
	// 생성자 DI =IOC개념 
	// contoller는 sevice한테 지시함
    private final PostService sevice; //interface <- class
    
    @GetMapping("/posts")
    public String list(Model model) {
    	
    	sevice.listProcess(model);  //내가 처리하기 귀찮기에 model 객체를 통해 너가 list 처리해
    	
    	//데이터베이스에서 데이터를 갖고와서 페이지에 데이터를 보내는것 => Model 객체
    	return "posts/list"; // post 페이지의 이름
    }
    
    @GetMapping("/posts/new") //페이지만 이동
    public String writePage() {
        return "posts/write";   //write 페이지의 이름
    }
    
    //request 메서드 다르면 url이 같아도 구분된다
    @PostMapping("/posts")
    public String wirte(PostVO vo) {//setter메서드가 있는 객체로 자동 매핑됨
    	//System.out.println(dto);
    	
    	sevice.saveProcess(vo); //겟매핑된 메서드를 호출시에는 url을 호출해야하는데
    	//내부에서 다시 요청해야하기에 redirect: (다시 호출해줘)라는 뜻
        return "redirect:/posts";   
    }
    
    
    
    
    ///////////////////////////////회원가입 페이지///////////////////////////
    @GetMapping("/login")
    public String signUpPage() {
    	
    	
    	return "views/a/b/login"; // 회원가입 페이지의 이름
    }
    
}