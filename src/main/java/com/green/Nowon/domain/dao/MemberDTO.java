package com.green.Nowon.domain.dao;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberDTO {

	
	private long no;
	private String email;
	private String pass;
	private String name;
	private String nickName;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
}
