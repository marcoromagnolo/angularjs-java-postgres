package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.UserRoleTable;

public class UserRoleEntity extends UserRoleTable implements Entity {

    @Relation(from = "roleId" , to = "id")
    private RoleEntity role;

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
