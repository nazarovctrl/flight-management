package uz.ccrew.flightmanagement.exp;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BasicException {
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}