package com.dmx.api.controller.meta;

import com.dmx.api.ApiApplication;
import com.dmx.api.bean.MessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
@RestController
public class ErrorControllerAdvise implements ErrorController {
    private static final String PATH = "/error";

    private static final Logger logger = LogManager.getLogger(ApiApplication.class);

    @Override
    public String getErrorPath() {
        return PATH;

    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<MessageResponse> sqlFailed(Exception ex) {
        logger.error("service sql exception:" + ex.getMessage());

        return new ResponseEntity<MessageResponse>(new MessageResponse(-1,  "service exception"), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> defaultFailed(IOException ex) {
        logger.error("service io exception:" + ex.getMessage());

        return new ResponseEntity<MessageResponse>(new MessageResponse(-1,  "service exception"), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> defaultFailed(Exception ex) {
        logger.error("service exception:" + ex.getMessage());

        return new ResponseEntity<MessageResponse>(new MessageResponse(-1,  "service exception"), HttpStatus.OK);
    }
}
