package uz.ccrew.flightmanagement.exp.unauthorized;

public class TokenExpiredException extends Unauthorized {
    public TokenExpiredException(String message) {
        super(message);
    }
}