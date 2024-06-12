package com.zahir.fathurrahman.malite.core.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter
@ToString
@Embeddable
public class AppVarConfig {
    private final Dotenv dotenv = Dotenv.configure().directory("/").ignoreIfMissing().ignoreIfMalformed().filename(".env").load();
    private final String PORT = dotenv.get("PORT");
    private final String ENV = dotenv.get("ENV");

    private final String DB_URL = dotenv.get("DB_URL");
    private final String DB_USERNAME = dotenv.get("DB_USERNAME");
    private final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private final String JWT_SECRET = dotenv.get("JWT_SECRET");
    private final String JWT_EXPIRED_MILLIS = dotenv.get("JWT_EXPIRED_MILLIS");
    private final String JWT_REFRESH_MILLIS = dotenv.get("JWT_REFRESH_MILLIS");

    private final String MAL_URL = dotenv.get("MAL_URL");
    private final String MAL_CLIENT_ID = dotenv.get("MAL_CLIENT_ID");
}
