package uz.ccrew.flightmanagement.exp;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends BasicException {
    public AlreadyExistException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}