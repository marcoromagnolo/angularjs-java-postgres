package dmt.server.test.data;

import dmt.server.data.entity.*;

import java.util.Date;

/**
 * @author Marco Romagnolo8/06/2016.
 */
public class EntityFactory {

    private Date now = new Date();
    public static final Long ID = 1000L;

    public UserEntity getUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(ID);
        entity.setBlocked(false);
        entity.setCreateDate(now);
        entity.setModifyDate(now);
        entity.setSettingId(getUserSettingEntity().getId());
        return entity;
    }

    public UserAccountEntity getUserAccountEntity() {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setId(ID);
        entity.setBirthday(now);
        entity.setConfirmCode("123456789");
        entity.setConfirmDate(now);
        entity.setCreateDate(now);
        entity.setEmail("unitTest@test.it");
        entity.setFirstName("firstName");
        entity.setLastName("lastName");
        entity.setModifyDate(now);
        entity.setPassword("Password1");
        entity.setPhone("3334556434");
        entity.setRecoveryId(getUserAccountRecoveryEntity().getId());
        entity.setUserId(getUserEntity().getId());
        entity.setUsername("test_username");
        return entity;
    }

    public UserSettingEntity getUserSettingEntity() {
        UserSettingEntity entity = new UserSettingEntity();
        entity.setId(ID);
        entity.setLocale("ita");
        entity.setModifyDate(now);
        entity.setTimezone("Europe/Copenhagen");
        return entity;
    }

    public UserAccountRecoveryEntity getUserAccountRecoveryEntity() {
        UserAccountRecoveryEntity entity = new UserAccountRecoveryEntity();
        entity.setId(ID);
        entity.setCreateDate(now);
        entity.setTempPassword("tempPass123");
        entity.setValidDate(now);
        return entity;
    }

    public SessionHistoryEntity getSessionHistoryEntity() {
        SessionHistoryEntity entity = new SessionHistoryEntity();
        entity.setId(ID);
        entity.setUserId(ID);
        entity.setToken("abcdef1234567890");
        entity.setExpiry(false);
        entity.setAddress("127.0.0.1");
        entity.setHeaders("Mozilla Firefox");
        entity.setLoginDate(now);
        entity.setLogoutDate(now);
        return entity;
    }

    public SessionEntity getSessionEntity() {
        SessionEntity entity = new SessionEntity();
        entity.setId(ID);
        entity.setUserId(ID);
        entity.setToken("abcdef1234567890");
        entity.setExpiry(false);
        entity.setCreateDate(now);
        return entity;
    }

    public RoleEntity getRoleEntity() {
        RoleEntity entity = new RoleEntity();
        entity.setId(ID);
        entity.setName("View");
        entity.setDescription("Enabled to view content");
        return entity;
    }

    public GroupEntity getGroupEntity() {
        GroupEntity entity = new GroupEntity();
        entity.setId(ID);
        entity.setName("Guest");
        entity.setDescription("Guest Group");
        return entity;
    }

}
