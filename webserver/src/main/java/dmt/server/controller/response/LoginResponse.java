package dmt.server.controller.response;

import java.util.Date;

/**
 * Created by flavio on 27/07/2016.
 */
public class LoginResponse {
    private String token;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private Date createDate;

    public LoginResponse() {
    }

    public LoginResponse(String token, String userId, String username, String firstName, String lastName, Date createDate) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
