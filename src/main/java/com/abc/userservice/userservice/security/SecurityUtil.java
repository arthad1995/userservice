package com.abc.userservice.userservice.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.abc.userservice.userservice.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SecurityUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendResponse(HttpServletResponse response, int status, String message, Exception exception,
    		Collection<? extends GrantedAuthority> authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(new Response<Collection<? extends GrantedAuthority>>(exception == null ? true : false, status, message,authentication)));
        response.setStatus(status);
        writer.flush();
        writer.close();
    }
}
