package fi.haagahelia.ajokilometrit.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import jakarta.validation.*;

import fi.haagahelia.ajokilometrit.domain.*;

@Controller
public class UserController {
	@Autowired
    private AppUserRepository repository; 
	
    @RequestMapping(value = "signup")
    public String addStudent(Model model){
    	model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }	
    

    @RequestMapping(value = "saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signupform") SignUpForm signUpForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (signUpForm.getPassword().equals(signUpForm.getPasswordCheck())) { // check password match		
	    		String pwd = signUpForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	AppUser newUser = new AppUser();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(signUpForm.getUsername());
		    	newUser.setRole("USER");
		    	if (repository.findByUsername(signUpForm.getUsername()) == null) { // Check if user exists
		    		repository.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/login";    	
    }    
    
}