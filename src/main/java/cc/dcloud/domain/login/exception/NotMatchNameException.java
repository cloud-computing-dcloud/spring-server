package cc.dcloud.domain.login.exception;

public class NotMatchNameException extends RuntimeException {

    private static final String NOT_MATCH_NAME = "유저의 아이디가 틀렸습니다.";

    public NotMatchNameException() {
        this(NOT_MATCH_NAME);
    }

    public NotMatchNameException(String message) {
        super(message);
    }

}
