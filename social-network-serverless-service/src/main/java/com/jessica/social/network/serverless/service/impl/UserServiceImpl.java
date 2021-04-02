package com.jessica.social.network.serverless.service.impl;

import com.jessica.social.network.serverless.bo.UserBo;
import com.jessica.social.network.serverless.service.UserService;
import com.jessica.user.dao.UserDao;
import com.jessica.user.dto.UserItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public void createUser(UserBo userBo) {
        if (userBo == null) {
            return;
        }
        this.userDao.save(userBo.toItem());
    }

    @Override
    public void deleteUser(String userName) {
        if (userName == null) {
            return;
        }
        this.userDao.delete(UserItem.builder().userName(userName).build());
    }

    @Override
    public void updateUser(UserBo userBo) {
        if (userBo == null) {
            return;
        }
        this.userDao.save(userBo.toItem());
    }

    @Override
    public UserBo getUser(String userName) {
        if (userName == null) {
            return null;
        }
        return UserBo.fromItem(this.userDao.load(UserItem.builder().userName(userName).build()));
    }
}
