package dmt.server.service;

import dmt.server.service.dto.LoginDTO;
import dmt.server.service.dto.RegistrationDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;

import java.util.Locale;

/**
 * @author Marco Romagnolo
 */
public interface AccountService {

    LoginDTO login(String username, String password, boolean expiry, String remoteAddress, String headers) throws InternalException, GenericException;

    void logout(String username, String token, String remoteAddress, String headers) throws AuthorizationException, InternalException, GenericException;

    void recovery(String email) throws InternalException, GenericException;

    void reset(String username, String tempPassword, String password, String remoteAddr, String headers) throws InternalException, GenericException;

    RegistrationDTO register(Locale locale, String firstName, String lastName, String email, String username, String password) throws InternalException, GenericException;

    void confirm(String email, String code) throws InternalException, GenericException;

    void verifyEmail(String email) throws InternalException, GenericException;

    void verifyUsername(String username) throws InternalException, GenericException;
}
