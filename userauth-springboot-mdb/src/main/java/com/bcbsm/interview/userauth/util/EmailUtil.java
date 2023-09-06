package com.bcbsm.interview.userauth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Utility to handle Email functionality
 * @author Moorthi Ramasamy
 */
@Slf4j
public final class EmailUtil {

    private EmailUtil() {
    }

    /**
     * Transfer the content of the Multipart File into File object
     * to sendEmail with attachment.
     * @param file
     * @return
     * @throws IOException
     */
    public static File convert(MultipartFile file) throws IOException {

        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        file.transferTo(convFile);
        return convFile;
    }
}
