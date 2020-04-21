package panda.domain.models.binding;

public class UserRegisterBindingModel {
    private String username;
    private String password;
    private String conformPassword;
    private String email;
    private String role;

    public UserRegisterBindingModel() {
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

    public String getConformPassword() {
        return conformPassword;
    }

    public void setConformPassword(String conformPassword) {
        this.conformPassword = conformPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
