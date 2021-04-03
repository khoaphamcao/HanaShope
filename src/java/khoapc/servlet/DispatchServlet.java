/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
public class DispatchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DispatchServlet.class);

    private final String SEARCH_PAGE = "search.jsp";
    private final String LOGIN_PAGE = "login.html";
    private final String CREATE_PAGE = "create_food.jsp";
    private final String UPDATE_PAGE = "update_food.jsp";
    private final String VIEW_CART_PAGE = "viewcart.jsp";
    private final String ADMIN_DEFAULT_PAGE = "admin_default_search.jsp";
    

    private final String LOGIN_SERVLET = "LoginServlet";
    private final String SEARCH_SERVLET = "SearchServlet";
    private final String LOGOUT_SERVLET = "LogoutServlet";
    private final String DELETE_SERVLET = "DeleteServlet";
    private final String UPDATE_SERVLET = "UpdateServlet";
    private final String CREATE_SERVLET = "CreateServlet";
    private final String DEFAULT_SERVLET = "DefaultSearchServlet";
    private final String ADD_TO_CART_SERVLET = "AddToCartServlet";
    private final String REMOVE_FROM_CART_SERVLET = "RemoveFromCartServlet";
    private final String PURCHASE_ITEMS_SERVLET = "PurchaseServlet";
    private final String HISTORY_SERVLET = "HistoryServlet";
    private final String UPDATE_CART_SERVLET = "UpdateCartServlet";
    private final String SEARCH_HISTORY_SERVLET = "SearchHistoryServlet";
    private final String PAYPAL_SERVLET = "PayPalServlet";
    

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
        String button = request.getParameter("btAction");
        String url = SEARCH_PAGE;
        try {
            if (button == null) {
                url = DEFAULT_SERVLET;
            } else if ("Submit".equals(button)) {
                url = LOGIN_SERVLET;
            } else if ("Login".equals(button)) {
                url = LOGIN_PAGE;
            } else if ("Search".equals(button)) {
                url = SEARCH_SERVLET;
            } else if ("Log Out".equals(button)) {
                url = LOGOUT_SERVLET;
            } else if ("Delete".equals(button)) {
                url = DELETE_SERVLET;
            } else if ("Update".equals(button)) {
                url = UPDATE_PAGE;
            } else if ("Update Food".equals(button)) {
                url = UPDATE_SERVLET;
            } else if ("Create Food".equals(button)) {
                url = CREATE_PAGE;
            } else if ("Create".equals(button)) {
                url = CREATE_SERVLET;
            } else if ("Home Page".equals(button)) {
                url = ADMIN_DEFAULT_PAGE;
            } else if ("Confirm".equals(button)) {
                url = DELETE_SERVLET;
            } else if ("Add To Cart".equals(button)) {
                url = ADD_TO_CART_SERVLET;
            } else if ("View Your Cart".equals(button)) {
                url = VIEW_CART_PAGE;
            } else if ("Remove Items".equals(button)) {
                url = REMOVE_FROM_CART_SERVLET;
            } else if ("Purchase".equals(button)) {
                url = PURCHASE_ITEMS_SERVLET;
            } else if ("View History".equals(button)) {
                url = HISTORY_SERVLET;
            } else if ("Update Quantity".equals(button)){
                url = UPDATE_CART_SERVLET;
            } else if ("Search in history".equals(button)){
                url = SEARCH_HISTORY_SERVLET;
            } else if ("PayPal".equals(button)){
                url = PAYPAL_SERVLET;
            }
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
