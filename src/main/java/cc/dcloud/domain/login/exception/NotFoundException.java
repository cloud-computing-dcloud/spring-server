package cc.dcloud.domain.login.exception;

public class NotFoundException extends RuntimeException{

    private static final String NOT_FOUND_MESSAGE = "찾는 유저가 없습니다.";

    public NotFoundException() {
        this(NOT_FOUND_MESSAGE);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
