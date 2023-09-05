package com.bcbsm.interview.userauth.service;


import com.bcbsm.interview.userauth.entity.Email;
import com.bcbsm.interview.userauth.exception.ApplicationRuntimeException;
import com.bcbsm.interview.userauth.model.request.EmailRequest;

import java.util.List;

/**
 * @author Moorthi Ramasamy
 *
 */
public interface EmailService {

	/**
	 * Send email notifcation with Subject, Body and Attachment
	 * @param emailRequest
	 * @return
	 */
	boolean sendEmail(EmailRequest emailRequest) throws ApplicationRuntimeException;

	List<Email> getAllSentEmails();

}
