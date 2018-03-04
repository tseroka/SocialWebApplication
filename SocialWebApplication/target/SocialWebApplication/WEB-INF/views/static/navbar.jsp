


  
    <div id='cssmenu'>


	<ul>		
	
	<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<li><a href='/home'><span>Home</span></a></li>
	
	<sec:authorize access="hasAuthority('ROLE_ADMIN')">

	<li class='active has-sub'><a href='#'><span>Admin</span></a> 
       <ul>
         <li class='last'><a href='/admin/view-users'><span>Manage users</span></a></li>
         <li class='last'><a href='/admin/view-active-users'><span>View online users</span></a></li>
         <li class='last'><a href='/admin/sendGlobalMessage'><span>Send global message</span></a></li>
       </ul> 
    </li>
   
	</sec:authorize>
				
	
     <li class='active has-sub'><a href='#'><span>Account</span></a> 
      <ul>
         <li class='last'><a href='/user/view'><span>View account</span></a></li>
         <li class='last'><a href='/user/edit'><span>Edit account</span></a></li>
		 <li class='last'><a href='/user/edit/password'><span>Change password</span></a></li>
      </ul> 
   </li>
			   
    <li class='active has-sub'><a href='#'><span>Profile</span></a> 
      <ul>
         <li class='last'><a href='/profile/yourProfile'><span>View profile</span></a></li>
         <li class='last'><a href='/profile/edit'><span>Edit profile</span></a></li>
		 <li class='last'><a href='/search'><span>Search profiles</span></a></li>
      </ul> 
   </li>
   
   
   <li class='active has-sub'><a href='#'><span>Messages</span></a>
      <ul>
         <li class='last'><a href='/profile/messages/inbox'><span>Inbox</span></a></li>
         <li class='last'><a href='/profile/messages/globalMessages'><span>Admin messages</span></a></li>
         <li class='last'><a href='/profile/messages/outbox'><span>Outbox</span></a></li>
		 <li class='last'><a href='/profile/messages/send/recipient='><span>Send message</span></a></li>
      </ul>
   </li>
   
   
   <li class='active has-sub'><a href='#'><span>Friends</span></a>
      <ul>
         <li class='last'><a href='/profile/friends/viewFriends'><span>Friends List</span></a></li>
         <li class='last'><a href='/profile/friends/viewSentInvitations'><span>Sent invitations</span></a></li>
		 <li class='last'><a href='/profile/friends/viewReceivedInvitations'><span>Received invitations</span></a></li>
      </ul>
   </li>
   
   <li><a href='<c:url value="/logout" />' ><span>Logout</span></a></li>
   
   <li><a href='/about'><span>About</span></a></li>
                
   </ul>

   </div>
   
    <header>
	
		<h2 class="logo">Social Website</h2>

	</header>
   