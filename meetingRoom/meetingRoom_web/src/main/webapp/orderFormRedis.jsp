<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单</title>
</head>
<body>
<table>
    <tr>
        <th>订单ID</th>
        <th>会议室ID</th>
        <th>下单时间</th>
        <th>预定开始时间</th>
        <th>预定结束时间</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${list}" var="orderForm">
        <tr>
            <td>${orderForm.orderFormId}</td>
            <td>${orderForm.roomId}</td>
            <td>${orderForm.orderTime}</td>
            <td>${orderForm.startTime}</td>
            <td>${orderForm.endTime}</td>
            <td>
                <form action="${pageContext.request.contextPath}/orderForm/addToSql">
                    <input type="hidden" value="${orderForm.orderFormId}" name="orderFormId">
                    <input type="hidden" value="${orderForm.roomId}" name="roomId">
                    <input type="hidden" value="${orderForm.orderTime}" name="orderTime">
                    <input type="hidden" value="${orderForm.startTime}" name="startTime">
                    <input type="hidden" value="${orderForm.endTime}" name="endTime">
                    <input type="submit" value="付款" >
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
