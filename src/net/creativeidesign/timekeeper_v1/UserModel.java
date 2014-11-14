/*
 * User Model for Time Keeper
 * This class ment to hold all the user related
 * values and the settings 
 */
package net.creativeidesign.timekeeper_v1;

/**
 *
 * @author Pi
 */
public class UserModel extends SettingsModel{
    private String username;
    private String email;
    private String full_name;
    private String password;
    private int userId;

    public UserModel() {
        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
