package com.bartkoo98.influxv1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException{
    private String resourceName;
    private String fieldName;

    public ResourceAlreadyExistsException(String resourceName, String fieldName) {
        super(String.format("%s with name '%s' already exists.", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
