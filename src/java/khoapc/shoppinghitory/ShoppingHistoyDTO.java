/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.shoppinghitory;

import java.io.Serializable;
import java.util.List;
import khoapc.food.FoodDTO;

/**
 *
 * @author Khoa Pham
 */
public class ShoppingHistoyDTO implements Serializable{
    private String orderID;
    private List<FoodDTO> foodList;
    private float total;

    public ShoppingHistoyDTO() {
    }

    public ShoppingHistoyDTO(String orderID, List<FoodDTO> foodList, float total) {
        this.orderID = orderID;
        this.foodList = foodList;
        this.total = total;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public List<FoodDTO> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodDTO> foodList) {
        this.foodList = foodList;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
