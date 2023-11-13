package core.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A builder for creating {@link PasswordValidator} instances.
 */
public class PasswordValidatorBuilder {

    private Map<Function<String, Boolean>, String> validators = new LinkedHashMap<>();

    public PasswordValidatorBuilder() {
    }

    /**
     * Adds a validator that checks if the password has a length greater than or equal to the
     * specified length.
     *
     * @param length the minimum length of the password
     * @return the PasswordValidatorBuilder instance
     */
    public PasswordValidatorBuilder hasLength(int length) {
        this.validators.put(password -> password.length() >= length,
                "- Be at least " + length + " characters long.");
        return this;
    }

    /**
     * Adds a validator that checks if the password contains an uppercase character.
     *
     * @return the PasswordValidatorBuilder instance
     */
    public PasswordValidatorBuilder hasUppercase() {
        this.validators.put(password -> password.matches(".*[A-Z].*"),
                "- Contain a capitalised character.");
        return this;
    }

    /**
     * Adds a validator that checks if the password contains a lowercase character.
     *
     * @return the PasswordValidatorBuilder instance
     */
    public PasswordValidatorBuilder hasLowercase() {
        this.validators.put(password -> password.matches(".*[a-z].*"),
                "- Contain a lowercase character.");
        return this;
    }

    /**
     * Adds a validator that checks if the password contains a number.
     *
     * @return the PasswordValidatorBuilder instance
     */
    public PasswordValidatorBuilder hasDigit() {
        this.validators.put(password -> password.matches(".*\\d.*"), "- Contain a digit.");
        return this;
    }

    public PasswordValidator build() {
        return new PasswordValidator(this.validators);
    }

}
