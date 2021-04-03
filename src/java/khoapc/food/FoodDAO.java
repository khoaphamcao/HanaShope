/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.food;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import khoapc.ultilties.DBHelpers;

/**
 *
 * @author Khoa Pham
 */
public class FoodDAO implements Serializable {

    private List<FoodDTO> foodList;

    public List<FoodDTO> getFoodList() {
        return foodList;
    }

    //0.0: DEFAULT : GET ALL FOOD
    public void getPagingListAllFood(int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity "
                        + "FROM tbl_Food "
                        + "WHERE status = 1 "
                        + "	AND quantity > 0 "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image").trim();
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    //0.1:COUNT ALL FOOD
    public int countAllFood() throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(foodID) "
                        + "FROM tbl_Food  "
                        + "WHERE status = 1 "
                        + "AND quantity > 0 ";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    //ADMIN:GET ALL FOOD ON ADMIN DEFAULT 
    public void getPagingAdminListAllFood(int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity, status "
                        + "FROM tbl_Food "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setInt(1, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, status, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    
    // ADMIN: DEFAULT PAGE COUNT FOOD
    public int countAllAdminFood() throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(foodID) "
                        + "FROM tbl_Food  ";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 1.1.USER:Count ToTal Food by FoodName
    public int countFoodName(String searchValue) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(foodName) "
                        + "FROM tbl_Food  "
                        + "WHERE foodName LIKE ? "
                        + "AND status = 1 "
                        + "AND quantity > 0 ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 1.2.USER.Show ToTal Food by FoodName on page
    public void getPagingListFoodName(String searchFoodName, int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity "
                        + "FROM tbl_Food "
                        + "WHERE foodName LIKE ? "
                        + "	AND status = 1 "
                        + "	AND quantity > 0 "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchFoodName + "%");
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image").trim();
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    // 2.1.USER:Count ToTal Food by FoodPrice
    public int countFoodPrice(float priceFrom, float PriceTo) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(price) "
                        + "FROM tbl_Food  "
                        + "WHERE price BETWEEN ? AND ? "
                        + "AND status = 1 "
                        + "AND quantity > 0";
                preStm = conn.prepareStatement(sql);
                preStm.setFloat(1, priceFrom);
                preStm.setFloat(2, PriceTo);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 2.2.USER: Show ToTal Food by FoodPrice on page
    public void getPagingListFoodPrice(float priceFrom, float priceTo, int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity "
                        + "FROM tbl_Food "
                        + "WHERE price BETWEEN ? AND ? "
                        + "AND status = 1 "
                        + "AND quantity > 0 "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setFloat(1, priceFrom);
                preStm.setFloat(2, priceTo);
                preStm.setInt(3, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    // 3.1.USER:Count ToTal Food by FoodCategory
    public int countFoodCategory(String searchValue) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(category) "
                        + "FROM tbl_Food  "
                        + "WHERE category LIKE ? "
                        + "AND status = 1 "
                        + "AND quantity > 0 ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 3.2.USER:Show ToTal Food by FoodCategory on page
    public void getPagingListFoodCategory(String searchCategory, int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity "
                        + "FROM tbl_Food "
                        + "WHERE category LIKE ? "
                        + "AND status = 1 "
                        + "AND quantity > 0 "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchCategory + "%");
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    //=============ADMIN=============
    // 1.1.ADMIN:Count ToTal Food by FoodName
    public int countAdminFoodName(String searchValue) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(foodName) "
                        + "FROM tbl_Food  "
                        + "WHERE foodName LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 1.2.ADMIN.Show ToTal Food by FoodName on page
    public void getPagingAdminListFoodName(String searchFoodName, int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity, status "
                        + "FROM tbl_Food "
                        + "WHERE foodName LIKE ? "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchFoodName + "%");
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image").trim();
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");

                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, status, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    // 2.1.ADMIN:Count ToTal Food by FoodPrice
    public int countAdminFoodPrice(float priceFrom, float PriceTo) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(price) "
                        + "FROM tbl_Food  "
                        + "WHERE price BETWEEN ? AND ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setFloat(1, priceFrom);
                preStm.setFloat(2, PriceTo);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 2.2.ADMIN: Show ToTal Food by FoodPrice on page
    public void getPagingListAdminFoodPrice(float priceFrom, float priceTo, int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity, status "
                        + "FROM tbl_Food "
                        + "WHERE price BETWEEN ? AND ? "
                        + "ORDER BY createDate "
                        + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
                preStm = conn.prepareStatement(sql);
                preStm.setFloat(1, priceFrom);
                preStm.setFloat(2, priceTo);
                preStm.setInt(3, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, status, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    // 3.1.ADMIN:Count ToTal Food by FoodCategory
    public int countAdminFoodCategory(String searchValue) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(category) "
                        + "FROM tbl_Food  "
                        + "WHERE category LIKE ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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

    // 3.2.ADMIN:Show ToTal Food by FoodCategory on page
    public void getPagingListAdminFoodCategory(String searchCategory, int index) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT foodID, foodName, image, description, price, createDate, category, quantity, status " +
                            "FROM tbl_Food " +
                            "WHERE category LIKE ? " +
                            "ORDER BY createDate " +
                            "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, "%" + searchCategory + "%");
                preStm.setInt(2, (index * 3 - 3));
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    FoodDTO dto = new FoodDTO(foodID, foodName, image, description, price, createDate, category, status, quantity);
                    if (this.foodList == null) {
                        this.foodList = new ArrayList<>();
                    }
                    this.foodList.add(dto);
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
    }

    //===============================
    // 4.END_PAGE
    public int getEndPage(int countFood) {
        int endPage = 0;
        int pageSize = 3;
        endPage = countFood / pageSize;
        if (countFood % pageSize != 0) {
            endPage++;
        }
        return endPage;
    }

    // 5.DELETE_FOOD : Chưa làm confirm
    public boolean deleteFood(String foodID, String username, String action) throws NamingException, SQLException {

        boolean check = true;
        Connection conn = null;
        PreparedStatement preStm = null;

        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS");
        String date = formatDate.format(new Date());

        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "UPDATE tbl_Food "
                        + "SET status = 0  "
                        + "WHERE foodID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, foodID);
                preStm.executeUpdate();

                sql = "INSERT INTO tbl_RecordUpdateFood(foodID,userID,date,action) "
                        + "VALUES (?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, foodID);
                preStm.setString(2, username);
                preStm.setString(3, date);
                preStm.setString(4, action);
                preStm.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
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

    // 6.UPDATE FOOD : 
    public boolean updateFood(FoodDTO dto, String userUpdate, String action) throws NamingException, SQLException {
        boolean check = true;
        Connection conn = null;
        PreparedStatement preStm = null;

        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SS");
        String date = formatDate.format(new Date());
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                String sql = "UPDATE tbl_Food  "
                        + "SET  foodName = ?, description = ?, price = ?, category = ?, quantity = ? , image = ?, status = ? "
                        + "WHERE foodID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getFoodName());
                preStm.setString(2, dto.getDescription());
                preStm.setFloat(3, dto.getPrice());
                preStm.setString(4, dto.getCategory());
                preStm.setInt(5, dto.getQuantity());
                preStm.setString(6, dto.getImage());
                preStm.setBoolean(7, dto.isStatus());
                preStm.setString(8, dto.getFoodID());
                preStm.executeUpdate();

                sql = "INSERT INTO tbl_RecordUpdateFood(foodID,userID,date,action) "
                        + "VALUES (?, ?, ?, ?)";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getFoodID());
                preStm.setString(2, userUpdate);
                preStm.setString(3, date);
                preStm.setString(4, action);
                preStm.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
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

    // 7.CREATE_FOOD : Not complete
    public boolean createFood(FoodDTO dto, String userCreate) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_Food(foodID, foodName, image, description, price, createDate, category, status, quantity)  "
                        + "VALUES  (?,?,?,?,?,?,?,?,?) ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, dto.getFoodID());
                preStm.setString(2, dto.getFoodName());
                preStm.setString(3, dto.getImage());
                preStm.setString(4, dto.getDescription());
                preStm.setFloat(5, dto.getPrice());
                preStm.setDate(6, new java.sql.Date(dto.getCreateDate().getTime()));
                preStm.setString(7, dto.getCategory());
                preStm.setBoolean(8, dto.isStatus());
                preStm.setInt(9, dto.getQuantity());
                int row = preStm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    //7.1 Count Food to Set ID
    public int countFood() throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(foodID) "
                        + "FROM tbl_Food";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt(1);
                    return totalFood;
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
    
    public int getFoodQuantityByID(String foodID) throws SQLException, NamingException{
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            conn = DBHelpers.myConnection();
            if (conn != null) {
                String sql = "SELECT quantity " +
                            "FROM tbl_Food " +
                            "WHERE foodID = ? ";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, foodID);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    int totalFood = rs.getInt("quantity");
                    return totalFood;
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
}
