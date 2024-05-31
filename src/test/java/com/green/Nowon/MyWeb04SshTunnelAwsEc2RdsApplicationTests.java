package com.green.Nowon;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.green.Nowon.domain.dao.MemberDTO;
import com.green.Nowon.domain.dto.MemberMapper;



@SpringBootTest
class MyWeb04SshTunnelAwsEc2RdsApplicationTests {

	
	//@Autowired
	MemberMapper mapper;
	

	@Test
		void 회원_조회하기() {
			List<MemberDTO>result=mapper.findAll();
			
			for(MemberDTO dto:result) {
				System.out.println(dto);
			}
		}
	

	//@Test
	void contextLoads() {
		
		MemberDTO dto =new MemberDTO();
		dto.setEmail("test02@test.com");
		dto.setPass("1234");
		dto.setName("테스트01");
		dto.setNickName("짜짜");
		
		int result=mapper.save(dto);
		System.out.println(">>>>result" + result);
	}

}
