package core.Data;

public class Password {

    String password;

    public Password() {
        this.password = "Password";
    }

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        return o.equals(this.getPassword());
    }

}
