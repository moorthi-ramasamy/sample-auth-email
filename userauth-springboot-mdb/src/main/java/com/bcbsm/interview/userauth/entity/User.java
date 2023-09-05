package com.bcbsm.interview.userauth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author Moorthi Ramasamy
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
}
