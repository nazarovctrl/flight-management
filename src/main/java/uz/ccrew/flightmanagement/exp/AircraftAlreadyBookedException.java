package uz.ccrew.flightmanagement.exp;

import org.springframework.http.HttpStatus;

public class AircraftAlreadyBookedException extends BasicException {
    public AircraftAlreadyBookedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
