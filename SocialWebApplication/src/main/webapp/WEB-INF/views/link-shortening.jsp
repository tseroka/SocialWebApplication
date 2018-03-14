<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Shorten url</title>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/form_style.css"%></style>

</head>

<body>

	
<div id="container">                                   
<c:if test="${isShortened}">
<p class="ok">Your shortened url: <a href='/short/${shortUrl}'>social.vaytee.com/short/${shortUrl}</a></p>
</c:if>

<form:form modelAttribute = "shortenedLink" method="POST" action="/shorteningProcessing">
         
<form:input  path="url" name="url" id="url" cssClass="inputData" 
placeholder="URL" onfocus="this.placeholder=''"  onblur="this.placeholder='URL'"/> <br>
									
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
              
                <form:button>Shorten url</form:button> 

        </form:form>

  

	</div>
	
</body>

</html>