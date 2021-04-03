/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khoapc.order.OrderDAO;
import khoapc.order.OrderDTO;
import khoapc.shoppinghitory.ShoppingHistoyDAO;
import khoapc.shoppinghitory.ShoppingHistoyDTO;
import khoapc.user.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Khoa Pham
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchHistoryServlet.class);

    private final String HISTORY_PAGE = "history.jsp";

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
        String txtDate = request.getParameter("date");
        String foodName = request.getParameter("foodName");

        String searchOption = request.getParameter("searchOption");

        String url = HISTORY_PAGE;
        try {
            // Convert date => Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // Het Account username
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO account = (UserDTO) session.getAttribute("ACCOUNT");
                if (account != null) {
                    String username = account.getUserID();
                    OrderDAO dao = new OrderDAO();
                    List<OrderDTO> listOrder = null;
                    if (searchOption.equals("Date")) {
                        if (txtDate.trim().length() != 0) {
                            Date date = format.parse(txtDate);
                            listOrder = dao.getHistoryByDate(username, date);
                        }
                    } else {
                        listOrder = dao.getHistoryByFoodName(username, foodName);
                    }
                    if (listOrder != null) {
                        session.setAttribute("LIST_ORDER", listOrder);
                        List<ShoppingHistoyDTO> listFood = null;
                        session.setAttribute("LIST_ORDER", listOrder);
                        ShoppingHistoyDAO historyDAO = new ShoppingHistoyDAO();
                        ShoppingHistoyDTO hisDTO = new ShoppingHistoyDTO();
                        for (OrderDTO orderDTO : listOrder) {
                            hisDTO = historyDAO.getFoodHistoryByOrderID(orderDTO);
                            if (hisDTO != null) {
                                if (listFood == null) {
                                    listFood = new ArrayList<>();
                                }
                                listFood.add(hisDTO);
                            }
                        }
                        request.setAttribute("HISTORY_FOOD", listFood);
                    }
                }
            }
        } catch (Exception e) {
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
