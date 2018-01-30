
    <div id='cssmenu'>


	<ul>		
	
	<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	
	<li><a href='/SocialWebApplication/home'><span>Home</span></a></li>
	
	<sec:authorize access="hasAuthority('ROLE ADMIN')">

	<li class='active has-sub'><a href='#'><span>Admin</span></a> 
       <ul>
         <li class='last'><a href='/SocialWebApplication/admin/view-users'><span>Manage users</span></a></li>
         <li class='last'><a href='/SocialWebApplication/admin/sendGlobalMessage'><span>Send global message</span></a></li>
       </ul> 
    </li>
   
	</sec:authorize>
				
	
     <li class='active has-sub'><a href='#'><span>Account</span></a> 
      <ul>
         <li class='last'><a href='/SocialWebApplication/user/view'><span>View account</span></a></li>
         <li class='last'><a href='/SocialWebApplication/user/edit'><span>Edit account</span></a></li>
		 <li class='last'><a href='/SocialWebApplication/user/edit/password'><span>Change password</span></a></li>
      </ul> 
   </li>
			   
    <li class='active has-sub'><a href='#'><span>Profile</span></a> 
      <ul>
         <li class='last'><a href='/SocialWebApplication/profile/yourProfile'><span>View profile</span></a></li>
         <li class='last'><a href='/SocialWebApplication/profile/edit'><span>Edit profile</span></a></li>
		 <li class='last'><a href='/SocialWebApplication/search'><span>Search profiles</span></a></li>
      </ul> 
   </li>
   
   
   <li class='active has-sub'><a href='#'><span>Messages</span></a>
      <ul>
         <li class='last'><a href='/SocialWebApplication/profile/messages/inbox'><span>Inbox</span></a></li>
         <li class='last'><a href='/SocialWebApplication/profile/messages/globalMessages'><span>Admin messages</span></a></li>
         <li class='last'><a href='/SocialWebApplication/profile/messages/outbox'><span>Outbox</span></a></li>
		 <li class='last'><a href='/SocialWebApplication/profile/messages/send/recipient='><span>Send message</span></a></li>
      </ul>
   </li>
   
   
   <li class='active has-sub'><a href='#'><span>Friends</span></a>
      <ul>
         <li class='last'><a href='/SocialWebApplication/profile/friends/viewFriends'><span>Friends List</span></a></li>
         <li class='last'><a href='/SocialWebApplication/profile/friends/viewSentInvitations'><span>Sent invitations</span></a></li>
		 <li class='last'><a href='/SocialWebApplication/profile/friends/viewReceivedInvitations'><span>Received invitations</span></a></li>
      </ul>
   </li>
   
   <li><a href='/SocialWebApplication/logout'><span>Logout</span></a></li>
   
   <li><a href='about'><span>About</span></a></li>
                
   </ul>

   </div>
   