package com.green.Nowon.domain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.Nowon.domain.dto.PostSaveDTO;
import com.green.Nowon.domain.dto.PostVO;

@Mapper //쿼리를 실행하는 객체-> 객체가 없기에 XML
public interface PostMapper {

	List<PostVO> findAll();

	void save(PostVO vo); //mapper 또한 오버라이드 해줘야하지만 xml에서 해줘야함

}
