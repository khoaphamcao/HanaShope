/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.cart;

import java.util.HashMap;
import java.util.Map;
import khoapc.food.FoodDTO;

/**
 *
 * @author Khoa Pham
 */
public class CartObjectDTO {
    
    private Map<String, FoodDTO> items;
    
    public CartObjectDTO() {
    }
    
    public CartObjectDTO(Map<String, FoodDTO> items) {
        this.items = items;
    }
    
    public Map<String, FoodDTO> getItems() {
        return items;
    }
    
    public void setItems(Map<String, FoodDTO> items) {
        this.items = items;
    }
    
    
    // Lỗi
    public void addFoodToCart(FoodDTO dto) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        if (this.items.containsKey(dto.getFoodID())) {
            int tmp = dto.getQuantity() - items.get(dto.getFoodID()).getQuantity();
            dto.setQuantity(dto.getQuantity() - tmp + 1);
        } else {
            dto.setQuantity(1);
        }
        this.items.put(dto.getFoodID(), dto);
    }
    
    public void removeFoodFromCart(String foodID) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(foodID)) {
            items.remove(foodID);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
    
    public void updateCart(FoodDTO dto){
        if(items != null){
            if(items.containsKey(dto.getFoodID())){
                this.items.replace(dto.getFoodID(), dto);
            }
        }
    }
}
