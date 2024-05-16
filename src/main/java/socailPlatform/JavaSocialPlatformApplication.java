package socailPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class JavaSocialPlatformApplication {

	public static class LoginInfo {
		public String username;
		public String password;
	}
	
	public static class RegisterInfo {
		public String username;
		public String password;
		public String email;
		public String coverImage;
		public String biography;
	}
	
	public static class LoginStatus {
		public boolean loggedIn;
		public String username;
	}
	
	public static class addPostInfo {
		public String user;
		public String content;
		public String imageUrl;
	}
	
	public static class updatePostInfo {
		public String postId;
		public String content;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JavaSocialPlatformApplication.class, args);
	}

}
