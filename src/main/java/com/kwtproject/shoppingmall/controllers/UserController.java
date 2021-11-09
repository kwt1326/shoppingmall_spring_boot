package com.kwtproject.shoppingmall.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;
import com.kwtproject.shoppingmall.vo.ResponseMessageVo;
import com.kwtproject.shoppingmall.vo.user.ResponseSignUp;
import com.kwtproject.shoppingmall.service.UserService;
import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import com.kwtproject.shoppingmall.vo.user.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;

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
    private ResponseEntity<?> createUser(@RequestBody RequestSignUp requestSignUp, ServletRequest request) {
        try {
            userService.signUp(requestSignUp);

            jwtUtils.generateToken(userService.loadUserByUsername(requestSignUp.getUsername()));

            return new ResponseEntity<>(new ResponseSignUp("Success signup"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "auth/logging", method = RequestMethod.GET)
    private ResponseEntity<String> checkLogging(WebRequest request) {
        try {
            Boolean result = userService.checkLogging(request);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseObj = mapper.createObjectNode();
            responseObj.put("result", result);

            String responseJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObj);

            return new ResponseEntity(responseJson, result ? HttpStatus.OK : HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "auth/info", method = RequestMethod.GET)
    private ResponseEntity<?> getUserInfo(WebRequest request) {
        try {
            UserInfoVo infoVo = userService.getUserInfo(request);

            if (infoVo != null) {
                return new ResponseEntity<>(infoVo, HttpStatus.OK);
            }
            ResponseMessageVo errorVo = new ResponseMessageVo("logged info not found", 403);
            return new ResponseEntity<>(errorVo, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
