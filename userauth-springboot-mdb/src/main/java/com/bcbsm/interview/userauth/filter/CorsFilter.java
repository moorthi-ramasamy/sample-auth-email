package com.bcbsm.interview.userauth.filter;


import com.bcbsm.interview.userauth.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Stream;
/**
 * @author Moorthi Ramasamy
 *
 */
@Component
@Slf4j
public class CorsFilter extends  OncePerRequestFilter {

    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        log.info("RequestURL : " + request.getRequestURL());
        log.info("RequestURI : " + requestURI);
        Enumeration<String> headerNames = request.getHeaderNames();
        Stream<String> stream = Stream.of(
                "/swagger-ui/");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with, x-auth-token");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        if(!("OPTIONS".equalsIgnoreCase(request.getMethod()))) {
            /**
             * Check for token if the request is not from localhost
             */
            if (stream.anyMatch(s -> requestURI.contains(s))
                    || isTokenExists(request, headerNames)
                    || isLocalHost(request)) {
                try {
                    chain.doFilter(req, res);
                } catch (RuntimeException e) {
                    log.error("doFilterInternal - CorsFilter Exception:: ", e);
                }
            } else {
                /*
                Throw an errorResponse if the token is missing in the header
                 */
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                res.sendError(HttpStatus.UNAUTHORIZED.value(), "The access token is missing");
                res.setContentLength(100);
                res.getWriter().write(convertObjectToJson(errorResponse));
            }
        }else{
            log.info("Pre-fight");
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allowed-Methods", "POST, GET, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "authorization, content-type, x-auth-token, " +
                    "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
            res.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private boolean isLocalHost(HttpServletRequest request) {
        if(request.getRequestURL().toString().contains("localhost:")){
            return true;
        }
        return false;
    }

    private boolean isTokenExists(HttpServletRequest request, Enumeration<String> headerNames) {
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                if("authorization".equalsIgnoreCase(name)){
                    log.info(name +" : " + value);
                    return true;
                }
            }
        }
        return false;
    }

    public String convertObjectToJson(Object object) {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return "";
    }
}
