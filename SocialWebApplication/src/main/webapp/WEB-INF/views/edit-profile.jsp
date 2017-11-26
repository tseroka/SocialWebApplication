<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit profile</title>
</head>
<body>
<div id="container">

<form:form modelAttribute="profile" method="POST" action="/SocialWebApplication/profile/edit/save">    

   
             
        
         <form:radiobutton path="sex" value="M" label="M" /> 
         <form:radiobutton path="sex" value="F" label="F" /> </br>
    
        Interests(separate with commas): <form:input path="interests" /> </br>
        
        City: <form:input path="city"/> </br>
        
        Allow others to search : <form:checkbox path="allowSearching"/> </br>
        
   <form:button name="save">Save</form:button>   
           
       </form:form>    

	</div>


</body>
</html>