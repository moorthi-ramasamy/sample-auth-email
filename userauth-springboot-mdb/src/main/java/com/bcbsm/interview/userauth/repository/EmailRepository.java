package com.bcbsm.interview.userauth.repository;

import com.bcbsm.interview.userauth.entity.Email;
import com.bcbsm.interview.userauth.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Moorthi Ramasamy
 */
public interface EmailRepository extends MongoRepository<Email, String> {
}
