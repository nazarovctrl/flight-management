package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.exp.*;
import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import com.auth0.jwt.exceptions.JWTDecodeException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Response<?> r = new Response<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> r.addError(fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(r);
    }

    @ExceptionHandler({BasicException.class})
    private ResponseEntity<Response<?>> basicHandler(BasicException e) {
        return ResponseMaker.error(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class})
    private ResponseEntity<Response<?>> forbiddenHandler(RuntimeException e) {
        return ResponseMaker.error(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({JWTDecodeException.class, SignatureException.class})
    private ResponseEntity<Response<?>> unauthorizedHandler(RuntimeException e) {
        return ResponseMaker.error(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Response<?>> handle(Exception e) {
        e.printStackTrace();
        return ResponseMaker.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}