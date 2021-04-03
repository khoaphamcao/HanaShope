<%-- 
    Document   : update_page
    Created on : Jan 13, 2021, 9:33:06 AM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style> <%@include file="css/update.css" %></style>
        <script src="https://code.jquery.com/jquery-2.2.4.js" ></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script >
             function displayImg() {
                var image = document.getElementById('image');
                image.src = document.getElementById('fileImage').value;
            }
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#image')
                                .attr('src', e.target.result)

                    };

                    reader.readAsDataURL(input.files[0]);
                }
            }
        </script>
    </head>
    <body>        
        <c:if test="${sessionScope.ACCOUNT.isAdmin != '1'}">
            <c:redirect url="DispatchServlet?btAction=Login"></c:redirect>
        </c:if>
        <h1>Update Food</h1>
        <form action="DispatchServlet">
            <div>
                <img src="${param.img}"id="image" style="height: 300px; width: 300px" /> <br/>
                <input type="hidden" name="oldImg" value="${param.img}"/>
                FoodName:   <input type="text" name="foodName" value="${param.foodName}"  pattern="[a-zA-Z ]+{1,20}" title="Foodname should only contain word and Foodname length > 1 AND < 20 " /> <br> <br>
                Image:      <input type="file" name="fileImage" accept="image/png, image/jpeg" onchange="readURL(this)"/><br> <br>
                Description:<input type="text" name="description" value="${param.description}" /> <br> <br>
                Price:      <input type="number" name="price" value="${param.price}" min="0" step="1000" /> <br> <br>
                Category:   <input type="text" name="category" value="${param.category}"  pattern="[a-zA-Z ]+{1,20}" title="Category should only contain word and Category length > 1 AND < 20 "/>  <br> <br>
                Quantity:   <input type="number" name="quantity" value="${param.quantity}" min="1"  /> <br> <br>
                Status:     <select name="status" >
                    <option <c:if test="${param.status == 'true' }" >selected </c:if> value="true" >Active</option>
                    <option <c:if test="${param.status == 'false' }" >selected</c:if> value="false"> Not active</option>
                            </select><br/><br/>
            
           
            
            <input type="hidden" name="foodID" value="${param.foodID}" />
            <input type="hidden" name="lastSearchValue" value="${param.lastSearchValue}" />
            <input type="hidden" name="lastSearchOption" value="${param.lastSearchOption}" />
            <input type="hidden" name="lastSearchPriceFrom" value="${param.lastSearchPriceFrom}" />
            <input type="hidden" name="lastSearchPriceTo" value="${param.lastSearchPriceTo}" />
            <input type="hidden" name="index" value="${param.index}" />
            <input type="submit" value="Update Food" name="btAction"/>
            </div>
        </form>
                <a href="DefaultAdminSearchServlet">Return to Admin Search</a>
    </body>
</html>
