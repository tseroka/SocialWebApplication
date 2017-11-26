<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Access denied</title>
<style><%@include file="/css/403_style.css"%></style> 
</head>
<body>
	
	<c:choose>
		<c:when test="${empty username}">
		  <h2>You do not have permission to access this page!</h2>
		</c:when>
		<c:otherwise>
		  <h2>${username} <br/>
          You do not have permission to access this page!</h2>
		</c:otherwise>
	</c:choose>

</body>
</html>