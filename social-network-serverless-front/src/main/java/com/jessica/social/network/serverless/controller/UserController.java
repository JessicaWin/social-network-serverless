package com.jessica.social.network.serverless.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(path = "/{id}")
    public String getUserById(@PathVariable String id) {
        return "user: " + id;
    }
}
