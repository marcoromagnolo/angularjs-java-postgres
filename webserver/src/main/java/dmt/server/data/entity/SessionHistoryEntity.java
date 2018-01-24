package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.SessionHistoryTable;

/**
 * @author Marco Romagnolo
 */
public class SessionHistoryEntity extends SessionHistoryTable implements Entity {

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
        return "SessionHistoryEntity{" +
                "user=" + user +
                ", " + super.toString() +
                '}';
    }
}
