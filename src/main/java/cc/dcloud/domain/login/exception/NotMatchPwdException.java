package cc.dcloud.domain.login.exception;

public class NotMatchPwdException extends RuntimeException {

    private static final String NOT_MATCH_PASSWORD = "비밀번호가 틀렸습니다.";

    public NotMatchPwdException() {
        this(NOT_MATCH_PASSWORD);
    }

    public NotMatchPwdException(String message) {
        super(message);
    }
}
