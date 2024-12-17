package utc2.itk62.e_reader.constant;

public class MessageCode {
    public static final String ERROR_INTERNAL_SERVER = "error.internal_server";
    public static final String ERROR_ARGUMENTS_INVALID = "error.arguments_invalid";
    public static final String ERROR_UNAUTHORIZED = "error.unauthorized";

    public static final String LOGIN_SUCCESS = "login.success";
    public static final String REGISTER_SUCCESS = "register.success";

    public static final String USER_EMAIL_EXISTS = "user.email.exists";
    public static final String USER_NOT_VERIFIED = "user.not_verified";
    public static final String USER_CREDENTIALS_INVALID = "user.credentials.invalid";
    public static final String USER_EMAIL_NOT_EXISTS = "user.email.not_exists";
    public static final String USER_ALREADY_VERIFIED = "user.already_verified";
    public static final String USER_ID_NOT_FOUND = "user.id.not_found";
    public static final String CHANGE_PASSWORD_INCORRECT = "change.password.incorrect";
    public static final String CHANGE_PASSWORD_SUCCESS = "change.password.success";

    public static final String EMAIL_VERIFICATION_CODE_EXPIRED = "email_verification.code.expired";
    public static final String EMAIL_VERIFICATION_STATUS_USED = "email_verification.status.used";
    public static final String EMAIL_VERIFICATION_CODE_INVALID = "email_verification.code.invalid";
    public static final String EMAIL_VERIFICATION_SUCCESS = "email_verification.success";

    public static final String RESET_PASSWORD_REQUEST_SUCCESS = "reset_password.request.success";
    public static final String RESET_PASSWORD_EXPIRED = "reset_password.expired";
    public static final String RESET_PASSWORD_SUCCESS = "reset_password.success";
    public static final String RESET_PASSWORD_INCORRECT = "reset_password.incorrect";
    public static final String RESET_PASSWORD_ALREADY_COMPLETED = "reset_password.already_completed";

    public static final String BOOK_ID_NOT_FOUND = "book.id.not_found";
    public static final String ROLE_ID_NOT_FOUND = "role.id.not_found";

    public static final String INVALID_ROLE_NAME = "role.name.invalid";
    public static final String AUTHOR_ID_NOT_FOUND = "author.id.not_found";
    public static final String AUTHOR_BOOK_ID_NOT_FOUND = "author.book_id.not_found";

    public static final String PERMISSION_ID_NOT_FOUND = "permission.id.not_found";
    public static final String ROLE_PERMISSION_EXISTS = "role_permission_exists";
}
