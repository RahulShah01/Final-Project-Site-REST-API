package site.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

/*
 * Global error handler class is created to handle and log exceptions in a organized way rather than
 * returning stack trace.
 * 
 * @RestControllerAdvice and @Slf4j annotations are used to log the exceptions.
 * 
 */

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

  /*
   * handleNoSuchElementException and handleIllegalArgumentException methods specifies a response
   * status of 404 not found. These methods take NoSuchElementException and IllegalArgumentException
   * as a parameter and returns Map with Key "message" and the result of the exception parameter ex
   * by calling toString().
   * 
   */

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
    log.error("NoSuchElementException", ex.toString());
    return Map.of("message", ex.toString());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("NoSuchElementException", ex.toString());
    return Map.of("message", ex.toString());
  }
}
