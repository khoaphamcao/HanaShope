<%-- 
    Document   : history
    Created on : Jan 20, 2021, 3:39:05 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style><%@include file="css/history.css"%></style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <title>Custom's History</title>
    </head>
    <body>
        <header>
            <font color="white">
            <h1>Shopping History</h1>
            </font>
            <h3>
        </header>

        <form action="DispatchServlet" method="POST">

            Foodname: <input type="text" name="foodName" value="${param.foodName}" /><br/>
            <br/>
            <label for="start">Start date:</label>

            <input type="date" id="start" name="date"
                   value="${param.date}"
                   <br/><br/>


            <input type="submit" name="btAction" value="Search in history" />  
            By <select name="searchOption">
                <c:set var="option" value="${param.searchOption}"/>
                <option <c:if test="${option == 'FoodName' }" >selected</c:if>>  FoodName</option>
                <option <c:if test="${option == 'Date' }" > selected</c:if>>  Date</option>
                </select>
            </form><br/>


        <c:if test="${sessionScope.LIST_ORDER != null}">
            <c:if test="${requestScope.HISTORY_FOOD != null}">
                <c:forEach var="list" items="${sessionScope.LIST_ORDER}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th colspan="4">Order ID: ${list.orderID} </th>
                            </tr>
                            <tr>
                                <th>Food Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="foodInfo" items="${requestScope.HISTORY_FOOD}">
                                <c:if test="${foodInfo.orderID == list.orderID}">
                                    <c:forEach var="foodList" items="${foodInfo.foodList}">
                                        <tr>
                                            <td>${foodList.foodName}</td>
                                            <td> ${foodList.quantity}</td>
                                            <td>${foodList.price}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                            <tr>
                                <td colspan="2">Buy Date:   
                                    <fmt:formatDate type = "date" value = "${list.date}" />
                                </td>
                                <td>Total: ${list.total}</td>
                            </tr>
                        </tbody>
                    </table><br/>
                </c:forEach> 
            </c:if>
            <c:if test="${requestScope.HISTORY_FOOD == null}">
                <font color="red">
                No food found 
                </font>
                <br/>
            </c:if>



        </c:if>
        <c:if test="${sessionScope.LIST_ORDER == null}">
            <font color="red">
            No order yet
            </font>
        </c:if>
        <a href="DefaultSearchServlet">Return Home Page</a><br/>
        <a href="viewcart.jsp">Return To Cart</a>

        <div class="footer-dark">
            <footer>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-md-3 item">
                            <h3>Services</h3>
                            <ul>
                                <li><a href="#">Web design</a></li>
                                <li><a href="#">Development</a></li>
                                <li><a href="#">Hosting</a></li>
                            </ul>
                        </div>
                        <div class="col-sm-6 col-md-3 item">
                            <h3>About</h3>
                            <ul>
                                <li><a href="#">Company</a></li>
                                <li><a href="#">Team</a></li>
                                <li><a href="#">Careers</a></li>
                            </ul>
                        </div>
                        <div class="col-md-6 item text">
                            <h3>Company Name</h3>
                            <p>Praesent sed lobortis mi. Suspendisse vel placerat ligula. Vivamus ac sem lacus. Ut vehicula rhoncus elementum. Etiam quis tristique lectus. Aliquam in arcu eget velit pulvinar dictum vel in justo.</p>
                        </div>
                        <div class="col item social"><a href="#"><i class="icon ion-social-facebook"></i></a><a href="#"><i class="icon ion-social-twitter"></i></a><a href="#"><i class="icon ion-social-snapchat"></i></a><a href="#"><i class="icon ion-social-instagram"></i></a></div>
                    </div>
                    <p class="copyright">Company Name © 2018</p>
                </div>
            </footer>
        </div>

    </body>
</html>
