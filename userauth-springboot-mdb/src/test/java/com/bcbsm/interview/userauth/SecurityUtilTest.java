package com.bcbsm.interview.userauth;

import com.bcbsm.interview.userauth.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
/**
 * @author Moorthi Ramasamy
 */

@RunWith(MockitoJUnitRunner.class)
public class SecurityUtilTest {
    @InjectMocks
    private SecurityUtil securityUtil;

    @Test
    public void testAuthenticate() throws Exception{
        String password = "test";
        String decodedText = securityUtil.encodeDecode(password);
        assertEquals(decodedText, password);
    }
}
