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

<form:form modelAttribute="profile" method="POST" action="/profile/edit/save">    

       <p>Sex</p>
       <form:select path="sex" name="sex" id="sex" cssClass="inputData" title="Sex">
       <option value="">Unspecified</option>
	   <option value="M">Male</option>
       <option value="F">Female</option>
	   </form:select> 
	
        
        <form:input path="interests" 
        placeholder="Interests(separate with comas)" onfocus="this.placeholder=''"  onblur="this.placeholder='Interests(separate with comas)'"/> 
           
         <form:input path="city"
         placeholder="City" onfocus="this.placeholder=''"  onblur="this.placeholder='City'"/> 
        
         <p>Allow others to search</p>
        <form:checkbox path="allowSearching" label="Allow others to search" /> 
        
        <p>Allow messages from strangers</p>
        <form:checkbox path="allowEveryoneToSendMessage" /> 
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        
        <form:button name="save">Save</form:button>   
           
       </form:form>    

	</div>


</body>

</html>