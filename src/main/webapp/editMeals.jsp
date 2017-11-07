<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 07.11.2017
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
    <h1 align="center">Managment</h1>
    <h2 align="center">
        <a href="/new">Add new meal</a>
        <a href="/list">Meal List</a>
    </h2>
    <div align="center">
        <c:if test="${meal != null}">
            <form action = "update" method="post">
        </c:if>
        <c:if test="${meal == null}">
            <form action = "create" method="post">
        </c:if>

            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${meal != null}">Update</c:if>
                        <c:if test="${meal == null}">Create New</c:if>
                    </h2>
                </caption>
                <tr>
                    <th> ID </th>
                    <td>
                        <input type="text"  name="id"
                               value="<c:out value="${meal.count}" />" /> <br />
                    </td>
                </tr>
                <tr>
                <tr>
                    <th> DateTime </th>
                    <td>
                        <input type="datetime-local"  name="dateTime"
                                      value="<c:out value="${meal.dateTime}" />" /> <br />
                     </td>
                </tr>
                <tr>
                    <th> Description </th>
                    <td>
                        <input type="text"  name = "description"
                                      value="<c:out value="${meal.description}" />" /> <br />
                     </td>
                </tr>
                <tr>
                    <th> Calories </th>
                    <td>
                        <input type="text"  name="calories"
                                      value="<c:out value="${meal.calories}" />" /> <br />
                     </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save" />
                    </td>
                </tr>

            </table>
            </form>

    </div>


</body>
</html>
