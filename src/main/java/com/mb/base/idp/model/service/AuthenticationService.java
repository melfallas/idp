package com.mb.base.idp.model.service;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import com.mb.base.idp.config.oauth.jwt.AccessToken;
import com.mb.base.idp.config.oauth.jwt.TokenGenerator;
import com.mb.base.idp.model.dto.PassClientLoginRequest;
import com.mb.base.idp.model.dto.PassClientLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthenticationService {
	
	@Autowired
	TokenGenerator tokenGenerator;
	
	private String tokenEndpoint = "http://localhost:9090/oauth/token";
	
	public ResponseEntity<PassClientLoginResponse> passwordAndClientAuthenticate(PassClientLoginRequest loginRequest) {
		PassClientLoginResponse loginResponse = new PassClientLoginResponse();
		ResponseEntity<AccessToken> tokenResponse = tokenGenerator
				.getTokenInfo(tokenEndpoint, loginRequest.getClient(), loginRequest.getSecret());
		AccessToken tokenInfo = tokenResponse.getBody();
		loginResponse.setResult("success");
		loginResponse.setAccessToken(tokenInfo.getAccess_token());
		return new ResponseEntity<PassClientLoginResponse>(loginResponse, tokenResponse.getStatusCode());
	}

}
