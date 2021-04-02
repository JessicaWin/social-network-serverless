package com.jessica.social.network.serverless.service;

import com.jessica.social.network.serverless.bo.UserBo;

public interface UserService {
    /**
     *
     * @param userBo
     */
    void createUser(UserBo userBo);

    /**
     *
     * @param userName
     */
    void deleteUser(String userName);

    /**
     *
     * @param userBo
     */
    void updateUser(UserBo userBo);

    /**
     *
     * @param userName
     * @return
     */
    UserBo getUser(String userName);
}
