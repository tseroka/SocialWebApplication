<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Search profiles</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/form_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

    <div id="container">

<form:form method="POST" action="/search/goSearch">    

  
    <p>Sex</p>
       <form:select path="searchSex" name="sex" id="sex" cssClass="inputData">
       <option value="">Unspecified</option>
	   <option value="M">Male</option>
       <option value="F">Female</option>
	</form:select>
         
        <form:input path="searchInterests"
        placeholder="Interests(separate with comas)" onfocus="this.placeholder=''"  onblur="this.placeholder='Interests(separate with comas)'" /> 
        
        <form:input path="searchCity"
         placeholder="City" onfocus="this.placeholder=''"  onblur="this.placeholder='City'"/>
         
          <input type="submit" value="Search" />
           
       </form:form>    

	</div>
    

</body>

</html>