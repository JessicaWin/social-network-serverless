package com.jessica.social.network.serverless.service;

import com.jessica.social.network.serverless.bo.UserBo;

public interface UserService {
    void createUser(UserBo userBo);
    void deleteById(String id);
    void updateUser(UserBo userBo);
    UserBo getById(String id);
}
