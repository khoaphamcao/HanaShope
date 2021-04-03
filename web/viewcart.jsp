<%-- 
    Document   : viewcart
    Created on : Jan 16, 2021, 3:05:55 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style><%@include file="css/viewcart.css" %> </style>
    </head>
    <body>
        <c:if test="${sessionScope.ACCOUNT.isAdmin != '0'}">
            <c:redirect url="DispatchServlet?btAction=Login"></c:redirect>
        </c:if>
        <header>
            <h1>
                <font color="white">
                Hana Shop
                </font>
            </h1>
            <h3>
                <c:set var="fullName" value="${sessionScope.ACCOUNT.fullname}"/>
                <font color="white">

                <h3>Welcome ${fullName}  </h3>
                </font></h3>
        </header>
        <form action="DispatchServlet">
            <c:if test="${fullName == null}">
                <input type="submit" value="Login" name="btAction" />
            </c:if>
            <c:if test="${fullName != null}">
                <input type="submit" value="Log Out" name="btAction" />
            </c:if>
        </form>

        <h2>Your Cart</h2>
        <c:set var="cart" value="${sessionScope.CART}" />
        <c:if test="${cart != null}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>ID</th>
                        <th>Food Name</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>


                <tbody>
                    <c:set var="total" value="0"/>
                <form action="DispatchServlet">
                    <c:forEach var="cartCus" items="${cart.items}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${cartCus.key}
                                <input type="hidden" name="txtfoodID" value="${cartCus.key}" />
                            </td>
                            <td>${cartCus.value.foodName}</td>
                            <td>${cartCus.value.category}</td>
                            <td>
                                <input type="number" name="txtQuantity" value="${cartCus.value.quantity}" min="1" max="1000"/>
                                <input type="submit" value="Update Quantity" name="btAction" />
                            </td>
                            <td>${cartCus.value.quantity * cartCus.value.price}
                                <input type="hidden" name="txtPrice" value="${cartCus.value.quantity * cartCus.value.price}" />
                                <c:set var="total" value="${total + cartCus.value.quantity * cartCus.value.price}" />
                            </td>
                            <td> 
                                <input type="checkbox" name="checkItems" value="${cartCus.key}" />
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4">
                            <a href="search.jsp">Add more Food To Cart </a>
                        </td>
                        <td colspan="2">
                            Total Price =  ${total}
                        </td>
                        <td>
                            <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" onclick="return confirm('Do you want to Delete this product?')" name="btAction" value="Remove Items">
                                Delete
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </form>
            </table> <br/>
        </c:if>
        <c:if test="${cart == null}">
            <font color="red">
            <h2> Cart is empty</h2>
            </font>
        </c:if>

        <form action="DispatchServlet" method="POST">
            <c:if test="${cart != null}">
                <input type="submit" value="Purchase" name="btAction" />
                <input type="submit" value="PayPal" name="btAction" />
            </c:if>
            <font color ="red">
            ${requestScope.ERROR}
            </font>

            <input type="hidden" name="total" value="${total}" />

            <input type="submit" value="View History" name="btAction"/>
        </form> <br/>

        <form action="DefaultSearchServlet" method="POST">
            <input type="submit" value="Return to Hana Shop" name="btAction"/>
        </form>

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
