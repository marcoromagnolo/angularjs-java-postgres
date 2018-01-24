package dmt.server.controller.request;

/**
 * Created by flavio on 28/07/2016.
 */
public class LogoutRequest {

    private String username;
    private String token;

    public LogoutRequest() {
    }

    public LogoutRequest(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
