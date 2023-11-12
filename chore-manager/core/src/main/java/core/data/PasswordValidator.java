package core.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A utility class for validating passwords.
 */
public class PasswordValidator {

    private Map<Function<String, Boolean>, String> validators = new LinkedHashMap<>();

    public PasswordValidator(Map<Function<String, Boolean>, String> validators) {
        this.validators.putAll(validators);
    }

    /**
     * Validates a password using the registered validators.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    public boolean validate(String password) {
        if (password == null)
            return false;

        for (Function<String, Boolean> validator : this.validators.keySet()) {
            if (!validator.apply(password))
                return false;
        }
        return true;
    }

    /**
     * Returns the requirements for a valid password.
     *
     * @param password the password to check requirements for
     * @return a string containing the requirements for a valid password
     */
    public String getRequirements(String password) {
        if (this.validate(password))
            return "";

        StringBuilder sb = new StringBuilder("Password must:\n");

        if (password == null) {
            sb.append("- Not be null.");
            return sb.toString();
        }

        for (Map.Entry<Function<String, Boolean>, String> entry : this.validators.entrySet()) {
            if (!entry.getKey().apply(password))
                sb.append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }

}
