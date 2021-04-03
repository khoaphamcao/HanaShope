<%-- 
    Document   : search
    Created on : Jan 5, 2021, 7:05:00 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style><%@include file="css/search.css" %> </style>
    </head>
    <body>
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

        <h3> Search Foods/ Drinks </h3>
        <form action="DispatchServlet">
            Search Food: <input type="text" name="txtSearch" value="${param.txtSearch}" /><br/><br/>
            Price: From: <input type="number" name="txtPriceFrom" min="0" value="${param.txtPriceFrom}" /> 
            To: <input type="number" name="txtPriceTo" min="0" value="${param.txtPriceTo}" />

            Filter <select name="searchOption">
                <c:set var="option" value="${param.searchOption}"/>
                <option>--Choose option--</option>

                <option <c:if test="${option == 'Product name' }" >selected</c:if> >Product name</option>
                <option <c:if test="${option == 'Price' }" >selected</c:if> >Price</option>
                <option <c:if test="${option == 'Category' }" >selected</c:if> >Category</option>

                </select><br/>
                <input type="submit" value="Search" name="btAction"/> 
            </form><br/>

        <c:set var="option" value="${param.searchOption}"/>
        <c:set var="searchValue" value="${param.txtSearch}"/>
        <c:if test="${searchValue != null}">
            <c:set var="result" value="${requestScope.RESULT}"/>
            <c:if test="${result != null}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>FoodID</th>
                            <th>FoodName</th>
                            <th>Image</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Create Date</th>
                            <th>Category</th>
                            <th>Quantity</th>

                            <th>Action</th>

                        </tr>
                    </thead>
                    <c:forEach var="dto" items="${result}" varStatus="counter">
                        <tbody>
                        <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.foodID}
                                    <input type="hidden" name="foodID" value="${dto.foodID}" />
                                </td>
                                <td>${dto.foodName}
                                    <input type="hidden" name="foodName" value="${dto.foodName}" /> 
                                </td>
                                <td> <img src= "img/${dto.image}"  width="150" height="150" 
                                          <input type="hidden" name="img" value="${dto.image}"/>
                                </td>
                                <td>${dto.description}</td>
                                <td>${dto.price}
                                    <input type="hidden" name="price" value="${dto.price}" /> </td>
                                </td>
                                <td>
                                    <fmt:formatDate type = "date"  value = "${dto.createDate}" />
                                </td>
                                <td>${dto.category}
                                    <input type="hidden" name="category" value="${dto.category}" />
                                </td>
                                <td> ${dto.quantity} 
                                    <input type="hidden" name="quantity" value="${dto.quantity}" />
                                </td>

                                <td> 
                                    <input type="submit" name="btAction" value="Add To Cart" /> 
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                    <input type="hidden" name="lastSearchOption" value="${option}" />
                                    <input type="hidden" name="lastSearchPriceFrom" value="${param.txtPriceFrom}" />
                                    <input type="hidden" name="lastSearchPriceTo" value="${param.txtPriceTo}" />
                                    <input type="hidden" name="index" value="${param.index}" />
                                </td>
                            </tr>
                        </form>
                    </tbody>
                </c:forEach>
            </table>
            <div class="paging">
                <c:forEach begin="1" end="${requestScope.END_PAGE}" var="i" >
                    <a href="SearchServlet?index=${i}&txtSearch=${requestScope.SEARCH_VALUE}&searchOption=${requestScope.OPTION}&txtPriceFrom=${requestScope.PRICE_FROM}&txtPriceTo=${requestScope.PRICE_TO}">${i}</a>
                </c:forEach>
            </div>

            <form action="DispatchServlet">
                <input type="submit" name="btAction" value="View Your Cart" />
            </form>

        </c:if>
        <c:if test="${result == null}">
            <font color="red">
            <h2> No record found </h2>
            </font>
        </c:if>
    </c:if>
    <br/>
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
