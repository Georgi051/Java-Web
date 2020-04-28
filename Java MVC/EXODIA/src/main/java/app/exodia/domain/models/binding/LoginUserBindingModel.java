package app.exodia.domain.models.binding;

public class LoginUserBindingModel {
    private String username;
    private String password;

    public LoginUserBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
