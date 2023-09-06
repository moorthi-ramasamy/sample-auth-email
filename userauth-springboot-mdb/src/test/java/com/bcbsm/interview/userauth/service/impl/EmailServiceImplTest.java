package com.bcbsm.interview.userauth.service.impl;

import com.bcbsm.interview.userauth.entity.Email;
import com.bcbsm.interview.userauth.model.request.EmailRequest;
import com.bcbsm.interview.userauth.repository.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
/**
 * @author Moorthi Ramasamy
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {
    @Mock
    EmailRepository emailRepository;
    @Mock
    JavaMailSender javaMailSender;
    @Mock
    private MimeMessage message;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Before
    public void setup() {
    }

    @Test
    public void testSendEmail(){
        EmailRequest emailRequest = new EmailRequest();
        MockMultipartFile file = new MockMultipartFile(
                "fileAttachment",
                "originalNameTest",
                MediaType.MULTIPART_FORM_DATA_VALUE ,
                "Sample Attachment File content".getBytes()
        );
        emailRequest.setToEmailAddress("send@test.com");
        emailRequest.setFromEmailAddress("received@test.com");
        emailRequest.setFileAttachment(file);
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(message);
        boolean isSuccess = emailService.sendEmail(emailRequest);
        assertTrue(isSuccess);
    }

    @Test
    public void testGetAllSentEmails(){
        Email email = new Email();
        email.setFromEmail("test@test.com");
        List<Email> emailList = Arrays.asList(email);
        when(emailRepository.findAll()).thenReturn(emailList);
        List<Email> emails = emailService.getAllSentEmails();
        assertNotNull(emails);
        assertTrue(emails.size() > 0);
    }

}
