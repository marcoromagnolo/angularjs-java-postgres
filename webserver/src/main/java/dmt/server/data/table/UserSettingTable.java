package dmt.server.data.table;

import dmt.server.data.annotation.Column;
import dmt.server.data.annotation.Table;
import dmt.server.data.helper.AbstractTable;

import java.util.Date;

@Table(name = "user_setting")
public class UserSettingTable extends AbstractTable {

    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "locale")
    private String locale;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "modify_date")
    private Date modifyDate;
    
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "UserSettingsTable [id=" + getId() + ", version=" + getVersion() + ", locale=" + locale + ", timezone=" + timezone
				+ ", modifyDate=" + modifyDate + ", createDate=" + createDate + "]";
	}

}
