package com.padnini.authserviceapi.controllers.impl;

import com.padnini.authserviceapi.controllers.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest authenticateRequest) {
        return ResponseEntity.ok().body(AuthenticationResponse.builder().type("Bearer").token("token").build());
    }
}
