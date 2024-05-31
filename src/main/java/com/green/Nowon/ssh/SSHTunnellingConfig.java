package com.green.Nowon.ssh;

import java.util.Random;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 이 클래스 내부에서 로그를 적용한다는 것 
@Configuration
@RequiredArgsConstructor //final 필드를 매개 변수로 반영된 생성자
public class SSHTunnellingConfig {
	
	
	 private final ApplicationContext application;//생성자DI
	 //스프링 애플리케이션을 시작할때 ApplicationContext 객체는 설정 파일을 읽고, 애플리케이션의 중앙 인터페이스로
	 //애플리케이션의 모든 구성 요소(빈)를 관리하고 애플리케이션의 설정 정보를 제공하는 컨테이너
	
	
	@Bean
	DataSource dataSource(SSHTunnellingProperties sshTunnellingProperties,
			DataSourceProperties dataSourceProperties) throws JSchException {
		
		JSch jsch=new JSch();//
		jsch.addIdentity(sshTunnellingProperties.getPrivateKey());
		
		
		Session sshsession=jsch.getSession(
				sshTunnellingProperties.getUsername(),
				sshTunnellingProperties.getSshHost(), 
				sshTunnellingProperties.getSshPort());
		
		sshsession.setConfig("StrictHostKeyChecking", "no");
		//서버 측에 등록된 HOST가 아니어도 엄격하게 확인하지 않도록 한것
		sshsession.connect();
		log.info("ssh-tunnel(EC2) 접속완료!");
		
		
		//정보수정 
		HikariConfig hikariConfig=new HikariConfig();
		
		Random ran = new Random();
		int lport=ran.nextInt(100)+3301;
		
		
		int localPort=sshsession.setPortForwardingL(
				lport, 
				sshTunnellingProperties.getRdsHost(), 
				sshTunnellingProperties.getRdsPort());
		
		
		hikariConfig.setJdbcUrl(dataSourceProperties.getUrl().replace("[LOCAL_PORT]", String.valueOf(localPort)));
		hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
		hikariConfig.setUsername(dataSourceProperties.getUsername());
		hikariConfig.setPassword(dataSourceProperties.getPassword());
		
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMaxLifetime(150000); // db-wait_time=180초
		//maxlifetime wait_timeout타입보다  약간 적게 2분 30
		
		System.out.println("max: " +hikariConfig.getMaximumPoolSize() );
		
		return new HikariDataSource(hikariConfig);
	}
	
	
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		
		
		SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
		//1.DATASOURCE
		factoryBean.setDataSource(dataSource);
		
		//2.CONFIG
		factoryBean.setConfiguration(mybatisConfiguration());
		//3.mapper.xml-location (...은 매개변수에서만 사용가능 )
		
		String locationPattern="classpath*:sqlmap/**/*-mapper.xml";
		Resource[] resource=application.getResources(locationPattern);//...대신 여러개 집합인 배열가능
		factoryBean.setMapperLocations(resource);
		
		
		
		return factoryBean.getObject();
		
	}
	@Bean
	@ConfigurationProperties(prefix= "mybatis.configuration")
	org.apache.ibatis.session.Configuration mybatisConfiguration() {
		
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception{
		
		return new SqlSessionTemplate(sqlSessionFactory(dataSource));
	}
}
