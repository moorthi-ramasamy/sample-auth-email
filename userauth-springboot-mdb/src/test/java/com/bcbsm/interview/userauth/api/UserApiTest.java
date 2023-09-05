package com.bcbsm.interview.userauth.api;

import com.bcbsm.interview.userauth.model.request.LoginRequest;
import com.bcbsm.interview.userauth.service.UserService;
import com.bcbsm.interview.userauth.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Moorthi Ramasamy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void authenticateTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("test");
        loginRequest.setPassword("test");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/userauth/authenticate")
                .header("Authorization", "Basic YnJ4X2RzczpOb25Qcm9kIUAjMTIz")
                .content(JsonUtil.objectToJson(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void getUsersTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/userauth/getUsers")
                .header("Authorization", "Basic YnJ4X2RzczpOb25Qcm9kIUAjMTIz");
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }
}
