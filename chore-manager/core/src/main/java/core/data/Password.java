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
    private final Integer legalLength = 8;

    private boolean containsInteger = false;
    private boolean containsUppercase = false;
    private boolean containsLowerCase = false;
    private boolean isLegalLength = false;

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
        this.password = "1234Password213";
        this.updateFlags();
        this.password = this.hashMD5(this.password);
    }

    /**
     * Constructs a new Password object with the given password string.
     *
     * @param password      The password string to use.
     * @param alreadyHashed Whether the password is already hashed or not.
     * @throws IllegalArgumentException If the password string is null.
     */
    public Password(String password, boolean alreadyHashed) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }

        this.password = password;
        this.updateFlags();
        if (!alreadyHashed) {
            this.password = this.hashMD5(password);
        }
    }

    public boolean isLegal() {
        return this.containsInteger && this.containsUppercase && this.containsLowerCase
                && this.isLegalLength;
    }

    /**
     * Returns a string containing the requirements for a legal password.
     *
     * @return A string containing the requirements for a legal password.
     */
    public String getFixMsg() {
        String returnString = "Password must:\n";

        if (!this.containsInteger)
            returnString += " - contain an integer.\n";
        if (!this.containsUppercase)
            returnString += " - contain a capitalised character.\n";
        if (!this.containsLowerCase)
            returnString += " - contain a lowercase character.\n";
        if (!this.isLegalLength)
            returnString += " - be at least " + this.legalLength + " characters long. ";

        return returnString;
    }

    public String getPasswordString() {
        return this.password;
    }

    /**
     * Updates the flags according to the password string.
     */
    public void updateFlags() {
        if (this.getPasswordString().length() < this.legalLength) {
            this.isLegalLength = false;
        } else {
            this.isLegalLength = true;
        }

        this.containsInteger = false;
        this.containsUppercase = false;
        this.containsLowerCase = false;

        for (int i = 0; i < this.getPasswordString().length(); i++) {
            char c = this.getPasswordString().charAt(i);
            if (Character.isDigit(c)) {
                this.containsInteger = true;
            } else if (Character.isUpperCase(c)) {
                this.containsUppercase = true;
            } else if (Character.isLowerCase(c)) {
                this.containsLowerCase = true;
            }
        }
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
