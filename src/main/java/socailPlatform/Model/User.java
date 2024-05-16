package socailPlatform.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
//import socailPlatform.JavaSocialPlatformApplication.LoginStatus;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    public boolean login(String username, String password) {
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
    
    public boolean register(String name, String email, String password, String coverImage, String biography) {
        String hashedPassword = passwordEncoder.encode(password);
        String sql = "INSERT INTO `user` (`id`, `name`, `email`, `pass`, `image`, `bio`) VALUES (NULL, ?, ?, ?, ?, ?);";
        return jdbcTemplate.update(sql, name, email, hashedPassword, coverImage, biography) > 0;
    }

}