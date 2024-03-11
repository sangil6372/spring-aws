package com.sangil.springaws.authentication.oauth.controller;


import com.sangil.springaws.authentication.oauth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value ="/login/oauth2", produces="application/json")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@PathVariable String registrationId, @RequestParam String code){
        loginService.socialLogin(registrationId, code);
    }

}
