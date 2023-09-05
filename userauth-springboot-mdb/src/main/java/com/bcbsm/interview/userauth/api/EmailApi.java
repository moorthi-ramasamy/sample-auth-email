package com.bcbsm.interview.userauth.api;

import com.bcbsm.interview.userauth.constants.ApiConstants;
import com.bcbsm.interview.userauth.constants.AppConstants;
import com.bcbsm.interview.userauth.entity.Email;
import com.bcbsm.interview.userauth.exception.ApplicationRuntimeException;
import com.bcbsm.interview.userauth.model.request.EmailRequest;
import com.bcbsm.interview.userauth.service.EmailService;
import com.bcbsm.interview.userauth.util.ValidationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Moorthi Ramasamy
 */
@RestController
@RequestMapping(ApiConstants.EMAIL_PATH)
@Slf4j
public class EmailApi {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/sendEmailWithAttachment")
    public ResponseEntity<String> sendEmailWithAttachment(@RequestPart String request, @RequestPart("fileAttachment") MultipartFile fileAttachment) {
        final String method = "[EmailApi.sendEmailWithAttachment] ";
        log.info(method + "Enter : request " + request + " FileAttachment " + fileAttachment);
        ValidationUtils.validate(request);
        ResponseEntity<String> response = null;
        EmailRequest emailAttachmentRequest = new EmailRequest();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            emailAttachmentRequest = objectMapper.readValue(request, EmailRequest.class);
            log.info(method + "emailAttachmentRequest " + emailAttachmentRequest);
            if(null != fileAttachment ) {
                emailAttachmentRequest.setFileAttachment(fileAttachment);
                emailAttachmentRequest.setFileName(fileAttachment.getOriginalFilename());
            }
            boolean success = emailService.sendEmail(emailAttachmentRequest);
            if (success) {
                response = new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.error(method + "Failed to send email with Attachment to  {} ", emailAttachmentRequest.getFromEmailAddress());
                throw new ApplicationRuntimeException("Failed to send email with Attachment to " + emailAttachmentRequest.getFromEmailAddress());
            }
            return response;
        } catch (Exception e) {
            log.error(method + "Exception occurred on sendEmailWithAttachment: ", e);
            throw new ApplicationRuntimeException("Failed to send email with Attachment to " + emailAttachmentRequest);
        }
    }
    @GetMapping("/getAllSentEmails")
    public ResponseEntity<List<Email>> getAllSentEmails(){
        final String method = "EmailApi.getAllSentEmails ";
        log.info(method + AppConstants.ENTER);
        return new ResponseEntity<>(emailService.getAllSentEmails(), HttpStatus.OK);
    }
}
