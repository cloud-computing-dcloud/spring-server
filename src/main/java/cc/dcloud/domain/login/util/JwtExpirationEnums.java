package cc.dcloud.domain.login.util;


public enum JwtExpirationEnums {

    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 30분", 1000L * 60 * 30),
    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 7일", 1000L * 60 * 60 * 24 * 7) ,
    REISSUE_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 3일", 1000L * 60 * 60 * 24 * 3);

    private String description;
    private Long value;

    JwtExpirationEnums(String description, Long value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Long getValue() {
        return value;
    }
}
