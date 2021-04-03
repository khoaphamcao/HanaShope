<%-- 
    Document   : create_food
    Created on : Jan 12, 2021, 6:15:39 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style> <%@include file="css/create.css" %></style>
        <title>Create Food</title>
    </head>
    <body>
        <c:if test="${sessionScope.ACCOUNT.isAdmin != '1'}">
            <c:redirect url="DispatchServlet?btAction=Login"></c:redirect>
        </c:if>
        <h1>Create Food</h1>
        <form action="DispatchServlet">
            <div class="container"> 
                FoodName:   <input type="text" name="txtFoodName" value="${param.txtFoodName}" required pattern="[a-zA-Z ]{1,20}" title="Foodname should only contain word and Foodname length > 1 AND < 20 " /> <br> <br>
                Image:      <input type="file" name="img" value="" accept="image/png, image/jpeg" /><br> <br>
                Description:<input type="text" name="txtDescription" value="${param.txtDescription}" /> <br> <br>
                Price:      <input type="number" name="txtPrice" value="0" min="0" step="1000" required/> <br> <br>
                Category:   <input type="text" name="txtCategory" value="${param.txtCategory}" required pattern="[a-zA-Z]{1,20}" title="Category should only contain word and Category length > 1 AND < 20 "/>  <br> <br>
                Quantity:   <input type="number" name="txtQuantity" value="1" min="1" required/> <br> <br>


                <input type="hidden" name="foodID" value="${param.foodID}" />
                <input type="hidden" name="lastSearchValue" value="${param.lastSearchValue}" />
                <input type="hidden" name="lastSearchOption" value="${param.lastSearchOption}" />
                <input type="hidden" name="lastSearchPriceFrom" value="${param.lastSearchPriceFrom}" />
                <input type="hidden" name="lastSearchPriceTo" value="${param.lastSearchPriceTo}" />
                <input type="hidden" name="index" value="${param.index}" />

                <input type="submit" value="Create" name="btAction"/>
                <a href="DefaultAdminSearchServlet"> Home </a>
            </div>
        </form>
        
                    </body>
                    </html>
