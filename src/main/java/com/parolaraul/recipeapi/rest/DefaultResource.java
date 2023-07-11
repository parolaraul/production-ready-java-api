package com.parolaraul.recipeapi.rest;

import com.parolaraul.recipeapi.rest.exceptions.HttpErrorResponse;
import com.parolaraul.recipeapi.rest.exceptions.HttpExceptionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultResource {

    @RequestMapping("/**")
    public ResponseEntity<HttpErrorResponse> handleUnknownPath() {
        return HttpExceptionFactory.notFound("NOT_FOUND", "resource not found");
    }

}
