<%-- 
    Document   : paypal.jsp
    Created on : Jan 23, 2021, 4:30:32 PM
    Author     : Khoa Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanh toán qua Paypal</title>
    </head>
    <body>
       <fieldset>
	<legend>Thanh toán qua cổng PayPal</legend>
	<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">
            <!-- Nhập địa chỉ email người nhận tiền (người bán) -->
            <input type="hidden" name="business" value="sb-e75ik4818206@personal.example.com">
            <!-- tham số cmd có giá trị _xclick chỉ rõ cho paypal biết là người dùng nhất nút thanh toán -->
            <input type="hidden" name="cmd" value="_xclick">
            <!-- Thông tin mua hàng. -->
            <input type="hidden" name="item_name" value="Bill Hana Shop">
			<!--Trị giá của giỏ hàng, vì paypal không hỗ trợ tiền việt nên phải đổi ra tiền $-->
            <c:set var="items" value="${sessionScope.CART}"/>            
            <c:set var="total" value="${param.total}"/>        
            
            <input type="number" name="amount" value="${total / 20000}$" readonly>
            <input type="hidden" name="total" value="${total}" />
			<!--Loại tiền-->
            <input type="hidden" name="currency_code" value="USD">
			<!--Đường link mình cung cấp cho Paypal biết để sau khi xử lí thành công nó sẽ chuyển về theo đường link này-->
            <input type="hidden" name="return" value="http://localhost:8084/J3.L.P0013/DispatchServlet?btAction=Purchase&total=${total}">
			<!--Đường link mình cung cấp cho Paypal biết để nếu  xử lí KHÔNG thành công nó sẽ chuyển về theo đường link này-->
            <input type="hidden" name="cancel_return" value="http://localhost:8084/J3.L.P0013/PurchaseServlet">
            <!-- Nút bấm. -->
            <input type="submit" name="btAction" value="OK">
	</form>
        </fieldset>
    </body>
</html>
