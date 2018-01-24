package dmt.server.service.dto;

import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.entity.UserEntity;

import java.util.Date;

/**
 * @author Marco Romagnolo
 */
public class RegistrationDTO extends AccountDTO {

    private Date birthday;
    private Date createDate;

    public RegistrationDTO(UserAccountEntity userAccount, UserEntity userEntity) {
        super(userAccount, userEntity);
        this.birthday = userAccount.getBirthday();
        this.createDate = userAccount.getCreateDate();
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getCreateDate() {
        return createDate;
    }

}
