package data;

import lombok.Getter;

@Getter
public enum LoginData {

    STANDARD_USER("standard_user","secret_sauce"),
    LOCKED_OUT_USER("locked_out_user","secret_sauce"),
    INVALID_USER("invalid_user","invalid_password");

    private final String username;
    private final String password;

    LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
