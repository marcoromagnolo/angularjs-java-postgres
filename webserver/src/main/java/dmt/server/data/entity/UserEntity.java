package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.UserTable;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class UserEntity extends UserTable implements Entity {

    @Relation(from = "settingId" , to = "id")
    private UserSettingEntity setting;

    @Relation(from = "id" , to = "userId")
    private List<UserGroupEntity> groups;

    @Relation(from = "id" , to = "userId")
    private List<UserRoleEntity> roles;

    public UserSettingEntity getSetting() {
        return setting;
    }

    public void setSetting(UserSettingEntity setting) {
        this.setting = setting;
    }

    public List<UserGroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroupEntity> groups) {
        this.groups = groups;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "setting=" + setting +
                ", " + super.toString() +
                '}';
    }
}
