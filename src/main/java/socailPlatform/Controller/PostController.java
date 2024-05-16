package socailPlatform.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import socailPlatform.JavaSocialPlatformApplication.RegisterInfo;
import socailPlatform.JavaSocialPlatformApplication.addCommebtInfo;
import socailPlatform.JavaSocialPlatformApplication.addPostInfo;
import socailPlatform.JavaSocialPlatformApplication.deletePostInfo;
import socailPlatform.JavaSocialPlatformApplication.updatePostInfo;
import socailPlatform.Model.Post;

@RestController
public class PostController {
	@Autowired
    private Post postService;
	
	@GetMapping("/PostController/getAllPosts")
	public List<Map<String, Object>> getAllPosts() {
    	return postService.getAllPosts();
    }
	
	@GetMapping("/PostController/getAllComments")
	public List<Map<String, Object>> getAllComments() {
    	return postService.getAllComments();
    }
	
	@PostMapping("/PostController/addNewPost")
	public boolean addNewPost(@RequestParam("user") String user,
	                          @RequestParam("content") String content,
	                          @RequestParam(value = "image", required = false) MultipartFile image) {
	    return postService.addNewPost(user, content, image);
	}
	
	@PostMapping("/PostController/editPost")
	public boolean editPost(@RequestBody updatePostInfo post) {
	    return postService.editPost(post.postId, post.content);
	}
	
	@PostMapping("/PostController/deletePost")
	public boolean deletePost(@RequestBody deletePostInfo post) {
	    return postService.deletePost(post.postId);
	}
	
	@PostMapping("/CommentController/addComment")
    public boolean addComment(@RequestBody addCommebtInfo comment) {
        return postService.addCommentModel(comment.userId, comment.postId, comment.content);
    }
}
