/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.user.UserDTO;
import khoapc.food.FoodDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteServlet.class);

    private final String AD_SEARCH_PAGE = "search.jsp";

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
        String foodID = request.getParameter("foodID");
        String searchValue = request.getParameter("lastSearchValue");
        String option = request.getParameter("lastSearchOption");
        String priceFrom = request.getParameter("lastSearchPriceFrom");
        String priceTo = request.getParameter("lastSearchPriceTo");

        String action = request.getParameter("btAction");
        String urlRewriting = AD_SEARCH_PAGE;

        try {
            FoodDAO dao = new FoodDAO();
            HttpSession session = request.getSession(false);
            UserDTO dto = (UserDTO) session.getAttribute("ACCOUNT");
            if (dto != null) {
                String userID = dto.getUserID();
                boolean result = dao.deleteFood(foodID, userID, action);
                if (result) {
                    urlRewriting = "DispatchServlet?"
                            + "btAction=Search"
                            + "&txtSearch=" + searchValue
                            + "&searchOption=" + option
                            + "&txtPriceFrom=" + priceFrom
                            + "&txtPriceTo=" + priceTo ;
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            response.sendRedirect(urlRewriting);
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
