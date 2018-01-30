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
           <div class="link"><a href="login">Login</a></div>
           <div class="link"><a href="register">Register</a></div>
		   <div class="link"><a href="about">About</a></div>			
	   </nav>
		
	<header>
		<h2 class="logo">Social Website</h2>
	</header>
    
	 
<script>

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

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