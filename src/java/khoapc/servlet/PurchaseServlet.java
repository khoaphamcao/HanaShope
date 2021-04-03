/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.cart.CartObjectDTO;
import khoapc.user.UserDTO;
import khoapc.order.OrderDAO;
import khoapc.order.OrderDTO;
import khoapc.food.FoodDAO;
import khoapc.food.FoodDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "PurchaseServlet", urlPatterns = {"/PurchaseServlet"})
public class PurchaseServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PurchaseServlet.class);

    private final String SHOPPING_VIEW = "viewcart.jsp";
    

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
        //tblOrder_DTO var;
        String totalPrice = request.getParameter("total");
        String url = SHOPPING_VIEW;

        String ERROR = null;
        
        // c
        try {
            boolean check = true;
            HttpSession session = request.getSession(false);
            UserDTO userDTO = (UserDTO) session.getAttribute("ACCOUNT");

            CartObjectDTO cart = (CartObjectDTO) session.getAttribute("CART");
            FoodDAO dao = new FoodDAO();
            if (cart != null) {
                for (FoodDTO food : cart.getItems().values()) {
                    int quantity = dao.getFoodQuantityByID(food.getFoodID());
                    if (quantity < food.getQuantity()) {
                        ERROR = " Food is out of stock";
                        check = false;
                    }
                }
            }
            if (check) {
                String userID = userDTO.getUserID();
                OrderDAO orderDAO = new OrderDAO();
                if (userID != null) {
                    int countUserID = orderDAO.countUserID(userID);
                    String orderID = userID + "-1";
                    if (countUserID != 0) {
                        orderID = userID + "-" + Integer.toString(countUserID + 1);
                    }
                    if (totalPrice != null) {
                        float total = Float.parseFloat(totalPrice);
                        OrderDTO orderDTO = new OrderDTO(orderID, userID, total);
                        Map<String, FoodDTO> items = cart.getItems();
                        if (items != null) {
                            boolean result = orderDAO.createPurchaseOrder(orderDTO, items);
                            if (result) {
                                session.removeAttribute("CART");
                            }
                        }
                        url = SHOPPING_VIEW;
                    }
                }
            }
            request.setAttribute("ERROR", ERROR);
        } catch (Exception ex) {
            LOGGER.error(ex);
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
