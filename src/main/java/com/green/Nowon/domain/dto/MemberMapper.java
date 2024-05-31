package com.green.Nowon.domain.dto;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.green.Nowon.domain.dao.MemberDTO;






@Mapper
public interface MemberMapper {
	
	/*
	@Insert("insert into member(email, pass, name) values(#{email},#{pass},#{name})")
	int save(@Param("email")String email, @Param("pass")String pass, @Param("name")String name);
	*/
	
	//@Insert("insert into member(email, pass, name, nick_Name) values(#{email},#{pass},#{name},#{nickName})")
	int save(MemberDTO dto);
	
	
	//@Select("select * from Member where no> 0")
	List<MemberDTO>findAll();
}
