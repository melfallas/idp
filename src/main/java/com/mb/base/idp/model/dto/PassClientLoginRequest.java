package com.mb.base.idp.model.dto;

import lombok.Data;

@Data
public class PassClientLoginRequest {
	
	private String userName;
	private String password;
	private String client;
	private String secret;
	
}
