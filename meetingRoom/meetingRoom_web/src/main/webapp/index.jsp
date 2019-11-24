<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>会议室</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/orderForm/findAll">查看数据库中的订单</a>
<a href="${pageContext.request.contextPath}/orderForm/findFromRedis">查看redis中的订单</a>
<table>
    <tr>
        <th>会议室ID</th>
        <th>会议室名字</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${allRoom}" var="room">
        <tr>
            <td>${room.roomId}</td>
            <td>${room.roomName}</td>
            <td><input type="button" value="预定" onclick="order(${room.roomId})"></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script>
        function order(roomId) {
            location.href="${pageContext.request.contextPath}/room/findById?roomId="+roomId;
        }
</script>