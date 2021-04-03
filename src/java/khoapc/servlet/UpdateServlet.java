/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
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
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateServlet.class);

    private final String AD_SEARCH_PAGE = "DefaultAdminSearchServlet";

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
        String foodName = request.getParameter("foodName");

        String newImg = request.getParameter("fileImage");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String category = request.getParameter("category");
        String quantity = request.getParameter("quantity");
        String statusString = request.getParameter("status");
        String oldImg = request.getParameter("oldImg");

        String searchValue = request.getParameter("lastSearchValue");
        String option = request.getParameter("lastSearchOption");
        String priceFrom = request.getParameter("lastSearchPriceFrom");
        String priceTo = request.getParameter("lastSearchPriceTo");
        String button = request.getParameter("btAction");
        

        String url = AD_SEARCH_PAGE;
        
        boolean status = statusString.equals("true");

        String image = null;
        if (newImg == null || newImg.isEmpty()) {
            String[] split = oldImg.split("/");
            image = split[1];
        }else{
            image = newImg;
        }
        try {
            //Error
            float priceF = Float.parseFloat(price);
            int quantityI = Integer.parseInt(quantity);

            Date date = new Date();
            if (image != null) {

                FoodDTO dto = new FoodDTO(foodID, foodName, image, description, priceF, date, category, status, quantityI);
                FoodDAO dao = new FoodDAO();

                HttpSession session = request.getSession();
                UserDTO userDto = (UserDTO) session.getAttribute("ACCOUNT");

                boolean result = dao.updateFood(dto, userDto.getUserID(), button);
                if (result) {
                    url = "DispatchServlet?"
                            + "btAction=Search"
                            + "&txtSearch=" + searchValue
                            + "&searchOption=" + option
                            + "&txtPriceFrom=" + priceFrom
                            + "&txtPriceTo=" + priceTo ;

                }
            }
        } catch (NamingException e) {
            LOGGER.error(e);
        } catch (SQLException e) {
            LOGGER.error(e);
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
