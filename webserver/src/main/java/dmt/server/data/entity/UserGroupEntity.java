package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.UserGroupTable;

public class UserGroupEntity extends UserGroupTable implements Entity {

    @Relation(from = "groupId" , to = "id")
    private GroupEntity group;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

}
