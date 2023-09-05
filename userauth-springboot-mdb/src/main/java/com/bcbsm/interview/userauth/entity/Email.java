package com.bcbsm.interview.userauth.entity;

import lombok.*;
import org.bson.types.Binary;
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
@Document("emails")
public class Email {
    @Id
    private String id;
    private String fromEmail;
    private String toEmail;
    private String uploadUser;
    private String uploadDate;
    private Binary attachment;
}
