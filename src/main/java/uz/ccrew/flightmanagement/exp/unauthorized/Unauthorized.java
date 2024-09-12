package uz.ccrew.flightmanagement.exp.unauthorized;

import uz.ccrew.flightmanagement.exp.BasicException;

import org.springframework.http.HttpStatus;

public class Unauthorized extends BasicException {
    public Unauthorized() {
        super("Unauthorized Exception");
    }

    public Unauthorized(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}