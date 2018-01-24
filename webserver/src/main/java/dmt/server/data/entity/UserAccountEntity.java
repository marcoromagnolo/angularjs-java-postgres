package dmt.server.data.entity;

import dmt.server.data.annotation.Relation;
import dmt.server.data.helper.Entity;
import dmt.server.data.table.UserAccountTable;

public class UserAccountEntity extends UserAccountTable implements Entity {

    @Relation(from = "userId" , to = "id")
    private UserEntity user;

    @Relation(from = "recoveryId" , to = "id")
    private UserAccountRecoveryEntity recovery;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserAccountRecoveryEntity getRecovery() {
        return recovery;
    }

    public void setRecovery(UserAccountRecoveryEntity recovery) {
        this.recovery = recovery;
    }

    @Override
    public String toString() {
        return "UserAccountEntity{" +
                "user=" + user +
                ", recovery=" + recovery +
                ", " + super.toString() +
                '}';
    }
    
}
