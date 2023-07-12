package com.parolaraul.recipeapi.config.web;

import com.parolaraul.recipeapi.config.ApplicationProperties;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final String API_KEY_HEADER_NAME = "X-API-KEY";

    ApplicationProperties applicationProperties;

    public AuthenticationFilter(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print("{ \"error\": \"" + exp.getMessage() + "\" }");
            writer.flush();
            writer.close();
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(API_KEY_HEADER_NAME);
        log.debug("authenticating with api key: " + apiKey);
        if (apiKey == null || !apiKey.equals(applicationProperties.getApiKey())) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
