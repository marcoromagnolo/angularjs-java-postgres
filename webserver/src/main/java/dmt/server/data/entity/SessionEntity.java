package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.SessionTable;

/**
 * @author Marco Romagnolo
 */
public class SessionEntity extends SessionTable implements Entity {

    @Relation(from = "userId" , to = "id")
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "user=" + user +
                ", " + super.toString() +
                '}';
    }
}
