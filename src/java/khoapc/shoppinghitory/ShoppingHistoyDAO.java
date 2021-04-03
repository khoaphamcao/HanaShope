/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.shoppinghitory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import khoapc.order.OrderDTO;
import khoapc.food.FoodDTO;
import khoapc.ultilties.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class ShoppingHistoyDAO implements Serializable {

    public ShoppingHistoyDTO getFoodHistoryByOrderID(OrderDTO order) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        List<FoodDTO> listFood = null;
        ShoppingHistoyDTO history = new ShoppingHistoyDTO();
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT f.foodName,od.quantity,od.price,o.total "
                        + "FROM tbl_Order o "
                        + "JOIN tbl_OrderDetail od ON o.orderID=od.orderID AND o.orderID = od.orderID "
                        + "JOIN tbl_Food f  ON od.foodID= f.foodID "
                        + "WHERE o.orderID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, order.getOrderID());
                rs = preStm.executeQuery();
                float total = 0;
                while (rs.next()) {
                    String foodName = rs.getString("foodName");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    total = rs.getFloat("total");
                    FoodDTO dto = new FoodDTO(foodName, price, quantity);
                    if (listFood == null) {
                        listFood = new ArrayList<>();
                    }
                    listFood.add(dto);
                }
                history = new ShoppingHistoyDTO(order.getOrderID(), listFood, total);
                return history;
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
        return history;
    }
}
