package cc.dcloud.exception;

import cc.dcloud.domain.memberGroup.MemberGroup;

public class AlreadyExistException extends RuntimeException{

    private static final String ALREADY_EXIST_MESSAGE = "not found";

    public AlreadyExistException() {
        this(ALREADY_EXIST_MESSAGE);
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
