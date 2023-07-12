package com.parolaraul.recipeapi.config.web;

import com.parolaraul.recipeapi.config.ApplicationProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class AuthenticationFilterTest {

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Mock
    private ApplicationProperties applicationProperties;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidApiKey() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("X-API-KEY")).thenReturn("valid-api-key");
        when(applicationProperties.getApiKey()).thenReturn("valid-api-key");

        authenticationFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testInvalidApiKey() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getHeader("X-API-KEY")).thenReturn("invalid-api-key");
        when(applicationProperties.getApiKey()).thenReturn("valid-api-key");
        when(response.getWriter()).thenReturn(writer);

        authenticationFilter.doFilter(request, response, filterChain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, times(1)).setContentType("application/json");
        verify(writer, times(1)).print("{ \"error\": \"Invalid API Key\" }");
        verify(writer, times(1)).flush();
        verify(writer, times(1)).close();
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testMissingApiKey() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(request.getHeader("X-API-KEY")).thenReturn(null);
        when(applicationProperties.getApiKey()).thenReturn("valid-api-key");
        when(response.getWriter()).thenReturn(writer);

        authenticationFilter.doFilter(request, response, filterChain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, times(1)).setContentType("application/json");
        verify(writer, times(1)).print("{ \"error\": \"Invalid API Key\" }");
        verify(writer, times(1)).flush();
        verify(writer, times(1)).close();
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
