package com.green.Nowon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.Nowon.domain.dao.PostMapper;
import com.green.Nowon.domain.dto.PostSaveDTO;
import com.green.Nowon.domain.dto.PostVO;
import com.green.Nowon.service.PostService;

import lombok.RequiredArgsConstructor;


@Service  //컨테이너에 보관 종료 할때까지 (싱글톤(객체하나로)로 관리)
//@Service :계산하고 정리하는 담당 (데이터베이스에 가져온걸 가공하고 정리하는 담당)
@RequiredArgsConstructor
public class PostServiceProcess implements PostService {

	//매퍼 -> 인터페이스로 만드세요!, 데이터베이스에 접근해주는것(DB의 posts테이블에)
	private final PostMapper mapper; 
	
	@Override
	public void listProcess(Model model) {
		//데이터베이스에서 Post데이터를 갖고와서 model 객체 보내라
		List<PostVO>result=mapper.findAll(); //모두 가져와서 각각 리스트에 넣어줘
		
		//퀵(Model 객체)을 통해 지정된 html파일로 보내
		model.addAttribute("list", result);
		model.addAttribute("txt", "모델에 저장된 데이터입니다"); //앞에는 네이밍(자유), 숫자형도 가능
		
	}

	@Override
	public void saveProcess(PostVO vo) {
		
		mapper.save(vo);
		
	}

}
