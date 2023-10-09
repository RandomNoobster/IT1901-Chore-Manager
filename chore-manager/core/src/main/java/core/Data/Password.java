package core.Data;

public class Password {

    private String password;
    private final Integer legalLength = 8;

    private boolean containsInteger = false;
    private boolean containsUppercase = false;
    private boolean containsLowerCase = false;
    private boolean isLegalLength = false;

    public Password(String password) {
        this.password = password;
        this.updateFlags();
    }

    public Password() {
        this.password = "1234Password213";
        this.updateFlags();
    }

    public boolean isLegal() {
        return this.containsInteger && this.containsUppercase && this.containsLowerCase
                && this.isLegalLength;
    }

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

}
