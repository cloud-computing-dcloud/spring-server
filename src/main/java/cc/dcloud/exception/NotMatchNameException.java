package cc.dcloud.exception;

public class NotMatchNameException extends RuntimeException {

    private static final String NOT_MATCH_NAME = "name not matched";

    public NotMatchNameException() {
        this(NOT_MATCH_NAME);
    }

    public NotMatchNameException(String message) {
        super(message);
    }

}
