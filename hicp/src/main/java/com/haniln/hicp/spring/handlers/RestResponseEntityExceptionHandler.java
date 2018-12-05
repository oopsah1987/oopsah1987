package com.haniln.hicp.spring.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Exception Handler
 * @author  : Lee Jung Min
 * @version : 2018-01-22 creation
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    // @Autowired
    // protected SimpleMessageSource message;

    @ExceptionHandler(value = { SQLException.class })
    protected ResponseEntity<Object> handleConflictSQL(SQLException exception, WebRequest request) {
        this.loggingException(exception);
        Map<String,Object> body = new HashMap<String,Object>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("exception", exception.getClass());
        body.put("message", "SQL Exception");
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // @ExceptionHandler({ AccessDeniedException.class }) 
    // public ResponseEntity<Object> handleEverything(final AccessDeniedException exception, final WebRequest request) { 
    //     logger.error("403 Status Code", exception); 
    //     return handleExceptionInternal(exception, "32323", new HttpHeaders(), HttpStatus.FORBIDDEN, request); 
    // }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(Exception exception, WebRequest request) {
        this.loggingException(exception);
        Map<String,Object> body = new HashMap<String,Object>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("statusText", "상태텍스트");
        body.put("exception", exception.getClass());
        body.put("message", HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private void loggingException(Exception exception) {
        exception.printStackTrace();
        StringWriter errors = new StringWriter();
        exception.printStackTrace(new PrintWriter(errors));
        logger.error("\n### Exception Stack Trace ###\n"+errors);
    }
}