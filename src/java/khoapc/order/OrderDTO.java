/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.order;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Khoa Pham
 */
public class OrderDTO implements Serializable{
    private String orderID;
    private String userID;
    private float total;
    private Date date;

    public OrderDTO() {
    }
    

    public OrderDTO(String orderID, String userID, float total, Date date) {
        this.orderID = orderID;
        this.userID = userID;
        this.total = total;
        this.date = date;
    }
    
    public OrderDTO(String orderID, String userID, float total) {
        this.orderID = orderID;
        this.userID = userID;
        this.total = total;
    }
    
     public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
