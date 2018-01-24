package dmt.server.service;

import dmt.server.service.dto.UserDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;

import java.util.Date;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public interface UserService {

    List<UserDTO> getUsers(String token) throws AuthorizationException, InternalException;

    UserDTO getUser(String userId, String token) throws GenericException, AuthorizationException, InternalException;

    void saveUser(String userId, String token, String firstName, String lastName, String email, Date birthday, String phone) throws GenericException, AuthorizationException, InternalException;

    void changePassword(String userId, String token, String password) throws GenericException, AuthorizationException, InternalException;
}
