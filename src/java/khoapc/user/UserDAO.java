/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.user;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import khoapc.ultilties.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class UserDAO implements Serializable {

    public UserDTO getAccount(String userID, String password) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT userID, password, fullName, isAdmin "
                        + "FROM tbl_User "
                        + "WHERE userID = ? "
                        + "AND password = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, userID);
                preStm.setString(2, password);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("userID");
                    String userPassword = rs.getString("password");
                    String fullName = rs.getString("fullName");
                    int isAdmin = rs.getInt("isAdmin");
                    UserDTO dto = new UserDTO(userID, userPassword, fullName, isAdmin);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public String getNameUser(String userID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT fullName "
                        + "FROM tbl_User "
                        + "WHERE userID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, userID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    return fullName;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public UserDTO checkLoginGG(String userID) throws SQLException, NamingException {
        UserDTO dto = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {

                String sql = "SELECT fullName,isAdmin,gmail "
                        + "FROM tbl_User WHERE "
                        + "userID= ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullname = rs.getString("fullName");
                    int isAdmin = rs.getInt("isAdmin");
                    String gmail = rs.getString("gmail").trim();
                    dto = new UserDTO(userID, "", fullname, isAdmin, gmail);
                    return dto;
                }

            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
        return dto;
    }
    
    public void createUserGG(UserDTO user) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {

                String sql = "INSERT INTO tbl_User(userID,fullName,isAdmin,gmail) "
                        + "VALUES(?,?,?,?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, user.getUserID());
                preStm.setString(2, user.getFullname());
                preStm.setInt(3, user.getIsAdmin());
                preStm.setString(4, user.getEmail());
                preStm.executeUpdate();
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
