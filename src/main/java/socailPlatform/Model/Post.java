package socailPlatform.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class Post {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getAllPosts() {
		String sql = "SELECT post.*, user.name AS UserName, user.image AS UserImage FROM post JOIN user ON post.UserID = user.id ORDER BY `post`.`Created` DESC;";
        return jdbcTemplate.queryForList(sql);
    }
	
	public List<Map<String, Object>> getAllComments() {
		String sql = "SELECT comment.*, user.name AS UserName FROM comment JOIN user ON user.id = comment.UserID ORDER BY `CreatedAt` DESC;";
        return jdbcTemplate.queryForList(sql);
    }
	
	/*public boolean addNewPost(String user, String content, MultipartFile image) {
	    try {
	        byte[] imageData = image.getBytes(); // 取得圖片的二進位數據
	        // 執行 SQL 語句以儲存帖子和圖片數據
	        String sql = "INSERT INTO `post`(`UserID`, `Content`, `Image`) VALUES (?,?,?)";
	        return jdbcTemplate.update(sql, user, content, imageData) > 0;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}*/
	public boolean addNewPost(String user, String content, MultipartFile image) {
	    try {
	    	String getUserIdSql = "SELECT id FROM user WHERE name = ?";
            Integer userId = jdbcTemplate.queryForObject(getUserIdSql, Integer.class, user);
            
            if (userId != null) {
	            byte[] imageData = null; // 預設將 imageData 設置為 null
		        if (image != null && !image.isEmpty()) {
		            imageData = image.getBytes(); // 如果圖片不為空，則取得圖片的二進位數據
		        }
		        // 執行 SQL 語句以儲存帖子和圖片數據
		        String sql = "INSERT INTO `post`(`UserID`, `Content`, `Image`) VALUES (?,?,?)";
		        return jdbcTemplate.update(sql, userId, content, imageData) > 0;
            } else {
                return false;
            }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean editPost(String postId, String content) {
	    String sql = "UPDATE post SET Content = ? WHERE PostID = ?";
		return jdbcTemplate.update(sql, content, postId) > 0;
	}
	
	/*public boolean deletePost(String postId) {
	    String sql = "DELETE FROM post WHERE PostID = ?";
		return jdbcTemplate.update(sql, postId) > 0;
	}*/
	
	@Transactional
    public boolean deletePost(String postId) {
        String deleteSql = "DELETE FROM post WHERE PostID = ?";
        String deleteSql_comment = "DELETE FROM comment WHERE PostID = ?"; // 更新另一個表的 SQL 語句

        jdbcTemplate.update(deleteSql, postId);
        jdbcTemplate.update(deleteSql_comment, postId);

        return true; // 或根據需要返回其他結果
    }
	
	public boolean addCommentModel(String userId, String postId, String content) {
        String sql = "INSERT INTO comment (UserID, PostID, Content) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, userId, postId, content) > 0;
    }
}
