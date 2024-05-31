package com.green.Nowon.ssh;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties("spring.ssh.tunnel")
public class SSHTunnellingProperties {

	
	private String sshHost; //ec2주소
	private int sshPort;
	private String username;
	private String privateKey;
	//private int localPort; --random 적용
	
	private String rdsHost;
	private int rdsPort;
}
