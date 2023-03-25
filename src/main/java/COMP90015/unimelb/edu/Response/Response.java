package COMP90015.unimelb.edu.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    private String message;
    private ResponseStatus status;

    public Response(@JsonProperty("message") String message, @JsonProperty("status") ResponseStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
