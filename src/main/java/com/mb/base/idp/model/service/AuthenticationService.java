package com.mb.base.idp.model.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mb.base.idp.config.oauth.jwt.AccessToken;
import com.mb.base.idp.config.oauth.jwt.TokenGenerator;
import com.mb.base.idp.model.dto.PassClientLoginRequest;
import com.mb.base.idp.model.dto.PassClientLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthenticationService {

	@Autowired
	TokenGenerator tokenGenerator;
	
	@Value("${atp.server.host}")
	private String oauthServer;
	
	@Value("${atp.server.token-path}")
	private String tokenPath;
	
	private static final String FAIL_RESULT = "fail";
	private static final String SUCCESS_RESULT = "success";
	
	public ResponseEntity<PassClientLoginResponse> passwordAndClientAuthenticate(PassClientLoginRequest loginRequest) {
		PassClientLoginResponse loginResponse = new PassClientLoginResponse();
		HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		if(validCredentials(loginRequest.getUserName(), loginRequest.getPassword())) {
			ResponseEntity<AccessToken> tokenResponse = tokenGenerator
					.getTokenInfo(oauthServer + tokenPath, loginRequest.getClient(), loginRequest.getSecret());
			AccessToken tokenInfo = tokenResponse.getBody();
			responseStatus = HttpStatus.OK;
			loginResponse.setResult(SUCCESS_RESULT);
			loginResponse.setAccessToken(tokenInfo.getAccess_token());
		} else {
			loginResponse.setResult(FAIL_RESULT);
			loginResponse.setAccessToken(null);
			responseStatus = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<PassClientLoginResponse>(loginResponse, responseStatus);
	}
	
	public boolean validCredentials(String pUsername, String pPassword) {
		return pUsername.equals("mfallas") && pPassword.equals("mfallas1");
	}

}
