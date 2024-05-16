package socailPlatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import socailPlatform.JavaSocialPlatformApplication.LoginInfo;
//import socailPlatform.JavaSocialPlatformApplication.LoginStatus;
import socailPlatform.JavaSocialPlatformApplication.RegisterInfo;
import socailPlatform.Model.User;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {

	@Autowired
    private User userService;
	
	@GetMapping("/UserController/users")
    public List<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers();
    }
	
	@PostMapping("/UserController/login")
    public boolean login(@RequestBody LoginInfo user, HttpServletResponse response) {
		boolean login = userService.login(user.username ,user.password);
		Cookie cookie;
		if(login) {
			//session.setAttribute("loggedIn", true);
			//session.setAttribute("user", user.username);
			cookie = new Cookie("loggedIn", "1");
			response.addCookie(cookie);
			cookie = new Cookie("user", user.username);
			response.addCookie(cookie);
		}else {
			cookie = new Cookie("loggedIn", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		return login;
    }

    @PostMapping("/UserController/register")
    public boolean register(@RequestBody RegisterInfo user) {
    	return userService.register(user.username ,user.password, user.email, user.coverImage, user.biography);
    }
    
}
