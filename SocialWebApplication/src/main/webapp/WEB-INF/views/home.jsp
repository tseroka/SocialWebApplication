<%@ page language="java" session="false"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!doctype html>
<html>

<head>
<meta charset="utf-8">
  
<title>Social Web Application</title>
    
<meta name="description" content="Web application with Spring MVC, Hibernate, MySQL">
<meta name="author" content="Tymon Seroka">

<style><%@include file="/css/index_style.css"%></style>

</head>

<body>

      <nav id="topnav">
			
				<sec:authorize access="hasAuthority('ROLE ADMIN')">
				<div class="link"><a href="admin">Admin</a></div>
				</sec:authorize>
				
				
				<c:if test="${pageContext.request.userPrincipal.name != null}">
				
			      <div id="mySidenav">
				
                     <div class="link"><a href="user/view">Your account</a></div>
                     <div class="link"><a href="profile/edit">Edit profile</a></div>
                     <!--<div class="link"><a href="profile/messages/inbox">Inbox</a></div>
                     <div class="link"><a href="profile/messages/outbox">Outbox</a></div>
                     <div class="link"><a href="profile/messages/send/recipient=">Send</a></div> -->  
                  
                     <div class="dropdown">
                     <button onclick="myFunction()" class="dropbtn">Messages</button>
                     <div id="myDropdown" class="dropdown-content">
                     <a class="link" href="profile/messages/inbox">Inbox</a>
                     <a class="link" href="profile/messages/outbox">Outbox</a>
                     <a class="link" href="profile/messages/send/recipient=">Send message</a>
                     </div>
                  
                     <div class="link"><a href="search">Search profiles</a></div>
                     <div class="link"><a href="profile/friends/viewFriends">View friends</a></div>
                     <div class="link"><a href="profile/friends/viewSentInvitations">Sent invitations</a></div>
                     <div class="link"><a href="profile/friends/viewReceivedInvitations">Received invitations</a></div>
		             <div class="link"> <a href="<c:url value="/logout" />">Logout</a> </div>
	                 </div>
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
	 
	 <script>

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
</script>
	 
</body>

</html>