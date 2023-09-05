package com.bcbsm.interview.userauth.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Represents fields required to send an email message.
 * @author Moorthi Ramasamy
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EmailRequest implements Serializable {
    private static final long serialVersionUID = 5645934353354227L;

    //Required
    protected String fromEmailAddress; //From email address
    protected String toEmailAddress; //accepts comma or semi-colon delimited email addresses.
    protected String uploadUser;
    protected String uploadDate;
    protected String fileName;

    //Optional
    protected transient MultipartFile fileAttachment;
    protected transient byte[] fileAttachmentContent;
}
