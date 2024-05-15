package socailPlatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import socailPlatform.JavaSocialPlatformApplication.LoginInfo;
import socailPlatform.JavaSocialPlatformApplication.RegisterInfo;
import socailPlatform.Model.User;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

	@Autowired
    private User userService;
	
	@GetMapping("/users")
    public List<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers();
    }
	
	@PostMapping("/login")
    public boolean login(@RequestBody LoginInfo user) {
		return userService.login(user.username ,user.password);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody RegisterInfo user) {
    	return userService.register(user.username ,user.password, user.email, user.coverImage, user.biography);
    }
}
