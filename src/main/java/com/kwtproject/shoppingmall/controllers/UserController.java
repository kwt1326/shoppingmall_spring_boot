package com.kwtproject.shoppingmall.controllers;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.dto.user.RequestSignIn;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;
import com.kwtproject.shoppingmall.dto.user.ResponseSignUp;
import com.kwtproject.shoppingmall.service.UserService;
import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import com.kwtproject.shoppingmall.utils.authentication.object.AuthenticationObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private ResponseEntity<?> login(@RequestBody RequestSignIn requestSignIn) throws Exception {
        try {
            authenticationManager.authenticate(
                    new AuthenticationObject(requestSignIn.getName(), requestSignIn.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect name and password", e);
        }

        String jwt = jwtUtils.generateToken(userService.loadUserByUsername(requestSignIn.getName()));

        return new ResponseEntity<>(new ResponseSignUp(jwt), HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private ResponseEntity<?> createUser(@RequestBody RequestSignUp requestSignUp) throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .name(requestSignUp.getName())
                .password(requestSignUp.getPassword())
                .username(requestSignUp.getUsername())
                .email(requestSignUp.getEmail()).build();

        userService.signUp(userEntity);

        String jwt = jwtUtils.generateToken(userService.loadUserByUsername(requestSignUp.getUsername()));

        return new ResponseEntity<>(new ResponseSignUp(jwt), HttpStatus.OK);
    }
}
