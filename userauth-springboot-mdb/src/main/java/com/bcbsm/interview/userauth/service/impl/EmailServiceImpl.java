package com.bcbsm.interview.userauth.service.impl;

import com.bcbsm.interview.userauth.entity.Email;
import com.bcbsm.interview.userauth.exception.ApplicationRuntimeException;
import com.bcbsm.interview.userauth.model.request.EmailRequest;
import com.bcbsm.interview.userauth.repository.EmailRepository;
import com.bcbsm.interview.userauth.service.EmailService;
import com.bcbsm.interview.userauth.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public boolean sendEmail(EmailRequest emailRequest) throws ApplicationRuntimeException {
		final String method = "[EmailServiceImpl.sendEmail] ";
		log.info(method + "Enter : emailRequest " + emailRequest);

		Binary binary = null;
		try {
			binary = new Binary(emailRequest.getFileAttachment().getBytes());
		} catch (IOException e) {
			throw new ApplicationRuntimeException(e.getMessage());
		}
		//Send email using SMTP server
		sendMailWithAttachment(emailRequest);
		//Save Email request object with attachment into MongoDB
		Email email = new Email();
		email.setFromEmail(emailRequest.getFromEmailAddress());
		email.setToEmail(emailRequest.getToEmailAddress());
		email.setUploadUser(emailRequest.getUploadUser());
		email.setUploadDate(emailRequest.getUploadDate());
		email.setFileName(emailRequest.getFileName());
		email.setAttachment(binary);
		emailRepository.save(email);
		log.info(method + "Exit \n");
		return true;
	}

	/**
	 * Send Email to the recipient using google SMTP server
	 * @param emailRequest
	 * @return boolean
	 */
	protected boolean sendMailWithAttachment(EmailRequest emailRequest){
		boolean success = false;
		final String method = "[EmailServiceImpl.sendMailWithAttachment] ";
		log.info(method + "Enter : emailRequest " + emailRequest);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = null;
		try {
			mmh = new MimeMessageHelper(mimeMessage, true);
			mmh.setFrom(emailRequest.getFromEmailAddress());
			mmh.setTo(emailRequest.getToEmailAddress());
			mmh.setText("Email with attachment");
			mmh.setSubject("Email Subject");
			// build FileSystemResource to be sent with mimeMessage
			FileSystemResource file = new FileSystemResource(emailRequest.getFileAttachment().getOriginalFilename());
			//Copy the content of Multipart File into the FileSystemResource and add it to MimeMessageHelper
			mmh.addAttachment(file.getFilename(), EmailUtil.convert(emailRequest.getFileAttachment()));
			javaMailSender.send(mimeMessage);
			success = true;
		}catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new ApplicationRuntimeException(e.getMessage());
		}catch (MessagingException e) {
			log.error(e.getMessage(), e);
			throw new ApplicationRuntimeException(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ApplicationRuntimeException(e.getMessage());
		}
		return success;
	}
	public List<Email> getAllSentEmails(){
		final String method = "[EmailServiceImpl.getAllSentEmails] ";
		log.info(method + "Enter");
		return emailRepository.findAll();
	}
}
