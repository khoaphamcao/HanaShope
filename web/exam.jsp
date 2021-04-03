<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Quiz Online</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="http://netdna.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style><%@include file="css/home.css" %> </style>

        <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
        <!--        <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
                <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>-->
    </head>
    <body onload="counterTime()">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css" integrity="sha256-3sPp8BkKUE7QyPSl6VfBByBroQbKxKG7tsusY2mhbVY=" crossorigin="anonymous" />
        <header class="header-area overlay">
            <nav class="navbar navbar-expand-md navbar-dark">
                <div class="container">
                    <a href="#"style="color: white" class="navbar-brand">Quiz Online</a>

                    <button type="button" class="navbar-toggler collapsed" data-toggle="collapse" data-target="#main-nav">
                        <span class="menu-icon-bar"></span>
                        <span class="menu-icon-bar"></span>
                        <span class="menu-icon-bar"></span>
                    </button>

                    <div id="main-nav" class="collapse navbar-collapse">
                        <ul class="navbar-nav ml-auto">
                            <li><a style="color: white" class="nav-item nav-link active">Welcome ${sessionScope.LOGIN_USER.name}</a></li>					
                            <li><a href="#" class="nav-item nav-link">History</a></li>
                            <li><a href="logout" class="nav-item nav-link">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="banner">
                <div class="container" id="exam">
                    <form role="form" id="myform" action="getQuestion" method="post">                      
                        <div class="container mt-sm-5 my-1">
                            <div class="question ml-sm-5 pl-sm-5 pt-2">
                                <c:set var="e" value="${requestScope.QUIZ}"></c:set>
                                <c:if test="${e!=null}">
                                    <div ><b>Q.${requestScope.currentQues} </b></div><br>
                                    <div ><b>${e.question_content}</b></div><br>
                                    <div class="ml-md-3 ml-sm-3 pl-md-5 pt-sm-0 pt-3" id="options"> 
                                        <c:set var="answer" value="${requestScope.QUIZ.answer}"></c:set>

                                        <c:forEach var="i" begin="1" end="4">
                                            <c:set var="flag" value="answer_content${i}"></c:set>
                                            <c:set var="content" value="${e[flag]}"></c:set>
                                            <label class="options">${content} 
                                                <input value="${e[flag]}" type="radio" name="quizChoose"<c:if test="${answer == content}">checked</c:if>> 
                                                    <span class="checkmark"></span> </label> 
                                            </c:forEach>
                                    </div>
                                </div>

                                <div class="d-flex align-items-center pt-3">
                                    <input type="hidden" name="quesFix" value="${requestScope.currentQues}">
                                    <c:if test="${requestScope.currentQues > 1}">
                                        <div id="prev"> <button  type="submit" name="currentQues" value="${requestScope.currentQues -1}">Previous</button></div>
                                    </c:if>
                                    <c:set var="numberQuestion" value="${sessionScope.EXAM.numberQuestion}"></c:set>
                                    <c:if test="${requestScope.currentQues < numberQuestion}">
                                        <div class="ml-auto mr-sm-5"> <button type="submit" name="currentQues" value="${requestScope.currentQues +1}">Next</button> </div>
                                    </c:if>
                                </div>
                            </div>
                            <c:forEach begin="1" end="${numberQuestion}" var="i">
                                <c:choose >
                                    <c:when test="${requestScope.currentQues == i}">
                                        <td style="width: 20px;color: red">${i}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <input style="width: 20px " type="submit" name="currentQues" value="${i}">
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                    </div>
                    <div>
                        <input type="hidden" id="remainingTimeText" name="time"value="${sessionScope.TIME}">
                        <span id="remainingTime" 
                              style="position: fixed;top:30px;left: 300px;font-size: 23px;background: rgba(255,0,77,0.38);border-radius: 5px;padding: 10px;box-shadow: 2px -2px 6px 0px;">
                        </span>
                        <input id="submit"type="submit" value="Submit" name="btnAction" onclick="return confirm('Submit and end the exam?')"class="button button-primary">
                    </div>

                    </form>
                </c:if>
                <c:if test="${e == null}">
                    <div class="col-sm-12  col-md-6" style="text-align: center;margin: auto" >
                        <h1>QUIZ ONLINE</h1>
                        <a href="home"
                           class="btn btn-block btn-light">Home</a>
                    </div>
                </c:if>
            </div>
        </header>


        <script>
            function counterTime() {
                var time = document.getElementById("remainingTimeText").value;
                var min = Math.floor(time / 60);
                var sec = Math.floor(time % 60);
                document.getElementById("remainingTime").innerHTML = min + " : " + sec;

                var x = window.setInterval(timerFunction, 1000);

                function timerFunction() {
                    var time = document.getElementById("remainingTimeText").value;
                    time--;
                    var min = Math.floor(time / 60);
                    var sec = Math.floor(time % 60);
                    // Display the result in the element with id="demo"
                    document.getElementById("remainingTimeText").value = time;
                    if (time <= 0) {
                        clearInterval(x);
                        document.getElementById("remainingTime").innerHTML = "00 : 00";
                        var redirect = "getQuestion?btnAction=Submit&quizChoose=";
                        if (document.querySelector('input[name="quizChoose"]:checked') !== null) {
                            var quizChoose = document.querySelector('input[name="quizChoose"]:checked').value;
                            redirect += quizChoose + "&quesFix=${requestScope.currentQues}";
                        }
                        window.location.href = redirect;
                    }
                    document.getElementById("remainingTime").innerHTML = min + " : " + sec;
                }
            }
            window.onload = counterTime();
        </script>
    </body>
</html>
