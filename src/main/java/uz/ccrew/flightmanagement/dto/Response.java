package uz.ccrew.flightmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.LinkedList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private T data;
    private List<String> errors;
    private String message;

    public Response(T data) {
        this.data = data;
    }

    public Response(String error) {
        addError(error);
    }

    public Response(int code, String error, T data) {
        this.data = data;
        addError(error);
    }

    public Response() {
    }

    public void addError(String error) {
        if (errors == null) {
            errors = new LinkedList<>();
        }
        this.errors.add(error);
    }
}