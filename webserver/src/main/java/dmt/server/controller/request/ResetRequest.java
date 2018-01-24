package dmt.server.controller.request;

/**
 * Created by flavio on 02/08/2016.
 */
public class ResetRequest {

    private String username;
    private String tempPassword;
    private String password;

    public ResetRequest() {
    }

    public ResetRequest(String username, String tempPassword, String password) {
        this.username = username;
        this.tempPassword = tempPassword;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
