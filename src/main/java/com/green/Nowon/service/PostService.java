package com.green.Nowon.service;

import org.springframework.ui.Model;

import com.green.Nowon.domain.dto.PostSaveDTO;
import com.green.Nowon.domain.dto.PostVO;

public interface PostService {

	void listProcess(Model model); //추상메서드

	void saveProcess(PostVO vo); //추상메서드

	

}
