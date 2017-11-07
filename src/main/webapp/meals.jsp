<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 05.11.2017
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
    <h2 align="center">
        <a href="/new">Add meal</a>
        <a href="/list">Meal List</a>
    </h2>
<h1>
    <h1>${name}</h1>
    <table class="item-table"  width="59%" border="1" align="center">
        <tr>
            <th>Id</th>
            <th>DateTime</th>
            <th>Descripton</th>
            <th>Calories</th>
            <th>Exeed</th>
        </tr>
    <c:forEach var = "list" items = "${list}">

        <c:if test="${list.exceed}">
            <tr align="center" style="color:red">
        </c:if>
        <c:if test="${!list.exceed}">
            <tr align="center" style="color:blue">
        </c:if>
                <td style="color:black">${list.count}</td>
                <td>${f:formatLocalDateTime(list.dateTime, 'dd.MM.yyyy : HH.mm')}</td>
                <td>${list.description}</td>
                <td>${list.calories}</td>
                <td>${list.exceed}</td>
                <td><a href="/edit?id=<c:out value="${list.count}"/>">Edit</a></td>
                <td><a href="/delete?id=<c:out value="${list.count}"/>">Delete</a></td>

            </tr>


    </c:forEach>
    </table>
</h1>

</body>
</html>
