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
                <td style="color:black">${list.count}</td>
                <td>${f:formatLocalDateTime(list.dateTime, 'dd.MM.yyyy : HH.mm')}</td>
                <td>${list.description}</td>
                <td>${list.calories}</td>
                <td>${list.exceed}</td>
                <td><a href="?action=edit&userId=<c:out value="${list.count}"/>">Edit</a></td>
                <td><a href="?action=delete&userId=<c:out value="${list.count}"/>">Delete</a></td>

            </tr>
         </c:if>
        <c:if test="${!list.exceed}">
            <tr align="center" style="color:blue">
                <td style="color:black">${list.count}</td>
                <td>${f:formatLocalDateTime(list.dateTime, 'dd.MM.yyyy : HH.mm')}</td>
                <td>${list.description}</td>
                <td>${list.calories}</td>
                <td>${list.exceed}</td>
                <td><a href="?action=edit&userId=<c:out value="${list.count}"/>">Edit</a></td>
                <td><a href="?action=delete&userId=<c:out value="${list.count}"/>">Delete</a></td>
            </tr>
        </c:if>
    </c:forEach>
    </table>
</h1>
<form method="POST" >
    <p>Add Meal</p>
    DateTime : <input type="datetime-local"  name="dateTime"
                     value="<c:out value="${DateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${Description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${Calories}" />" /> <br />
    <input  type="submit" value="Добавить" />
</form>
</body>
</html>
