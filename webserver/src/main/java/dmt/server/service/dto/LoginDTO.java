package dmt.server.service.dto;

/**
 * @author Marco Romagnolo
 */
public class LoginDTO {

    private SessionDTO session;
    private AccountDTO account;

    public LoginDTO(SessionDTO sessionDTO, AccountDTO accountDTO) {
        this.session = sessionDTO;
        this.account = accountDTO;
    }

    public LoginDTO(SessionDTO sessionDTO) {
        this.session = sessionDTO;
    }

    public SessionDTO getSession() {
        return session;
    }

    public AccountDTO getAccount() {
        return account;
    }

}
