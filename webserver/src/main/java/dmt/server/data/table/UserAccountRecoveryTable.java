package dmt.server.data.table;

import dmt.server.data.annotation.Column;
import dmt.server.data.annotation.Table;
import dmt.server.data.helper.AbstractTable;

import java.util.Date;

@Table(name = "user_account_recovery")
public class UserAccountRecoveryTable extends AbstractTable {

    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "temp_password")
    private String tempPassword;

    @Column(name = "valid_date")
    private Date validDate;

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

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	@Override
	public String toString() {
		return "UserAccountRecoveryTable [id=" + getId() + ", version=" + getVersion() + ", tempPassword=" + tempPassword
				+ ", validDate=" + validDate + ", createDate=" + createDate + "]";
	}

}
