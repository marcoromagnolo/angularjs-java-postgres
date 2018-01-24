package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.UserTable;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class UserSessionHistoryEntity extends UserTable implements Entity {

    @Relation(from = "id" , to = "userId")
    private List<SessionHistoryEntity> sessionHistories;

    public List<SessionHistoryEntity> getSessionHistories() {
        return sessionHistories;
    }

    public void setSessionHistories(List<SessionHistoryEntity> sessionHistories) {
        this.sessionHistories = sessionHistories;
    }
}
