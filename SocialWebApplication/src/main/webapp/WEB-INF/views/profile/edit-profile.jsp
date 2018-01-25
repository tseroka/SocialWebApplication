<%@ page language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Edit profile</title>
</head>

<body>

<div id="container">

<form:form modelAttribute="profile" method="POST" action="/SocialWebApplication/profile/edit/save">    

 
   Sex:<form:select path="sex" name="sex" id="sex" cssClass="inputData">
       <option value="">Unspecified</option>
	   <option value="M">Male</option>
       <option value="F">Female</option>
	   </form:select> <br>
	
        Interests(separate with commas): <form:input path="interests" /> <br>
        
        City: <form:input path="city"/> <br>
        
        Allow others to search : <form:checkbox path="allowSearching"/> <br>
        
        Allow messages from strangers : <form:checkbox path="allowEveryoneToSendMessage"/> <br>
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        
   <form:button name="save">Save</form:button>   
           
       </form:form>    

	</div>


</body>

</html>