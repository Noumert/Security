package com.example.demo.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Noumert on 15.08.2021.
 */
@Component
public class ControllerUtils {
    public Map<String, String> getErrorsMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors
                        .toMap(fieldError -> fieldError.getField() + "Error",
                                FieldError::getDefaultMessage));
    }
}
