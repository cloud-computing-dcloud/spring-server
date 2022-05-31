package cc.dcloud.domain.login.exception;

public class NotMatchTokenException extends RuntimeException {

    private static final String NOT_MATCH_TOKEN = "토큰이 일치하지 않습니다.";

    public NotMatchTokenException() {
        this(NOT_MATCH_TOKEN);
    }

    public NotMatchTokenException(String message) {
        super(message);
    }
}
