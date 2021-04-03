/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.food.FoodDAO;
import khoapc.food.FoodDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "DefaultSearchServlet", urlPatterns = {"/DefaultSearchServlet"})
public class DefaultSearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DefaultSearchServlet.class);

    private final String DEFAULT_SEARCH = "default_search.jsp";

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
        String url = DEFAULT_SEARCH;
        try {
            FoodDAO dao = new FoodDAO();
            int countFood = 0;
            int endPage = 0;

            //get page index
            String txtIndex = request.getParameter("index");
            int index = 0;
            if (txtIndex == null || txtIndex.equals("")) {
                index = 1;
            } else {
                index = Integer.parseInt(txtIndex);
            }
            countFood = dao.countAllFood();
            endPage = dao.getEndPage(countFood);
            if (endPage != 0) {
                request.setAttribute("END_PAGE", endPage);
                dao.getPagingListAllFood(index);
                List<FoodDTO> result = dao.getFoodList();
                HttpSession session = request.getSession();
                session.setAttribute("RESULT", result);
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
