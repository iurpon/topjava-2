<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 05.11.2017
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h1>
    <h1>${name}</h1>
    <table class="item-table"  width="59%" border="1" align="center">
        <tr>
            <th>DateTime</th>
            <th>Descripton</th>
            <th>Calories</th>
            <th>Exeed</th>
        </tr>
    <c:forEach var = "list" items = "${list}">
        <c:if test="${list.exceed}">
            <tr align="center" style="color:red">
                <td>${list.dateTime}</td>
                <td>${list.description}</td>
                <td>${list.calories}</td>
                <td>${list.exceed}</td>
            </tr>
         </c:if>
        <c:if test="${!list.exceed}">
            <tr align="center" style="color:blue">
                <td>${list.dateTime}</td>
                <td>${list.description}</td>
                <td>${list.calories}</td>
                <td>${list.exceed}</td>
            </tr>
        </c:if>
    </c:forEach>
    </table>
</h1>

</body>
</html>
