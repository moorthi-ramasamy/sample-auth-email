package com.bcbsm.interview.userauth.api;

import com.bcbsm.interview.userauth.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Moorthi Ramasamy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmailApiTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService emailService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void sendEmailWithAttachmentTest() throws Exception {
        Mockito.when(emailService.sendEmail(Mockito.any())).thenReturn(true);
        MockMultipartFile file = new MockMultipartFile("fileAttachment","originalNameTest", MediaType.MULTIPART_FORM_DATA_VALUE ,"Attachment File content".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile
                ("request", "", "application/json",
                        "{\"toEmailAddress\": \"moorthi123@gmail.com\"}".getBytes());
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.multipart("/email/sendEmailWithAttachment")
                        .file(file)
                        .file(jsonFile)
                        .header("Authorization", "Basic YnJ4X2RzczpOb25Qcm9kIUAjMTIz");
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAllSentEmailsTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/email/getAllSentEmails")
                .header("Authorization", "Basic YnJ4X2RzczpOb25Qcm9kIUAjMTIz");
        mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

}
