package com.bcbsm.interview.userauth.service.impl;

import com.bcbsm.interview.userauth.constants.AppConstants;
import com.bcbsm.interview.userauth.entity.User;
import com.bcbsm.interview.userauth.repository.UserRepository;
import com.bcbsm.interview.userauth.service.UserService;
import com.bcbsm.interview.userauth.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Moorthi Ramasamy
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        final String method = "UserServiceImpl.getUsers ";
        log.info(method + AppConstants.ENTER);
        return userRepository.findAll();
    }

    @Override
    public User authenticate(String userName, String password) {
        final String method = "UserServiceImpl.authenticate ";
        log.info(method + AppConstants.ENTER);
        return userRepository.findByUserIdAndPassword(userName, SecurityUtil.decodeText(password));
    }
}
