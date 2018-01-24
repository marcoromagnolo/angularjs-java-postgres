package dmt.server.data.table;

import dmt.server.data.annotation.Column;
import dmt.server.data.annotation.Table;
import dmt.server.data.helper.AbstractTable;

@Table(name = "user_group")
public class UserGroupTable extends AbstractTable {

    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Integer groupId;

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "UserGroupTable{" + "id=" + getId() + ", version=" + getVersion() +
                ", userId=" + userId + ", groupId=" + groupId +
                '}';
    }
}
