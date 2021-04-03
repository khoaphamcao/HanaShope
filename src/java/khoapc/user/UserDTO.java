/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.user;

import java.io.Serializable;

/**
 *
 * @author Khoa Pham
 */
public class UserDTO implements Serializable{
    private String userID;
    private String password;
    private String fullname;
    private int isAdmin;
    private String email;

    
    public UserDTO() {
    }
    
    

    public UserDTO(String userID, String password, String fullname, int isAdmin, String email) {
        this.userID = userID;
        this.password = password;
        this.fullname = fullname;
        this.isAdmin = isAdmin;
        this.email = email;
    }

    public UserDTO(String userID, String password, String fullname, int isAdmin) {
        this.userID = userID;
        this.password = password;
        this.fullname = fullname;
        this.isAdmin = isAdmin;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    


}
