package com.lineplus.featurestore.global.exception;

import java.util.ArrayList;
import java.util.List;

import com.lineplus.featurestore.global.config.CustomCollectionValidator;
import com.lineplus.featurestore.global.reponse.FeatureStoreResponse;
import com.lineplus.featurestore.global.reponse.ResponseCodes;
import com.lineplus.featurestore.global.reponse.FeatureStoreExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ControllerAdviser {

    protected LocalValidatorFactoryBean validator;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new CustomCollectionValidator(validator));
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    private ResponseEntity<FeatureStoreExceptionResponse> invalidDataException(MethodArgumentNotValidException e){

        List<String> fields = new ArrayList<String>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            fields.add(error.getField());
        }
        ResponseCodes code = ResponseCodes.INVALID_BODY;
        FeatureStoreExceptionResponse response = new FeatureStoreExceptionResponse(fields, code.getMessage());
        return new ResponseEntity<FeatureStoreExceptionResponse>(response,HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<FeatureStoreResponse> serverErrorExeption(Exception e){
        log.error("error",e);
        ResponseCodes code = ResponseCodes.SERVER_ERROR;
        FeatureStoreExceptionResponse response = new FeatureStoreExceptionResponse(code);
        return new ResponseEntity<FeatureStoreResponse>(response.getResponse(),HttpStatus.OK);
    }

    @ExceptionHandler({BindException.class})
    private ResponseEntity<FeatureStoreResponse> bindErrorExeption(BindException e){
        log.error("error",e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> fields = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            fields.add(fieldError.getField());
            messages.add(fieldError.getDefaultMessage());
        }
        ResponseCodes code = ResponseCodes.SERVER_ERROR;
        FeatureStoreExceptionResponse response = new FeatureStoreExceptionResponse(code, fields, messages.toString());

        return new ResponseEntity<FeatureStoreResponse>(response.getResponse(),HttpStatus.OK);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    private ResponseEntity<FeatureStoreExceptionResponse> invalidDataException(MissingServletRequestParameterException e){
        List<String> fields = new ArrayList<String>();
        fields.add(e.getParameterName());
        FeatureStoreExceptionResponse response = new FeatureStoreExceptionResponse(fields,ResponseCodes.INVALID_PARAMETER.getMessage());
        return new ResponseEntity<FeatureStoreExceptionResponse>(response,HttpStatus.OK);
    }

    @ExceptionHandler({FeatureStoreRuntimeException.class})
    private ResponseEntity<FeatureStoreResponse> GoodsException(FeatureStoreRuntimeException e){
        FeatureStoreExceptionResponse response;

        if(StringUtils.hasText(e.getMessage())) {
            response = new FeatureStoreExceptionResponse(e.getCode(), e.getMessage());
        } else {
            response = new FeatureStoreExceptionResponse(e.getCode());
        }

        return new ResponseEntity<FeatureStoreResponse>(response.getResponse(),HttpStatus.OK);
    }




}
