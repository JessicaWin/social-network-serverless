package com.jessica.social.network.serverless.controller.login;

import com.jessica.jwt.JwtClaim;
import com.jessica.jwt.JwtKeyHelper;
import com.jessica.jwt.JwtUtils;
import com.jessica.social.network.serverless.bo.UserBo;
import com.jessica.social.network.serverless.service.UserService;
import com.jessica.social.network.serverless.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Api("api to login")
@Validated
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtKeyHelper jwtKeyHelper;

    @PostMapping("/signin")
    @ApiOperation("use user name and password to login the system")
    public String login(@RequestParam String userName, @RequestParam String password, HttpServletResponse response) {
        UserBo user = userService.getUser(userName);
        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
            JwtClaim jwtClaim = JwtClaim.builder().userName(userName).build();
            try {
                String jwtToken = JwtUtils.getTokenStr(jwtClaim, 3600000, jwtKeyHelper.getPrivateKey());
                response.addHeader("Authorization", jwtToken);
                return jwtToken;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }



    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public UserVo createUser(@Valid @RequestBody UserVo userVo, HttpServletResponse response) {
        UserBo userBo = userVo.toBo();
        this.userService.createUser(userBo);
        return UserVo.fromBo(userBo);
    }
}
