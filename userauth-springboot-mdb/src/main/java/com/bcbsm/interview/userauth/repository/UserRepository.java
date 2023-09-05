package com.bcbsm.interview.userauth.repository;

import com.bcbsm.interview.userauth.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * @author Moorthi Ramasamy
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserIdAndPassword(String userId, String password);
}
