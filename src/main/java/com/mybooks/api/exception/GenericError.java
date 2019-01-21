package com.mybooks.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
public class GenericError extends ResourceSupport {
    private String message;

    public GenericError(String message) {
        this.message = message;
    }
}
