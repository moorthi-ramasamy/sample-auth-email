package com.bcbsm.interview.userauth.model.request;

import lombok.*;

/**
 * @author Moorthi Ramasamy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequest {
    private String userName;
    private String password;
}
