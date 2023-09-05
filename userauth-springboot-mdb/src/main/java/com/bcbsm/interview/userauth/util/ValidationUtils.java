package com.bcbsm.interview.userauth.util;

import com.bcbsm.interview.userauth.exception.BadRequestException;
import com.bcbsm.interview.userauth.model.request.LoginRequest;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to validate rest service request params.
 * @author Moorthi Ramasamy
 */
@Slf4j
public class ValidationUtils {
    public static final String MISSING_REQ_PARAMS_MSG = "Missing required parameters";
    private ValidationUtils() {
    }

    /**
     * Validate the input string params to not null
     * @param vals
     * @return
     */
    public static boolean isValid(String... vals) {
        boolean isValid = true;
        for (String s : vals) {
            if (StringUtils.isBlank(s)) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
    public static void validate(LoginRequest s) throws BadRequestException {
        if (!isValid(s.getUserName()) || !isValid(s.getPassword())) {
            throw new BadRequestException(ValidationUtils.MISSING_REQ_PARAMS_MSG);
        }
    }
    public static void validate(String s) throws BadRequestException {
        if (!isValid(s)) {
            throw new BadRequestException(ValidationUtils.MISSING_REQ_PARAMS_MSG);
        }
    }
}
