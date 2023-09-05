package com.bcbsm.interview.userauth.service.impl;

import com.bcbsm.interview.userauth.entity.Email;
import com.bcbsm.interview.userauth.exception.ApplicationRuntimeException;
import com.bcbsm.interview.userauth.model.request.EmailRequest;
import com.bcbsm.interview.userauth.repository.EmailRepository;
import com.bcbsm.interview.userauth.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Moorthi Ramasamy
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository emailRepository;
	@Override
	public boolean sendEmail(EmailRequest emailRequest) throws ApplicationRuntimeException {
		final String method = "[EmailServiceImpl.sendEmail] ";
		log.info(method + "Enter : emailRequest " + emailRequest);
		Email email = new Email();
		email.setFromEmail(emailRequest.getFromEmailAddress());
		email.setToEmail(emailRequest.getToEmailAddress());
		email.setUploadUser(emailRequest.getUploadUser());
		email.setUploadDate(emailRequest.getUploadDate());
		email.setFileName(emailRequest.getFileName());
		try {
			email.setAttachment(new Binary(emailRequest.getFileAttachment().getBytes()));
		} catch (IOException e) {
			throw new ApplicationRuntimeException(e.getMessage());
		}
		emailRepository.save(email);
		log.info(method + "Exit \n");
		return true;
	}

	public List<Email> getAllSentEmails(){
		final String method = "[EmailServiceImpl.getAllSentEmails] ";
		log.info(method + "Enter");
		return emailRepository.findAll();
	}
}
