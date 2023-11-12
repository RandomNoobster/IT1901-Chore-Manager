package core.data;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * The Password class includes logic for handling passwords and determining if they are legal.
 */
public class Password {

    private String password = "";
    private static final Integer legalLength = 8;

    private static final PasswordValidator validator = new PasswordValidatorBuilder().notNull()
            .hasLength(legalLength).hasUppercase().hasLowercase().build();

    /**
     * Constructs a new Password object with the given password string.
     *
     * @param password The password string to use.
     * @throws IllegalArgumentException If the password string is null.
     */
    public Password(String password) {
        this(password, false);
    }

    /**
     * Constructs a new Password object with a default password string.
     */
    public Password() {
        this("1234Password213", false);
    }

    /**
     * Constructs a new Password object with the given password string.
     *
     * @param password      The password string to use.
     * @param alreadyHashed Whether the password is already hashed or not.
     * @throws IllegalArgumentException If the password string is invalid.
     */
    public Password(String password, boolean alreadyHashed) {
        if (!isValid(password))
            throw new IllegalArgumentException(
                    "Password is not valid. Please check if the password is valid before creating a new Password object.");

        if (!alreadyHashed) {
            this.password = this.hashMD5(password);
        }
    }

    public String getPasswordString() {
        return this.password;
    }

    private String hashMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, messageDigest);

            // Convert to hex value
            String hashPassword = number.toString(16);

            // Enforce hash to be 32 characters long
            int additionalZeroes = 32 - hashPassword.length();
            hashPassword = "0".repeat(additionalZeroes) + hashPassword;

            // Return hashed hex password
            return hashPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines if the given password follows contraints.
     *
     * @param password The password to check.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValid(String password) {
        return validator.validate(password);
    }

    /**
     * Returns a string containing the requirements for a legal password.
     *
     * @return A string containing the requirements for a legal password.
     */
    public static String getRequirements(String password) {
        return validator.getRequirements(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Password) {
            Password other = (Password) obj;
            return this.getPasswordString().equals(other.getPasswordString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPasswordString());
    }
}
