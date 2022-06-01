package cc.dcloud.exception;

public class NotFoundException extends RuntimeException{

    private static final String NOT_FOUND_MESSAGE = "not found";

    public NotFoundException() {
        this(NOT_FOUND_MESSAGE);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
