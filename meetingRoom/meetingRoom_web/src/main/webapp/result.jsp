<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>结果</title>
</head>
<body>
<c:if test="${result.flag}">
    <style>
        body{
            background: url("${pageContext.request.contextPath}/img/success.jpg");
            background-size: 100%;
        }
    </style>
    <h1>${result.message}</h1>
</c:if>
<c:if test="${!result.flag}">
    <style>
        body{
            background: url("${pageContext.request.contextPath}/img/error.jpg");
            background-size: 100%;
        }
    </style>
    <h1>${result.message}</h1>
</c:if>
</body>
</html>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
