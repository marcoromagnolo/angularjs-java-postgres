package dmt.server.controller;

import dmt.server.controller.exception.HttpForbiddenException;
import dmt.server.controller.exception.HttpInternalServerErrorException;
import dmt.server.controller.exception.HttpUnauthorizedException;
import dmt.server.service.UserService;
import dmt.server.service.dto.UserDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@RestController
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestParam(value = "token", required = false) String token) {
        try {
            List<UserDTO> users = userService.getUsers(token);
            return new ResponseEntity<List>(users, HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable(value = "userId") String userId,
                                  @RequestParam(value = "token", required = false) String token) {
        try {
            UserDTO user = userService.getUser(userId, token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

}
