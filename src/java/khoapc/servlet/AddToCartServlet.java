/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.cart.CartObjectDTO;
import khoapc.user.UserDTO;
import khoapc.food.FoodDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(AddToCartServlet.class);
    private final String SHOPPING_PAGE = "DefaultSearchServlet";
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
        String foodID = request.getParameter("foodID").trim();
        String foodName = request.getParameter("foodName").trim();
        String price = request.getParameter("price").trim();
        String category = request.getParameter("category").trim();
        String quantity = request.getParameter("quantity").trim();
        
        String url = SHOPPING_PAGE;
        
        String searchValue = request.getParameter("lastSearchValue");
        String option = request.getParameter("lastSearchOption");
        String priceFrom = request.getParameter("lastSearchPriceFrom");
        String priceTo = request.getParameter("lastSearchPriceTo");
        String index = request.getParameter("index");
        
        

        
        try {
            HttpSession session = request.getSession(true);
            UserDTO account = (UserDTO) session.getAttribute("ACCOUNT");
            if(account != null){
                CartObjectDTO cart = (CartObjectDTO) session.getAttribute("CART");
                if(cart == null){
                    cart = new CartObjectDTO();
                }
                    float priceF = Float.parseFloat(price);
                    int quantityI = Integer.parseInt(quantity);
                    FoodDTO dto = new FoodDTO(foodID, foodName, priceF, category, quantityI);
                    cart.addFoodToCart(dto);
                    session.setAttribute("CART", cart);
                    if(searchValue.trim().length() > 0 || priceFrom.trim().length() > 0 && priceTo.trim().length() > 0){
                        url = "DispatchServlet?"
                                + "btAction=Search"
                                + "&txtSearch=" + searchValue
                                + "&searchOption=" + option
                                + "&txtPriceFrom=" + priceFrom
                                + "&txtPriceTo=" + priceTo 
                                + "&index" + index;
                    }else{
                        url = "DefaultSearchServlet?index=" + index;
                    }
            }
        } catch (Exception e){
            LOGGER.error(e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
