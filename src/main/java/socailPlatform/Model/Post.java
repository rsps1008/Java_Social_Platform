package socailPlatform.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class Post {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getAllPosts() {
		String sql = "SELECT post.*, user.name AS UserName FROM post JOIN user ON post.UserID = user.id;";
        return jdbcTemplate.queryForList(sql);
    }
	
	public boolean addNewPost(String user, String content, MultipartFile image) {
	    try {
	        byte[] imageData = image.getBytes(); // 取得圖片的二進位數據
	        // 執行 SQL 語句以儲存帖子和圖片數據
	        String sql = "INSERT INTO `post`(`UserID`, `Content`, `Image`) VALUES (?,?,?)";
	        return jdbcTemplate.update(sql, user, content, imageData) > 0;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean editPost(String postId, String content) {
	    String sql = "UPDATE post SET Content = ? WHERE PostID = ?";
		return jdbcTemplate.update(sql, content, postId) > 0;
	}
}
