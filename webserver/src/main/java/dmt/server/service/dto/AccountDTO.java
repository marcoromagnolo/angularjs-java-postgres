package dmt.server.service.dto;

import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.entity.UserEntity;

/**
 * @author Marco Romagnolo
 */
public class AccountDTO extends UserDTO {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;

    public AccountDTO(UserAccountEntity userAccount, UserEntity userEntity) {
        super(userEntity);
        this.firstName = userAccount.getFirstName();
        this.lastName = userAccount.getLastName();
        this.email = userAccount.getEmail();
        this.username = userAccount.getUsername();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
