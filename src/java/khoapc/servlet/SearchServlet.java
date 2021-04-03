/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khoapc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class);

    private final String SEARCH_PAGE = "search.jsp";
    private final String ADMIN_PAGE = "admin_page.jsp";
    private final String DEFAULT_SERVLET = "DefaultSearchServlet";
    private final String DEFAULT_ADMIN_SERVLET = "DefaultAdminSearchServlet";
    

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
        String url = DEFAULT_SERVLET;

        String searchValue = request.getParameter("txtSearch");
        String searchOption = request.getParameter("searchOption");
        String priceFrom = request.getParameter("txtPriceFrom");
        String priceTo = request.getParameter("txtPriceTo");

        try {
            FoodDAO dao = new FoodDAO();
            int countFood = 0;
            int endPage = 0;

            //get page index
            String txtIndex = request.getParameter("index");
            int index = 0;
            if (txtIndex == null) {
                index = 1;
            } else {
                index = Integer.parseInt(txtIndex);
            }

            //Role page
            HttpSession session = request.getSession();
            UserDTO account = (UserDTO) session.getAttribute("ACCOUNT");

            //Controler
            if (account != null) {
                if (account.getIsAdmin() == 1) {
                    url = ADMIN_PAGE;
                    if ((searchValue.trim().length() > 0) || (priceFrom.trim().length() > 0 && priceTo.trim().length() > 0)) {
                        if (searchOption.equals("Product name")) {
                            countFood = dao.countAdminFoodName(searchValue);
                            endPage = dao.getEndPage(countFood);
                            if (endPage != 0) {
                                request.setAttribute("END_PAGE", endPage);
                                dao.getPagingAdminListFoodName(searchValue, index);
                                List<FoodDTO> result = dao.getFoodList(); 
                                request.setAttribute("RESULT", result);
                                request.setAttribute("SEARCH_VALUE", searchValue);
                                request.setAttribute("OPTION", searchOption);
                            }
                        } else if (searchOption.equals("Price")) {
                            if (priceFrom.trim().length() > 0 && priceTo.trim().length() > 0) {
                                float priceFromF = Float.parseFloat(priceFrom.trim());
                                float priceToF = Float.parseFloat(priceTo.trim());
                                countFood = dao.countAdminFoodPrice(priceFromF, priceToF);
                                endPage = dao.getEndPage(countFood);
                                if (endPage != 0) {
                                    request.setAttribute("END_PAGE", endPage);
                                    dao.getPagingListAdminFoodPrice(priceFromF, priceToF, index);
                                    List<FoodDTO> result = dao.getFoodList();
                                    request.setAttribute("RESULT", result);
                                    request.setAttribute("PRICE_FROM", priceFrom);
                                    request.setAttribute("PRICE_TO", priceTo);
                                    request.setAttribute("OPTION", searchOption);
                                }
                            }
                        } else if (searchOption.equals("Category")) {
                            countFood = dao.countAdminFoodCategory(searchValue);
                            endPage = dao.getEndPage(countFood);
                            if (endPage != 0) {
                                request.setAttribute("END_PAGE", endPage);
                                dao.getPagingListAdminFoodCategory(searchValue, index);
                                List<FoodDTO> result = dao.getFoodList();
                                request.setAttribute("RESULT", result);
                                request.setAttribute("SEARCH_VALUE", searchValue);
                                request.setAttribute("OPTION", searchOption);
                            }
                        }
                    }else if((searchValue.trim().length() == 0) && (priceFrom.trim().length() == 0 && priceTo.trim().length() == 0)){
                        url = DEFAULT_ADMIN_SERVLET;
                    }
                } else {
                    if ((searchValue.trim().length() > 0) || (priceFrom.trim().length() > 0 && priceTo.trim().length() > 0)) {
                        if (searchOption.equals("Product name")) {
                            countFood = dao.countFoodName(searchValue);
                            endPage = dao.getEndPage(countFood);
                            if (endPage != 0) {
                                request.setAttribute("END_PAGE", endPage);
                                dao.getPagingListFoodName(searchValue, index);
                                List<FoodDTO> result = dao.getFoodList();
                                request.setAttribute("RESULT", result);
                                request.setAttribute("SEARCH_VALUE", searchValue);
                                request.setAttribute("OPTION", searchOption);
                                url = SEARCH_PAGE;
                            }
                        } else if (searchOption.equals("Price")) {
                            if (priceFrom.trim().length() > 0 && priceTo.trim().length() > 0) {
                                float priceFromF = Float.parseFloat(priceFrom.trim());
                                float priceToF = Float.parseFloat(priceTo.trim());
                                countFood = dao.countFoodPrice(priceFromF, priceToF);
                                endPage = dao.getEndPage(countFood);
                                if (endPage != 0) {
                                    request.setAttribute("END_PAGE", endPage);
                                    dao.getPagingListFoodPrice(priceFromF, priceToF, index);
                                    List<FoodDTO> result = dao.getFoodList();
                                    request.setAttribute("RESULT", result);
                                    request.setAttribute("PRICE_FROM", priceFrom);
                                    request.setAttribute("PRICE_TO", priceTo);
                                    request.setAttribute("OPTION", searchOption);
                                    url = SEARCH_PAGE;
                                }
                            }
                        } else if (searchOption.equals("Category")) {
                            countFood = dao.countFoodCategory(searchValue);
                            endPage = dao.getEndPage(countFood);
                            if (endPage != 0) {
                                request.setAttribute("END_PAGE", endPage);
                                dao.getPagingListFoodCategory(searchValue, index);
                                List<FoodDTO> result = dao.getFoodList();
                                request.setAttribute("RESULT", result);
                                request.setAttribute("SEARCH_VALUE", searchValue);
                                request.setAttribute("OPTION", searchOption);
                                url = SEARCH_PAGE;
                            }
                        }
                    }
                }
            } else {
                if ((searchValue.trim().length() > 0) || (priceFrom.trim().length() > 0 && priceTo.trim().length() > 0)) {
                    if (searchOption.equals("Product name")) {
                        countFood = dao.countFoodName(searchValue);
                        endPage = dao.getEndPage(countFood);
                        if (endPage != 0) {
                            request.setAttribute("END_PAGE", endPage);
                            dao.getPagingListFoodName(searchValue, index);
                            List<FoodDTO> result = dao.getFoodList();
                            request.setAttribute("RESULT", result);
                            request.setAttribute("SEARCH_VALUE", searchValue);
                            request.setAttribute("OPTION", searchOption);
                            url = SEARCH_PAGE;
                        }
                    } else if (searchOption.equals("Price")) {
                        if (priceFrom.trim().length() > 0 && priceTo.trim().length() > 0) {
                            float priceFromF = Float.parseFloat(priceFrom.trim());
                            float priceToF = Float.parseFloat(priceTo.trim());
                            countFood = dao.countFoodPrice(priceFromF, priceToF);
                            endPage = dao.getEndPage(countFood);
                            if (endPage != 0) {
                                request.setAttribute("END_PAGE", endPage);
                                dao.getPagingListFoodPrice(priceFromF, priceToF, index);
                                List<FoodDTO> result = dao.getFoodList();
                                request.setAttribute("RESULT", result);
                                request.setAttribute("PRICE_FROM", priceFrom);
                                request.setAttribute("PRICE_TO", priceTo);
                                request.setAttribute("OPTION", searchOption);
                                url = SEARCH_PAGE;
                            }
                        }
                    } else if (searchOption.equals("Category")) {
                        countFood = dao.countFoodCategory(searchValue);
                        endPage = dao.getEndPage(countFood);
                        if (endPage != 0) {
                            request.setAttribute("END_PAGE", endPage);
                            dao.getPagingListFoodCategory(searchValue, index);
                            List<FoodDTO> result = dao.getFoodList();
                            request.setAttribute("RESULT", result);
                            request.setAttribute("SEARCH_VALUE", searchValue);
                            request.setAttribute("OPTION", searchOption);
                            url = SEARCH_PAGE;
                        }
                    }
                }
            }
        } catch (NamingException ex) {
            LOGGER.error(ex);
        } catch (SQLException ex) {
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
