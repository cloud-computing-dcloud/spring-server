package cc.dcloud.domain.login.util;

public enum JwtHeaderUtilEnums {

	GRANT_TYPE("JWT 타입 / Bearer ", "Bearer ");

	private String description;
	private String value;

	JwtHeaderUtilEnums(String description, String value) {
		this.description = description;
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
