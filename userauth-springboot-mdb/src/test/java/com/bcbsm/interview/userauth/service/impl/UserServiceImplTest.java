package com.bcbsm.interview.userauth.service.impl;

import com.bcbsm.interview.userauth.entity.User;
import com.bcbsm.interview.userauth.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.anyString;
/**
 * @author Moorthi Ramasamy
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup() {
    }

    private static List<User>  getUserList() {
        User user = new User();
        user.setFirstName("Moorthi");
        user.setLastName("Ramasamy");
        List<User> users = Arrays.asList(user);
        return users;
    }

    @Test
    public void testAuthenticate(){
        Mockito.when(userRepository.findByUserIdAndPassword(anyString(), anyString())).thenReturn(getUserList().get(0));
        User user = userService.authenticate(anyString(), anyString());
        assertNotNull(user);
        assertEquals("Ramasamy", user.getLastName());
    }

    @Test
    public void testGetUsers(){
        Mockito.when(userRepository.findAll()).thenReturn(getUserList());
        List<User> users = userService.getUsers();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }
}
