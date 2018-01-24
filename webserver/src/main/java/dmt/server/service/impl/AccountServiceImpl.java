package dmt.server.service.impl;

import dmt.server.component.Mail;
import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.entity.UserAccountRecoveryEntity;
import dmt.server.data.entity.UserEntity;
import dmt.server.data.entity.UserSettingEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.AccountRecoveryRepo;
import dmt.server.data.repository.AccountRepo;
import dmt.server.data.repository.UserRepo;
import dmt.server.data.repository.UserSettingsRepo;
import dmt.server.service.AccountService;
import dmt.server.service.dto.AccountDTO;
import dmt.server.service.dto.LoginDTO;
import dmt.server.service.dto.RegistrationDTO;
import dmt.server.service.dto.SessionDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.DbException;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dmt.server.enums.ErrorType.*;

/**
 * @author Marco Romagnolo
 */
@Service("accountService")
public class AccountServiceImpl extends AbstractService implements AccountService {

    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    @Autowired
    private AccountRepo<UserAccountEntity> accountRepo;

    @Autowired
    private UserSettingsRepo<UserSettingEntity> settingsRepo;

    @Autowired
    private UserRepo<UserEntity> userRepo;

    @Autowired
    private AccountRecoveryRepo<UserAccountRecoveryEntity> recoveryRepo;

    private static final Random RANDOM = new SecureRandom();

    @Autowired
    private Mail mail;

    @Autowired
    private ResourceBundleMessageSource i18n;

    @Value("${service.login.username.maxLenght}")
    private int usernameMaxLenght;

    @Value("${service.login.username.minLenght}")
    private int usernameMinLenght;

    @Value("${service.login.username.pattern}")
    private String usernamePattern;

    @Value("${service.login.password.maxLenght}")
    private int passwordMaxLenght;

    @Value("${service.login.password.minLenght}")
    private int passwordMinLenght;

    @Value("${service.login.password.pattern}")
    private String passwordPattern;

    @Value("${service.login.token.maxLenght}")
    private int tokenMaxLenght;

    @Value("${service.recovery.email.maxLenght}")
    private int emailMaxLenght;

    @Value("${service.recovery.interval}")
    private int recoveryInterval;

    @Value("${service.recovery.tempPassword.lenght}")
    private int temporaryPasswordLenght;

    @Value("${service.recovery.tempPassword.chars}")
    private String recoveryPasswordChars;

    @Value("${service.sendEmail}")
    private boolean sendEmail;

    @Value("${url}")
    private String url;

    @Value("${service.confirm.code.lenght}")
    private int confirmCodeLenght;

    @Value("${service.confirm.code.chars}")
    private String confirmCodeCars;

    @Override
    public LoginDTO login(String username, String password, boolean expiry, String remoteAddress, String headers) throws InternalException, GenericException {
        try {
            if (username == null) throw new GenericException(ERROR_USERNAME_NOTNULL);
            if (password == null) throw new GenericException(ERROR_PASSWORD_NOTNULL);
            UserAccountEntity account = accountRepo.findByEmail(username);
            if (account == null) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            if (account.getConfirmDate() == null) {
                throw new GenericException(ERROR_ACCOUNT_EMAIL_NOTCONFIRMED);
            }
            //Account locked out
            if (account.getUser().isBlocked()) {
                throw new GenericException(ERROR_ACCOUNT_LOCKED);
            }
            if (!hash(password).equals(account.getPassword())) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            SessionDTO sessionDTO = createSession(account.getUser(), expiry, remoteAddress, headers);
            return new LoginDTO(sessionDTO, new AccountDTO(account, account.getUser()));
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        }
    }

    @Override
    public void logout(String username, String token, String remoteAddress, String headers) throws AuthorizationException, InternalException, GenericException {
        try {
            txOpen();
            UserAccountEntity account = accountRepo.findByUsername(username);
            destroySession(account.getUser(), token, remoteAddress, headers);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void recovery(String email) throws InternalException, GenericException {
        try {
            txOpen();
            if (email == null) {
                throw new GenericException(ERROR_EMAIL_NOTNULL);
            }
            if (email.isEmpty()) {
                throw new GenericException(ERROR_EMAIL_REQUIRED);
            }
            if (email.length() > emailMaxLenght) {
                throw new GenericException(ERROR_EMAIL_MAXLENGHT);
            }
            UserAccountEntity user = accountRepo.findByEmail(email);
            if (user == null) {
                throw new GenericException(ERROR_ACCOUNT_EMAIL_NOTEXISTS);
            }
            UserAccountRecoveryEntity recovery = user.getRecovery();
            if (recovery.getId() != null) {
                throw new GenericException(ERROR_ALREADY_RECOVERY_REQUEST);
            }
            //Set Recovery
            Date now = new Date();
            recovery = new UserAccountRecoveryEntity();
            recovery.setValidDate(new Date(now.getTime() + recoveryInterval));
            recovery.setTempPassword(generateTempPassword());
            recovery.setCreateDate(now);
            UserAccountRecoveryEntity userAccountRecoveryEntity = recoveryRepo.insert(recovery);
            user.setRecoveryId(userAccountRecoveryEntity.getId());
            accountRepo.save(user);
            //Send Mail
            if (sendEmail) {

                String subject = i18n.getMessage("smtp.mail.recovery.subject", null, new Locale(user.getUser().getSetting().getLocale()));
                String tempPassword = recovery.getTempPassword();
                String urlReset = url + "/reset";
                String body = i18n.getMessage("smtp.mail.recovery.body",
                        new Object[]{tempPassword, urlReset}, new Locale(user.getUser().getSetting().getLocale()));
                try {
                    mail.send(user.getEmail(), subject, body);
                } catch (MailException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    throw new GenericException(ERROR_SEND_MAIL_ERROR);
                }
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void reset(String username, String tempPassword, String password, String remoteAddr, String headers) throws InternalException, GenericException {
        try {
            txOpen();
            if (username == null) throw new GenericException(ERROR_USERNAME_NOTNULL);
            if (password == null) throw new GenericException(ERROR_PASSWORD_NOTNULL);
            if (tempPassword == null) throw new GenericException(ERROR_TEMPPASSWORD_NOTNULL);
            // Username check
            Pattern pattern = Pattern.compile(usernamePattern);
            Matcher matcher = pattern.matcher(username);
            if (!matcher.matches()) {
                throw new GenericException(ERROR_USERNAME_REGEX);
            }
            if (username.length() > usernameMaxLenght) {
                throw new GenericException(ERROR_USERNAME_MAXLENGHT);
            }
            // Password check
            pattern = Pattern.compile(passwordPattern);
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                throw new GenericException(ERROR_PASSWORD_REGEX);
            }
            if (password.length() > passwordMaxLenght) {
                throw new GenericException(ERROR_PASSWORD_MAXLENGHT);
            }
            // Temporary Password check
            pattern = Pattern.compile(passwordPattern);
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                throw new GenericException(ERROR_TEMPPASSWORD_REGEX);
            }
            if (tempPassword.length() > temporaryPasswordLenght) {
                throw new GenericException(ERROR_TEMPPASSWORD_MAXLENGHT);
            }
            //Find By Username
            UserAccountEntity account = accountRepo.findByUsername(username);
            if (account == null) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            //Account locked out
            if (account.getUser().isBlocked()) {
                throw new GenericException(ERROR_ACCOUNT_LOCKED);
            }
            if (account.getRecovery() == null) {
                throw new GenericException(ERROR_NO_RECOVERY_REQUEST);
            }
            if (account.getRecovery().getValidDate().before(new Date())) {
                account.setRecoveryId(null);
                accountRepo.save(account);
                throw new GenericException(ERROR_TEMPPASSWORD_EXPIRED);
            }
            if (!account.getRecovery().getTempPassword().equals(tempPassword)) {
                throw new GenericException(ERROR_USERNAME_PASSWORD_INCORRECT);
            }
            account.setRecoveryId(null);
            account.setPassword(hash(password));
            accountRepo.save(account);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public RegistrationDTO register(Locale locale, String firstName, String lastName, String email, String username, String password) throws InternalException, GenericException {
        logger.log(Level.FINE, "locale:" + locale + ", firstName:" + firstName + ", lastName:" + lastName + ", email:"
                + email + ", username:" + username + ", password:" + password);
        try {
            txOpen();
            if (firstName == null) {
                throw new GenericException(ERROR_FIRSTNAME_NOTNULL);
            }
            if (lastName == null) {
                throw new GenericException(ERROR_LASTNAME_NOTNULL);
            }
            if (email == null) {
                throw new GenericException(ERROR_EMAIL_NOTNULL);
            }
            if (username == null) {
                throw new GenericException(ERROR_USERNAME_NOTNULL);
            }
            if (password == null) {
                throw new GenericException(ERROR_PASSWORD_NOTNULL);
            }
            if (accountRepo.findByEmail(email) != null) {
                throw new GenericException(ERROR_EMAIL_EXISTS);
            }
            if (accountRepo.findByUsername(username) != null) {
                throw new GenericException(ERROR_USERNAME_EXISTS);
            }

            // Insert Settings
            UserSettingEntity settingEntity = new UserSettingEntity();
            if(!locale.getCountry().isEmpty()){
                settingEntity.setLocale(locale.getCountry());
            }else{
                settingEntity.setLocale(new Locale("en","US").getCountry());
            }
            settingEntity.setCreateDate(new Date());
            settingEntity.setModifyDate(settingEntity.getCreateDate());
            settingsRepo.insert(settingEntity);

            //Insert User
            UserEntity userEntity = new UserEntity();
            userEntity.setBlocked(false);
            userEntity.setSettingId(settingEntity.getId());
            userEntity.setCreateDate(new Date());
            userRepo.insert(userEntity);

            // Insert Account
            UserAccountEntity accountEntity = new UserAccountEntity();
            accountEntity.setUserId(userEntity.getId());
            accountEntity.setRecoveryId(null);
            accountEntity.setFirstName(firstName);
            accountEntity.setLastName(lastName);
            accountEntity.setEmail(email);
            accountEntity.setUsername(username);
            accountEntity.setPassword(hash(password));
            accountEntity.setConfirmCode(generateConfirmCode());
            accountEntity.setConfirmDate(new Date());
            accountEntity.setCreateDate(new Date());
            UserAccountEntity accountWrap = accountRepo.insert(accountEntity);

            // Send Confirmation email
            if (sendEmail) {
                // Send Email
                String subject = i18n.getMessage("smtp.mail.confirm.subject", null, locale);
                String body = i18n.getMessage("smtp.mail.confirm.body",
                        new Object[]{accountEntity.getFirstName(), accountEntity.getConfirmCode(), accountEntity.getEmail(), url}, locale);
                mail.send(accountEntity.getEmail(), subject, body);
            }
            return new RegistrationDTO(accountEntity, accountWrap.getUser());
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void confirm(String email, String code) throws GenericException, InternalException {
        try {
            txOpen();
            if (email == null) {
                throw new GenericException(ERROR_EMAIL_NOTNULL);
            }
            if (code == null) {
                throw new GenericException(ERROR_CODE_NOTNULL);
            }
            UserAccountEntity user = accountRepo.findByEmail(email);
            if (user == null) {
                throw new GenericException(ERROR_ACCOUNT_NOTEXISTS);
            }
            if (code.equals(user.getConfirmCode())) {
                user.setConfirmDate(new Date());
                accountRepo.update(user);
            } else {
                throw new GenericException(ERROR_ACCOUNT_NOTCONFIRMED);
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void verifyEmail(String email) throws InternalException, GenericException {
        try {
            txOpen();
            if (accountRepo.findByEmail(email) != null) {
                throw new GenericException(ERROR_EMAIL_EXISTS);
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void verifyUsername(String username) throws InternalException, GenericException {
        try {
            txOpen();
            if (accountRepo.findByUsername(username) != null) {
                throw new GenericException(ERROR_USERNAME_EXISTS);
            }
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    private String generateConfirmCode() {
        String code = "";
        for (int i=0; i< confirmCodeLenght; i++)
        {
            int index = (int)(RANDOM.nextDouble()*confirmCodeCars.length());
            code += confirmCodeCars.substring(index, index + 1);
        }
        return code;
    }

    private String generateTempPassword() {
        String password = "";
        for (int i=0; i< temporaryPasswordLenght; i++)
        {
            int index = (int)(RANDOM.nextDouble()*recoveryPasswordChars.length());
            password += recoveryPasswordChars.substring(index, index + 1);
        }
        return password;
    }

    private String hash(String password) throws GenericException{
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new GenericException(ERROR_PASSWORD_HASH);
        }
    }

}
