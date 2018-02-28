<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Add question</title>
</head>
<body>

<h3>Notes</h3>

<table>
    <c:forEach var="list" items="${lists}">
        <tr>
            <td><a href="/todo/${list.getId()}">${list.getName()}</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>