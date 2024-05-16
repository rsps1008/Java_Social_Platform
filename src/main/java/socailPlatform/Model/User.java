package socailPlatform.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import socailPlatform.JavaSocialPlatformApplication.LoginStatus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class User {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Map<String, Object>> getAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.queryForList(sql);
    }
    
    /*public boolean login(String username, String password) {
        String sql = "SELECT COUNT(*) FROM user WHERE name = ? AND pass = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        if (count != null && count > 0) {
        	return true;
        }else {
        	return false;
        }
    }

    public boolean register(String username, String password, String email, String coverImage, String biography) {
    	String sql = "INSERT INTO `user` (`id`, `name`, `email`, `pass`, `image`, `bio`) VALUES (NULL, ?, ?, ?, ?, ?);";
    	return jdbcTemplate.update(sql, username, password, email, coverImage, biography) > 0;
    }*/
    
    public boolean login(String username, String password, HttpServletResponse response) {
    	String getUserIdSql = "SELECT id FROM user WHERE name = ?";
        Integer userId = jdbcTemplate.queryForObject(getUserIdSql, Integer.class, username);
        Cookie loggedIn_cookie;
        loggedIn_cookie = new Cookie("userId", String.valueOf(userId));
		loggedIn_cookie.setPath("/");
		loggedIn_cookie.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(loggedIn_cookie);
    	
        String sql = "SELECT pass FROM user WHERE name = ?";
        try {
            String storedHashedPassword = jdbcTemplate.queryForObject(sql, String.class, username);

            if (storedHashedPassword != null && passwordEncoder.matches(password, storedHashedPassword)) {
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
    
    public boolean register(String name, String email, String password, MultipartFile coverImage, String biography) {
    	try {
	    	String hashedPassword = passwordEncoder.encode(password);
	        
	        byte[] imageData = null;
	        if (coverImage != null && !coverImage.isEmpty()) {
	            imageData = coverImage.getBytes(); // 如果圖片不為空，則取得圖片的二進位數據
	        }
	        
	        String sql = "INSERT INTO `user` (`id`, `name`, `email`, `pass`, `image`, `bio`) VALUES (NULL, ?, ?, ?, ?, ?);";
	        return jdbcTemplate.update(sql, name, email, hashedPassword, imageData, biography) > 0;
    	} catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
    }

}