<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>预定会议室</title>
</head>
<body>
<h1>预定会议室</h1>
<form action="${pageContext.request.contextPath}/orderForm/addToRedis">
    会议室ID：<input type="text" value="${room.roomId}" name="roomId" readonly>
    会议室名字：<input type="text" value="${room.roomName}" readonly><br>
    预定开始时间：<input type="text" name="startTime" id="start">
    预定结束时间：<input type="text" name="endTime" id="end">
    <input type="button" id="btn" value="检查">
    <button type="submit">提交</button>
</form>
</body>
</html>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script>
    $("#btn").click(function () {
        console.log($("#start").val());
        console.log($("#end").val());
    })
</script>