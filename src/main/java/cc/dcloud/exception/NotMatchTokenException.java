package cc.dcloud.exception;

public class NotMatchTokenException extends RuntimeException {

    private static final String NOT_MATCH_TOKEN = "token not matched";

    public NotMatchTokenException() {
        this(NOT_MATCH_TOKEN);
    }

    public NotMatchTokenException(String message) {
        super(message);
    }
}
