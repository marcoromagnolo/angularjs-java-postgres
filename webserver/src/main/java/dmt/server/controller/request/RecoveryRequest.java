package dmt.server.controller.request;

/**
 * Created by flavio on 01/08/2016.
 */
public class RecoveryRequest {

    private String username;
    private String locale;

    public RecoveryRequest() {
    }

    public RecoveryRequest(String username, String locale) {
        this.username = username;
        this.locale = locale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
