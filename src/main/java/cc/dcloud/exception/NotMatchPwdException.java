package cc.dcloud.exception;

public class NotMatchPwdException extends RuntimeException {

	private static final String NOT_MATCH_PASSWORD = "password not matched";

	public NotMatchPwdException() {
		this(NOT_MATCH_PASSWORD);
	}

	public NotMatchPwdException(String message) {
		super(message);
	}
}
