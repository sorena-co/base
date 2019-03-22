package ir.sp.base.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String INSTITUTION = "ROLE_INSTITUTION";

    public static final String TEACHER = "ROLE_TEACHER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
