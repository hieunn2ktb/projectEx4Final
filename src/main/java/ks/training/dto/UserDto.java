package ks.training.dto;

public class UserDto {
    private int id;
    private String username;
    private String email;
    private String role;

    public UserDto() {
    }

    public UserDto(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
