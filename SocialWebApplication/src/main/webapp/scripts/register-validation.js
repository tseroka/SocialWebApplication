// Wait for the DOM to be ready
$(function() {
  // Initialize form validation on the registration form.
  // It has the name attribute "registration"
  $("form[name='registerForm']").validate({
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      username: {
         required: true,
         pattern: "^[a-zA-Z0-9]{8,25}$",
         minlength: 8,
         maxlength: 25
      },
      nickname: {
    	  required: true,
    	  pattern:"^[a-zA-Z]{4,25}$",
    	  minlength: 4,
    	  maxlength: 25
      },
      email: {
        required: true,
        // Specify that email should be validated
        // by the built-in "email" rule
        email: true
      },
      password: {
    	pattern: "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{8,40})",
        required: true,
        minlength: 8,
        maxlength:40
      }
    },
    // Specify validation error messages
    messages: {
      username:{
        required: "Please enter your username",
        pattern: "Username can only contain lower, uppercase letters and digits",
        minlength: "Username must be at least 8 characters long",
        maxlength: "Username can't be longer than 25 characters"
      },
      nickname:
      {
    	  required: "Please enter your nickname",
          pattern: "Nickname can only contain lower, uppercase letters",
          minlength: "Nickname must be at least 4 characters long",
          maxlength: "Nickname can't be longer than 25 characters"
      },
      password: {
        required: "Please provide a password",
        minlength: "Your password must be at least 8 characters long",
        maxlength: "Your password can't be longer than 40 characters",
        pattern: "Password must contain at least 1 lower, uppercase letter, digit and special character"
      },
      email: "Please enter a valid email address"
    },
    // Make sure the form is submitted to the destination defined
    // in the "action" attribute of the form when valid
    submitHandler: function(form) {
      form.submit();
    }
  });
});