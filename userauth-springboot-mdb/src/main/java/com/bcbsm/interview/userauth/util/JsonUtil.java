package com.bcbsm.interview.userauth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to manipulate json
 * @author Moorthi Ramasamy
 *
 */
@Slf4j
public final class JsonUtil {

	private JsonUtil() {
		super();
	}

    public static String objectToJson(Object object) {
        return objectToJson(object, false);
    }

    public static String objectToJson(Object object, boolean prettyPrint) {
        String result = "<json marshalling error>";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if (prettyPrint) {
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(baos, object);
            } else {
                new ObjectMapper().writeValue(baos, object);
            }
            result = baos.toString(StandardCharsets.UTF_8.toString());
        } catch (IOException ex) {
            log.error("objectToJson marshalling, error {}", ex.getMessage());
        }
        return result;
    }

}
