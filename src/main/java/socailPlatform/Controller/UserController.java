package socailPlatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		boolean login = userService.login(user.username ,user.password, response);
		Cookie loggedIn_cookie;
		Cookie username_cookie;
		if(login) {
			//session.setAttribute("loggedIn", true);
			//session.setAttribute("user", user.username);
			loggedIn_cookie = new Cookie("loggedIn", "1");
			loggedIn_cookie.setPath("/");
			loggedIn_cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(loggedIn_cookie);
			username_cookie = new Cookie("user", user.username);
			username_cookie.setPath("/");
			username_cookie.setMaxAge(60 * 60 * 24 * 7); 
			response.addCookie(username_cookie);
		}else {
			loggedIn_cookie = new Cookie("loggedIn", null);
			loggedIn_cookie.setPath("/");
			loggedIn_cookie.setMaxAge(0);
			response.addCookie(loggedIn_cookie);
		}
		return login;
    }

    @PostMapping("/UserController/register")
	public boolean register(@RequestParam("name") String name,
            				@RequestParam("email") String email,
            				@RequestParam("password") String password,
            				@RequestParam(value = "coverImage", required = false) MultipartFile image,
            				@RequestParam("biography") String biography) {
		return userService.register(name, email, password, image, biography);
    }
}

