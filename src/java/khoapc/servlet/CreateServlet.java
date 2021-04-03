/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.user.UserDTO;
import khoapc.food.FoodDAO;
import khoapc.food.FoodDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
public class CreateServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateServlet.class);

    private final String CREATE_FOOD = "create_food.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //Rewriting
        String searchValue = request.getParameter("lastSearchValue");
        String option = request.getParameter("lastSearchOption");
        String priceFrom = request.getParameter("lastSearchPriceFrom");
        String priceTo = request.getParameter("lastSearchPriceTo");
        //DTO tblFood
        String foodName = request.getParameter("txtFoodName").trim();
        String img = request.getParameter("img");
        String description = request.getParameter("txtDescription").trim();
        String price = request.getParameter("txtPrice").trim();
        String category = request.getParameter("txtCategory").trim();
        String quantity = request.getParameter("txtQuantity").trim();

        Date createDate = new Date();
        boolean status = true;

        //url Page
        String url = CREATE_FOOD;

        try {

            String foodId = null;

            FoodDAO dao = new FoodDAO();
            int idNumber = dao.countFood() + 1;
            if (idNumber == 0) {
                foodId = "F0001";
            } else {
                String converId = Integer.toString(idNumber);
                if (converId.length() == 1) {
                    foodId = "F000" + converId;
                } else if (converId.length() == 2) {
                    foodId = "F00" + converId;
                } else if (converId.length() == 3) {
                    foodId = "F0" + converId;
                } else if (converId.length() == 4) {
                    foodId = "F" + converId;
                }
            }
            //DTO
            int quantityI = Integer.parseInt(quantity);
            float priceF = Float.parseFloat(price);
            if (img.length() == 0) {
                img = "default_food.jpg";
            }
            //Create
            FoodDTO dto = new FoodDTO(foodId, foodName, img, description, priceF, createDate, category, status, quantityI);
            HttpSession session = request.getSession();
            UserDTO userDto = (UserDTO) session.getAttribute("ACCOUNT");
            boolean result = dao.createFood(dto, userDto.getUserID());
            if (result) {
                url = "DispatchServlet?"
                        + "btAction=Search"
                        + "&txtSearch=" + searchValue
                        + "&searchOption=" + option
                        + "&txtPriceFrom=" + priceFrom
                        + "&txtPriceTo=" + priceTo;
            } else {
                url = CREATE_FOOD;
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
