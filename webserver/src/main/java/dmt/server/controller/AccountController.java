package dmt.server.controller;

import dmt.server.controller.exception.HttpForbiddenException;
import dmt.server.controller.exception.HttpInternalServerErrorException;
import dmt.server.controller.exception.HttpUnauthorizedException;
import dmt.server.controller.request.*;
import dmt.server.controller.response.LoginResponse;
import dmt.server.controller.util.HttpUtil;
import dmt.server.service.AccountService;
import dmt.server.service.UserService;
import dmt.server.service.dto.LoginDTO;
import dmt.server.service.dto.UserDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;
import dmt.server.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@RestController
//@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity getAccount(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "token") String token) {
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

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity saveAccount(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "token") String token,
                                     @RequestParam(value = "firstName") String firstName,
                                     @RequestParam(value = "lastName") String lastName,
                                     @RequestParam(value = "email") String email,
                                     @RequestParam(value = "birthday") String birthStr,
                                     @RequestParam(value = "phone") String phone) {
        try {
            Date birthday = DateUtil.parseDate(birthStr);
            userService.saveUser(userId, token, firstName, lastName, email, birthday, phone);
            return new ResponseEntity<>(HttpStatus.OK);
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

    @RequestMapping(value = "/account/changepassword", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestParam(value = "userId") String userId,
                                      @RequestParam(value = "token") String token,
                                      @RequestParam(value = "password") String password) {
        try {
            userService.changePassword(userId, token, password);
            return new ResponseEntity<>(HttpStatus.OK);
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity login(@RequestBody LoginRequest loginRequest,
                         HttpServletRequest request) {
        try {
            LoginDTO login = accountService.login(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.isExpiry(), request.getRemoteAddr(), HttpUtil.getHeaders(request));
            LoginResponse response = new LoginResponse(login.getSession().getToken(), login.getSession().getUserId(), login.getAccount().getUsername(), login.getAccount().getFirstName(), login.getAccount().getLastName(), login.getSession().getCreateDate());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    ResponseEntity logout(@RequestBody LogoutRequest logoutRequest,
                          HttpServletRequest request) {
        try {
            accountService.logout(logoutRequest.getUsername(), logoutRequest.getToken(), request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(HttpStatus.OK);
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

    @RequestMapping(value = "/recovery", method = RequestMethod.POST)
    ResponseEntity recovery(@RequestBody RecoveryRequest recoveryRequest) {

        try {
            accountService.recovery(recoveryRequest.getUsername());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    ResponseEntity reset(@RequestBody ResetRequest resetRequest,
                         HttpServletRequest request) {
        try {
            accountService.reset(resetRequest.getUsername(), resetRequest.getTempPassword(), resetRequest.getPassword(), request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseEntity register(@RequestBody RegisterRequest registerRequest,
                            HttpServletRequest request) {
        try {
            accountService.register(new Locale(registerRequest.getLocale()), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(), registerRequest.getUsername(), registerRequest.getPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/register/verify/email", method = RequestMethod.POST)
    ResponseEntity verifyEmail(@RequestBody Map<String, String> map) {
        try {
            accountService.verifyEmail(map.get("email"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    ResponseEntity confirm(@RequestParam("email") String email, @RequestParam("code") String code) {
        try {
            accountService.confirm(email, code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    ResponseEntity confirmByLink(@RequestParam("email") String email, @RequestParam("code") String code,
                                 HttpServletResponse response) {
        try {
            accountService.confirm(email, code);
            response.sendRedirect("http://localhost:9000/#/");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

}
