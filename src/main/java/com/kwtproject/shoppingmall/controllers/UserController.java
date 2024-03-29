package com.kwtproject.shoppingmall.controllers;

import com.kwtproject.shoppingmall.dto.user.RequestSignIn;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;
import com.kwtproject.shoppingmall.dto.user.ResponseSignUp;
import com.kwtproject.shoppingmall.service.UserService;
import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

//    // REDIS 를 사용하여 구현 필요, 추후 예정 ( 현재 로그아웃은 클라이언트 단에서 쿠키 제거하는 것으로 )
//    @RequestMapping(value = "auth/logout", method = RequestMethod.POST)
//    private ResponseEntity<?> logout() throws Exception {
//        return new ResponseEntity<>("Successful Logout", HttpStatus.OK);
//    }

    @RequestMapping(value = "auth/create", method = RequestMethod.POST)
    private ResponseEntity<?> createUser(@RequestBody RequestSignUp requestSignUp) throws Exception {
        try {
            userService.signUp(requestSignUp);

            String jwt = jwtUtils.generateToken(userService.loadUserByUsername(requestSignUp.getUsername()));

            return new ResponseEntity<>(new ResponseSignUp(jwt), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
