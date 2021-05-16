package com.mb.base.idp.controller;

import org.springframework.http.ResponseEntity;
import com.mb.base.idp.model.dto.PassClientLoginRequest;
import com.mb.base.idp.model.dto.PassClientLoginResponse;
import com.mb.base.idp.model.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	@PostMapping("pass-client-login")
	public ResponseEntity<PassClientLoginResponse> passwordAndClientAuthentication(
			 @RequestBody PassClientLoginRequest loginRequest) {
		return authService.passwordAndClientAuthenticate(loginRequest);
	}

}
