package com.mb.base.idp.config.oauth.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {
	
	private String access_token;
	private String token_type;
	private int expires_in;
	private String scope;
	private String info_adicional;
	private String jti;
	
}
