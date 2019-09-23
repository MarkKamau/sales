package data.transform.sales.advice;

import data.transform.sales.error.ErrorMessage;
import data.transform.sales.error.SalesException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SalesErrorHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleExecutionException(Exception ex) {
        return response(103,"Transaction failed", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SalesException.class)
    @ResponseBody
    public ResponseEntity<String> handleExecutionBoardabodaException(SalesException ex) {
        return response(101,"Sales Tranform Exception", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return response(102,"Data Integrity violation", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<String> response(int number, String message,
                                                   String reason,
                                                   HttpStatus httpStatus) {
        String json = createJson(number,message, reason);
        return new ResponseEntity<>(json, httpStatus);
    }

    private static String createJson(int number, String message, String reason) {
        return (new ErrorMessage(number,message,reason).toJsonString());
    }
}