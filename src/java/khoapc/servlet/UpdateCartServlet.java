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
import khoapc.food.FoodDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateCartServlet.class);
    private final String VIEW_CART_PAGE = "viewcart.jsp";
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
        String txtQuantity = request.getParameter("txtQuantity");
        String foodID = request.getParameter("txtfoodID").trim();
        
        String url = VIEW_CART_PAGE;
        try  {
            HttpSession session = request.getSession(false);
            if(session != null){
                CartObjectDTO cart = (CartObjectDTO) session.getAttribute("CART");
                if(cart != null){
                    FoodDTO foodDTO = new FoodDTO();
                    int quantity = Integer.parseInt(txtQuantity);
                    for (FoodDTO food : cart.getItems().values()) {
                        if(food.getFoodID().equals(foodID)){
                            foodDTO = new FoodDTO(foodID, food.getFoodName(), food.getImage() , food.getDescription(), food.getPrice(), food.getCreateDate(), food.getCategory(), true, quantity);
                        }
                    }
                    cart.updateCart(foodDTO);
                    session.setAttribute("CART", cart);
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
