package socailPlatform.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
//import socailPlatform.JavaSocialPlatformApplication.LoginStatus;

import java.util.List;
import java.util.Map;

@Service
public class User {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllUsers() {
        String sql = "SELECT id, name FROM user";
        return jdbcTemplate.queryForList(sql);
    }
    
    public boolean login(String username, String password) {
        String sql = "SELECT COUNT(*) FROM user WHERE name = ? AND pass = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);
        if (count != null && count > 0) {
        	return true;
        }else {
        	return false;
        }
    }

    public boolean register(String username, String password, String email, String coverImage, String biography) {
    	String sql = "INSERT INTO `user` (`id`, `name`, `email`, `pass`, `image`, `bio`) VALUES (NULL, ?, ?, ?, ?, ?);";
    	return jdbcTemplate.update(sql, username, password, email, coverImage, biography) > 0;
    }

}