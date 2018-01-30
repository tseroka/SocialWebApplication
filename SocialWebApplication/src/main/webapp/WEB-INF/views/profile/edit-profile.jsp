<%@ page language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Edit profile</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/form_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

<div id="container">

<form:form modelAttribute="profile" method="POST" action="/SocialWebApplication/profile/edit/save">    

 
   <p>Sex</p>
   <form:select path="sex" name="sex" id="sex" cssClass="inputData">
       <option value="">Unspecified</option>
	   <option value="M">Male</option>
       <option value="F">Female</option>
	   </form:select> <br>
	
        <p>Interests(separate with commas)</p>
        <form:input path="interests" /> <br>
        
        City: <form:input path="city"/> <br>
        
        Allow others to search : <form:checkbox path="allowSearching"/> <br>
        
        Allow messages from strangers : <form:checkbox path="allowEveryoneToSendMessage"/> <br>
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        
   <form:button name="save">Save</form:button>   
           
       </form:form>    

	</div>


</body>

</html>