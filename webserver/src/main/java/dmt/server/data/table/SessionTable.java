package dmt.server.data.table;

import dmt.server.data.annotation.Column;
import dmt.server.data.annotation.Table;
import dmt.server.data.helper.AbstractTable;

import java.util.Date;

@Table(name = "session")
public class SessionTable extends AbstractTable {

    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry")
    private boolean expiry;

    @Column(name = "create_date")
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpiry() {
        return expiry;
    }

    public void setExpiry(boolean expiry) {
        this.expiry = expiry;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Override
   	public String toString() {
   		return "SessionTable [id=" + getId() + ", version=" + getVersion() + ", userId=" + userId + ", token=" + token
   				+ ", expiry=" + expiry + ", createDate=" + createDate + "]";
   	}

}
