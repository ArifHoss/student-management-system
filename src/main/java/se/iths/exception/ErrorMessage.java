package se.iths.exception;

public class ErrorMessage {

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage() {
    }

    public String getMessage() {
        return message;
    }
}
