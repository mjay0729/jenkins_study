package com.lineplus.featurestore.global.config;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collection;
import java.util.List;

public class CustomCollectionValidator implements Validator {

    private final Validator validator;

    public CustomCollectionValidator(LocalValidatorFactoryBean validatorFactory) {
        this.validator = validatorFactory;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof List) {
            Collection collection = (Collection) target;
            for (Object object : collection) {
                ValidationUtils.invokeValidator(validator, object, errors);
            }
        }
    }
}

