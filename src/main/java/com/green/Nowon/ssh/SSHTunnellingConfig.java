package com.green.Nowon.ssh;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 이 클래스 내부에서 로그를 적용한다는 것 
@Configuration
public class SSHTunnellingConfig {
	
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
		
		int localPort=sshsession.setPortForwardingL(
				sshTunnellingProperties.getLocalPort(), 
				sshTunnellingProperties.getRdsHost(), 
				sshTunnellingProperties.getRdsPort());
		
		
		hikariConfig.setJdbcUrl(dataSourceProperties.getUrl().replace("[LOCAL_PORT]", String.valueOf(localPort)));
		hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
		hikariConfig.setUsername(dataSourceProperties.getUsername());
		hikariConfig.setPassword(dataSourceProperties.getPassword());
		
		hikariConfig.setMaximumPoolSize(10);
		System.out.println("max: " +hikariConfig.getMaximumPoolSize() );
		
		return new HikariDataSource(hikariConfig);
	}
	
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		
		SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		
		return factoryBean.getObject();
		
	}
	
	@Bean
	SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception{
		
		return new SqlSessionTemplate(sqlSessionFactory(dataSource));
	}
}
