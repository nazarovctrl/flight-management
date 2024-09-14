package uz.ccrew.flightmanagement.exp;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BasicException {
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
