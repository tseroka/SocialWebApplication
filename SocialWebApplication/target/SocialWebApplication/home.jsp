<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

 <head>
 
    <meta charset="utf-8">
	<meta http-equiv="X-Ua-Compatible" content="IE=edge,chrome=1">
	
	<style>
	<%@include file="/css/index_style.css"%></style>
	</head>
<body>

      <nav id="topnav">

			  <c:if test="${pageContext.request.userPrincipal.name == null}">
                <div class="link"><a href="login">Login</a></div>
                <div class="link"><a href="register">Register</a></div>
              </c:if>
				
				
				<sec:authorize access="hasAuthority('ROLE ADMIN')">
				<div class="link"><a href="admin">Admin</a></div>
				</sec:authorize>
				
				
				<c:if test="${pageContext.request.userPrincipal.name != null}">
				<div id="mySidenav">
				
                  <div class="link"><a href="user/view">Your account</a></div>
                  <div class="link"><a href="profile/edit">Edit profile</a></div>
                  <div class="link"><a href="profile/messages/inbox">Inbox</a></div>
                  <div class="link"><a href="profile/messages/outbox">Outbox</a></div>
                  <div class="link"><a href="profile/messages/send/recipient=">Send</a></div>
                  <div class="link"><a href="search">Search profiles</a></div>
                  <div class="link"><a href="profile/friends/viewFriends">View friends</a></div>
                  <div class="link"><a href="profile/friends/viewSentInvitations">Sent invitations</a></div>
                  <div class="link"><a href="profile/friends/viewReceivedInvitations">Received invitations</a></div>
		          <div class="link"> <a href="<c:url value="/logout" />">Logout</a> </div>
		          
                </div>
                </c:if>
				
				
				<div class="link"><a href="about">About</a></div>			
			
			<div style="clear:both;"> </div>	
		</nav>
		 <div style="clear:both;"> </div>	
	<header>
	
		<h2 class="logo">Social Website</h2>

	</header>
    
	 <div id="shoutbox">SHOUT</div>
</body>


</html>