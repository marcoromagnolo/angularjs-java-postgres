package dmt.server.enums;

/**
 * @author Marco Romagnolo
 */
public enum ErrorType {

    ERROR_GENERIC("Generic error"),
    ERROR_USERID_NUMBER_FORMAT("UserId number format error"),
    ERROR_ALREADY_OPEN_SESSION("You already have an open session"),
    ERROR_USERNAME_NOTNULL("Username cannot be null"),
    ERROR_PASSWORD_NOTNULL("Password cannot be null"),
    ERROR_USERNAME_PASSWORD_INCORRECT("Username and password doesn't match"),
    ERROR_ACCOUNT_EMAIL_NOTCONFIRMED("User doesn't confirmed email address"),
    ERROR_ACCOUNT_LOCKED("Account is locked out"),
    ERROR_EMAIL_NOTNULL("Email cannot be null"),
    ERROR_EMAIL_REQUIRED("Email is required"),
    ERROR_EMAIL_MAXLENGHT("Email exceeds maximum lenght"),
    ERROR_ACCOUNT_EMAIL_NOTEXISTS("No email account registered"),
    ERROR_ALREADY_RECOVERY_REQUEST("Was already made a recovery request"),
    ERROR_TEMPPASSWORD_NOTNULL("Temporary password cannot be null"),
    ERROR_NO_RECOVERY_REQUEST("There are no recovery request for this account"),
    ERROR_TEMPPASSWORD_EXPIRED("Temporary password is expired, please make a new recovery request"),
    ERROR_FIRSTNAME_NOTNULL("First name cannot be null"),
    ERROR_LASTNAME_NOTNULL("Last name cannot be null"),
    ERROR_EMAIL_EXISTS("This email already exists"),
    ERROR_USERNAME_EXISTS("This username already exists"),
    ERROR_CODE_NOTNULL("Code cannot be null"),
    ERROR_ACCOUNT_NOTEXISTS("User has no account"),
    ERROR_PASSWORD_HASH("Error in password hashing"),
    ERROR_FACEBOOK_ERROR("Facebook error while retrieve data"),
    ERROR_SEND_MAIL_ERROR("Send mail error"),
    ERROR_UNSUPPORTED_ENCODING("Unsupported encoding"),
    ERROR_TWITTER_ERROR("Twitter error while retrieve data"),
    ERROR_USERNAME_REGEX("Username pattern doesn't match with regular expression"),
    ERROR_PASSWORD_REGEX("Password pattern doesn't match with regular expression"),
    ERROR_TEMPPASSWORD_REGEX("Temp Password pattern doesn't match with regular expression"),
    ERROR_TEMPPASSWORD_MAXLENGHT("Temporary Password exceeds maximum lenght "),
    ERROR_PASSWORD_MAXLENGHT("Password exceeds maximum length"),
    ERROR_USERNAME_MAXLENGHT("Username exceeds maximum length"),
    ERROR_REDIRECT("Error while redirect"),
    ERROR_AUTHORIZATION("Authorization error"),
    ERROR_SESSION_EXPIRED("Session expired"),
    ERROR_INVALID_SESSION("Invalid session identifier"),
    ERROR_USER_INVALID_SESSION("Cannot identify valid user session token"),
    ERROR_ACCOUNT_NOTCONFIRMED("User registration not confirmed"),
    ERROR_DATABASE("Error with database server"),
    ERROR_NETWORK("Network Error"),
    ERROR_REGISTER("Cannot complete registration");

    ErrorType(String message) {
        this.message = message;
    }

    String message;

    public String getMessage() {
        return message;
    }
}
