package com.crm.gatewayserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gatewayserver.model.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user
    ) {

        //creating auth response object
        AuthResponse authResponse = new AuthResponse();

        //setting email to authresponse
        authResponse.setUserId(user.getEmail());

        //setting toke to auth response
//        authResponse.setAccessToken(client.getAccessToken().getTokenValue());
//
//        authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());
//
//        authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

        List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
            return grantedAuthority.getAuthority();
        }).collect(Collectors.toList());


        authResponse.setAuthorities(authorities);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
