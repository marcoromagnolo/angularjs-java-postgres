package dmt.server.controller.request;

/**
 * Created by flavio on 27/07/2016.
 */
public class LoginRequest {
    private String username;
    private String password;
    private boolean expiry;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password, boolean expiry) {
        this.username = username;
        this.password = password;
        this.expiry = expiry;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isExpiry() {
        return expiry;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExpiry(boolean expiry) {
        this.expiry = expiry;
    }
}
