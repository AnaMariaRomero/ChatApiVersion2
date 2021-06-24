package net.apimessages.pd2.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MessageExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ServerError.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        MessageErrorResponse error = new MessageErrorResponse("Server Error", details);
        return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
	@ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<Object> handleMessageNotFoundException(MessageNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        MessageErrorResponse error = new MessageErrorResponse("Not Found", details);
        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
    }
 
	@ExceptionHandler(BadRequest.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(BadRequest ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        MessageErrorResponse error = new MessageErrorResponse("Validation Failed", details);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

}
