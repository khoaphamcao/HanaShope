/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import khoapc.food.FoodDTO;
import khoapc.ultilties.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class OrderDAO implements Serializable {

    public boolean createPurchaseOrder(OrderDTO dto, Map<String, FoodDTO> items) throws NamingException, SQLException {
        boolean check = true;
        Connection conn = null;
        PreparedStatement preStm = null;

        Date date = new Date();

        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "INSERT INTO tbl_Order(orderID,userID,total,date) "
                        + "VALUES (?,?,?,?) ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getOrderID());
                preStm.setString(2, dto.getUserID());
                preStm.setFloat(3, dto.getTotal());
                preStm.setDate(4, new java.sql.Date(date.getTime()));
                preStm.executeUpdate();
                for (String it : items.keySet()) {
                    sql = "INSERT INTO tbl_OrderDetail(foodID,orderID,price,quantity) "
                            + "VALUES (?,?,?,?)";
                    preStm = conn.prepareStatement(sql);
                    preStm.setString(1, it);
                    preStm.setString(2, dto.getOrderID());
                    preStm.setFloat(3, items.get(it).getPrice());
                    preStm.setInt(4, items.get(it).getQuantity());
                    preStm.executeUpdate();
                    sql = "UPDATE tbl_Food "
                            + "SET quantity = quantity - ? "
                            + "WHERE foodID = ? ";
                    preStm = conn.prepareStatement(sql);
                    preStm.setInt(1, items.get(it).getQuantity());
                    preStm.setString(2, it);
                    preStm.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            check = false;
            conn.rollback();
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    // Count to set orderID
    public int countUserID(String userID) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(userID) "
                        + "FROM tbl_Order  "
                        + "WHERE userID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, userID);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int result = rs.getInt(1);
                    return result;
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
        return 0;
    }

    //Get List Order
    public List<OrderDTO> getOrder(String username) throws NamingException, SQLException {
        List<OrderDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT orderID, total, date "
                        + "FROM tbl_Order "
                        + "WHERE userID= ?  "
                        + "ORDER BY date DESC";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, username);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String orderId = rs.getString("orderID");
                    float total = rs.getFloat("total");
                    Date date = rs.getDate("date");
                    OrderDTO dto = new OrderDTO(orderId, username, total, date);
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    list.add(dto);
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
        return list;
    }
    
    //GET ORDER by DATE
     public List<OrderDTO> getHistoryByDate(String username, Date date) throws NamingException, SQLException {
        List<OrderDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT orderID,total,date " +
                                "FROM tbl_Order " +
                                "WHERE userID = ? AND date= ?   ORDER BY date DESC ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, username);
                preStm.setDate(2, new java.sql.Date(date.getTime()));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String orderId = rs.getString("orderID");
                    float total = rs.getFloat("total");
                    Date historyDate = rs.getDate("date");
                    OrderDTO dto = new OrderDTO(orderId, username, total, historyDate);
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    list.add(dto);
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
        return list;
    }
     
     
    public List<OrderDTO> getHistoryByFoodName(String username, String foodname) throws NamingException, SQLException {
        List<OrderDTO> list = null;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT o.orderID,o.total,o.date " +
                            "  FROM tbl_Order o JOIN tbl_OrderDetail od ON o.orderID = od.orderID " +
                            "  	 AND  o.orderID = od.orderID " +
                            "  JOIN tbl_Food f ON od.foodID = f.foodID " +
                            "  WHERE o.userID = ? "+
                            "  AND f.foodName LIKE ? " + 
                            " ORDER BY date DESC ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, username);
                preStm.setString(2,"%" + foodname + "%");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String orderId = rs.getString("orderID");
                    float total = rs.getFloat("total");
                    Date date = rs.getDate("date");
                    OrderDTO dto = new OrderDTO(orderId, username, total, date);
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    list.add(dto);
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
        return list;
    } 
}
