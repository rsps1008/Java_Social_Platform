package socailPlatform.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class Post {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getAllPosts() {
		String sql = "SELECT * FROM post";
        return jdbcTemplate.queryForList(sql);
    }
	
	public boolean addNewPost(String user, String content, String imageUrl) {
    	String sql = "INSERT INTO `post`(`UserID`, `Content`, `Image`) VALUES (?,?,?)";
    	return jdbcTemplate.update(sql, user, content, imageUrl) > 0;
    }
}
