package com.bcbsm.interview.userauth.api;

import com.bcbsm.interview.userauth.constants.ApiConstants;
import com.bcbsm.interview.userauth.constants.AppConstants;
import com.bcbsm.interview.userauth.entity.User;
import com.bcbsm.interview.userauth.exception.ApplicationRuntimeException;
import com.bcbsm.interview.userauth.model.request.LoginRequest;
import com.bcbsm.interview.userauth.service.UserService;
import com.bcbsm.interview.userauth.util.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController to handle User Logins
 * @author Moorthi Ramasamy
 */
@RestController
@RequestMapping(value= ApiConstants.USER_AUTH_PATH)
@Slf4j
public class UserApi {

    @Autowired
    UserService userService;
    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers(){
        final String method = "UserApi.getUsers ";
        log.info(method + AppConstants.ENTER);
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    public ResponseEntity<User> authenticate(@RequestBody LoginRequest loginRequest){
        final String method = "UserApi.authenticate ";
        log.info(method + AppConstants.ENTER);
        ValidationUtils.validate(loginRequest);
        try {
            User user = userService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error(method + AppConstants.ERROR_OCCURED, e);
            throw new ApplicationRuntimeException("Error occurred on authenticating user");
        }
    }
}
