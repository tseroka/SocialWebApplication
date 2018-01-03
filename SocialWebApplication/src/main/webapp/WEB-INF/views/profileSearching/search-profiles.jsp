<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search profiles</title>
</head>
<body>
    <div id="container">

<form:form method="POST" action="/SocialWebApplication/search/goSearch">    

  
         
        
        Sex: <form:input path="searchSex" /> 
         
        Interests(separate with commas): <form:input path="searchInterests" /> 
        
        City: <form:input path="searchCity"/>
         
          <input type="submit" value="Search" />
           
       </form:form>    

	</div>
    

</body>
</html>