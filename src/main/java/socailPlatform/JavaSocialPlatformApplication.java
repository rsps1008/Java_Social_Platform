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

	
	public static void main(String[] args) {
		SpringApplication.run(JavaSocialPlatformApplication.class, args);
	}

}
