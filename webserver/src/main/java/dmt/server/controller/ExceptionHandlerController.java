package dmt.server.controller;

import dmt.server.controller.exception.HttpException;
import dmt.server.controller.response.ErrorResponse;
import dmt.server.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerController {

    private static final Logger logger = Logger.getLogger(ExceptionHandlerController.class.getName());

    private ResponseEntity toResponse(ErrorType errorType, Map<String, ErrorType> fields, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), errorType.name(), errorType.getMessage());
        if (fields!=null) {
            error.setFields(new HashMap<String, String>());
            for (Map.Entry entry : fields.entrySet()) {
                error.getFields().put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception ex) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
        return toResponse(ErrorType.ERROR_GENERIC, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpException.class})
    public ResponseEntity handleHttpException(HttpException ex) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
        return toResponse(ex.getErrorType(), ex.getFields(), ex.getHttpStatus());
    }


}
